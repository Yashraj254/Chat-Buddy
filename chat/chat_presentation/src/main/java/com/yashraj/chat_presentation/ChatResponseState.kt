package com.yashraj.chat_presentation

data class ChatResponseState (
    val chatResponse: String? = null,
    val isLoading:Boolean = false,
    val error: String? = null
)