package com.yashraj.chat_domain.repository

import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_domain.model.request.ChatRequest
import com.yashraj.utils.Resource

import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChatResponse(chatRequest: ChatRequest, chatTitle:String): Flow<Resource<String>>

    suspend fun getFirebaseChats(chatTitle: String): Flow<Resource<List<Message>>>

    suspend fun getAllChats(): Flow<Resource<List<String>>>

    suspend fun createNewChat(chatTitle: String): Flow<Resource<String>>

    suspend fun deleteChat(chatTitle: String)

    suspend fun logout(): Flow<Resource<Boolean>>

}