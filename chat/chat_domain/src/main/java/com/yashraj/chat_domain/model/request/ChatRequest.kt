package com.yashraj.chat_domain.model.request

import com.yashraj.chat_domain.model.common.Message

data class ChatRequest(
    val messages: List<Message>,
    val model: String
)