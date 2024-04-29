package com.yashraj.chat_domain.model.response

import com.yashraj.chat_domain.model.common.Message

data class Choice(
    val finish_reason: String,
    val index: Int,
    val message: Message
)