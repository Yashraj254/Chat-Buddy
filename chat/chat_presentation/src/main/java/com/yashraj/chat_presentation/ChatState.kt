package com.yashraj.chat_presentation

import com.yashraj.chat_domain.model.common.Message


data class ChatState (
    val chats: List<Message>? = emptyList(),
    val isLoading:Boolean = false,
    val error: String? = null
)