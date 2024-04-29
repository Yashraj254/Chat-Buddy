package com.yashraj.chat_data.mapper


import com.yashraj.chat_data.model.request.ChatRequestDto
import com.yashraj.chat_domain.model.request.ChatRequest

fun ChatRequestDto.toChatRequest(): ChatRequest = ChatRequest(
    messages = messages.map { it.toMessage() },
    model = model
)

fun ChatRequest.toChatRequestDto(): ChatRequestDto = ChatRequestDto(
    messages = messages.map { it.toMessageDto() },
    model = model
)