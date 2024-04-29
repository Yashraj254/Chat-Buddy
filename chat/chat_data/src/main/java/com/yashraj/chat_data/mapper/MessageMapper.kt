package com.yashraj.chat_data.mapper

import com.yashraj.chat_data.model.common.MessageDto
import com.yashraj.chat_domain.model.common.Message


fun MessageDto.toMessage(): Message = Message(
    content = content,
    role = role
)

fun Message.toMessageDto(): MessageDto = MessageDto(
    content = content,
    role = role
)