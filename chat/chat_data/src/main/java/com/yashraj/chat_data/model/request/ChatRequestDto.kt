package com.yashraj.chat_data.model.request


import com.yashraj.chat_data.model.common.MessageDto
import com.squareup.moshi.Json

data class ChatRequestDto(
    @Json(name = "messages")
    val messages: List<MessageDto>,
    @Json(name = "model")
    val model: String
)