package com.yashraj.chat_data.model.response


import com.yashraj.chat_data.model.common.MessageDto
import com.squareup.moshi.Json

data class ChoiceDto(
    @Json(name = "finish_reason")
    val finishReason: String,
    @Json(name = "index")
    val index: Int,
    @Json(name = "message")
    val message: MessageDto
)