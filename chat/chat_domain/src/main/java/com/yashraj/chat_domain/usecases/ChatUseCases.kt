package com.yashraj.chat_domain.usecases

import com.example.chat_domain.usecases.DeleteChat

data class ChatUseCases(
    val getChatResponse: GetChatResponse,
    val getFirebaseChats: GetFirebaseChats,
    val getAllChats: GetAllChats,
    val createNewChat: CreateNewChat,
    val deleteChat: DeleteChat,
    val logout: Logout
)