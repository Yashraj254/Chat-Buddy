package com.yashraj.chat_domain.model.response

data class ChatResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val `object`: String,
    val usage: Usage
)