package com.yashraj.chat_data.model.common

import com.squareup.moshi.Json

data class MessageDto(
    @Json(name = "content")
    val content: String,
    @Json(name = "role")
    val role: String
)
