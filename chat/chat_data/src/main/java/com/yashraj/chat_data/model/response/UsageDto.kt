package com.yashraj.chat_data.model.response


import com.squareup.moshi.Json

data class UsageDto(
    @Json(name = "completion_tokens")
    val completionTokens: Int,
    @Json(name = "prompt_tokens")
    val promptTokens: Int,
    @Json(name = "total_tokens")
    val totalTokens: Int
)