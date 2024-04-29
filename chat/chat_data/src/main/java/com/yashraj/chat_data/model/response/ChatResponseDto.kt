package com.yashraj.chat_data.model.response


import com.squareup.moshi.Json

data class ChatResponseDto(
    @Json(name = "choices")
    val choices: List<ChoiceDto>,
    @Json(name = "created")
    val created: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "object")
    val objectX: String,
    @Json(name = "usage")
    val usage: UsageDto
)