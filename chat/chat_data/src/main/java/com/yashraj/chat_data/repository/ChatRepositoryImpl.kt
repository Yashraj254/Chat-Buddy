package com.yashraj.chat_data.repository

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.yashraj.chat_data.mapper.toChatRequestDto
import com.yashraj.chat_data.remote.ChatApi
import com.yashraj.chat_data.utils.SafeApiCall
import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_domain.model.request.ChatRequest
import com.yashraj.chat_domain.repository.ChatRepository
import com.yashraj.utils.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow


class ChatRepositoryImpl(
    private val context: Context,
    private val api: ChatApi,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : ChatRepository, SafeApiCall() {

    override suspend fun getChatResponse(
        chatRequest: ChatRequest,
        chatTitle: String
    ): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            sendChatMessage(
                chatRequest.messages[chatRequest.messages.size - 1].content,
                auth.currentUser?.email.toString(),
                chatTitle
            )
            val chatRequestDto = chatRequest.toChatRequestDto()
            when (val response =
                safeApiCall { api.getChat(chatRequest = chatRequestDto) }) {
                is Resource.Success -> {
                    if (response.data?.choices?.get(0)?.message?.content != null) {
                        sendChatMessage(
                            response.data?.choices?.get(0)?.message?.content!!,
                            "assistant",
                            chatTitle
                        )
                    }
                    emit(Resource.Success(chatRequest.messages[chatRequest.messages.size - 1].content))
                }

                is Resource.Error -> {
                    emit(Resource.Error(response.message ?: "Something went wrong"))
                }

                else -> {
                    emit(Resource.Error("Something went wrong"))
                }
            }
        }
    }

    override suspend fun getFirebaseChats(chatTitle: String): Flow<Resource<List<Message>>> =
        callbackFlow {
            trySend(Resource.Loading())
            firestore.collection("users").document(auth.currentUser?.email.toString())
                .collection(chatTitle)
                .orderBy("timeStamp", Query.Direction.ASCENDING)
                .addSnapshotListener { it, e ->
                    val list = mutableListOf<Message>()

                    it?.documents?.forEach { document ->
                        list.add(
                            Message(
                                document["chat"].toString(),
                                document["role"].toString()
                            )
                        )
                    }
                    trySend(Resource.Success(list))
                }
            awaitClose()
        }

    override suspend fun getAllChats(): Flow<Resource<List<String>>> = callbackFlow {

        firestore.collection("users").document(auth.currentUser?.email.toString())
            .addSnapshotListener { snapshot, e ->
                try {
                    val list = snapshot?.data?.get("AllChats") as List<String>
                    trySend(Resource.Success(list))
                } catch (e: Exception) {
                    e.printStackTrace()
                    trySend(Resource.Error("Something went wrong"))
                }
            }
        awaitClose()
    }

    override suspend fun createNewChat(chatTitle: String): Flow<Resource<String>> = callbackFlow {

        firestore.collection("users").document(auth.currentUser?.email.toString())
            .update("AllChats", FieldValue.arrayUnion(chatTitle))
            .addOnSuccessListener {
                trySend(Resource.Success(chatTitle))
            }
            .addOnFailureListener {
                val allChats = hashMapOf("AllChats" to listOf(chatTitle))
                firestore.collection("users").document(auth.currentUser?.email.toString()).set(allChats)
                trySend(Resource.Error("Something went wrong"))
            }

        awaitClose()
    }

    override suspend fun deleteChat(chatTitle: String) {
        firestore.collection("users").document(auth.currentUser?.email.toString())
            .update("AllChats", FieldValue.arrayRemove(chatTitle))

        //delete all messages
        firestore.collection("users").document(auth.currentUser?.email.toString())
            .collection(chatTitle)
            .get()
            .addOnSuccessListener {
                it.documents.forEach { document ->
                    document.reference.delete()
                }
            }

    }

    override suspend fun logout(): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
                trySend(Resource.Success(true))
            }
        awaitClose()
    }


    private fun sendChatMessage(message: String, from: String, chatTitle: String) {
        val timeStamp = FieldValue.serverTimestamp()
        var sender = from
        if (auth.currentUser?.email.toString() == from)
            sender = "user"
        if (from == "assistant")
            sender = "assistant"
        val chat = hashMapOf(
            "chat" to message,
            "role" to sender,
            "timeStamp" to timeStamp
        )
        firestore.collection("users").document(auth.currentUser?.email.toString())
            .collection(chatTitle)
            .add(chat)

    }

}