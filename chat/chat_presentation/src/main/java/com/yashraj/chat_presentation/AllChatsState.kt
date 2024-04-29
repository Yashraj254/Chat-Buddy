package com.yashraj.chat_presentation

data class AllChatsState(
    val chats: List<String>? = emptyList(),
    val isLoading:Boolean = false,
    val error: String? = null
)