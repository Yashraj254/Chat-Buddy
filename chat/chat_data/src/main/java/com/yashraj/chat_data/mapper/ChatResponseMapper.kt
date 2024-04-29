package com.yashraj.chat_data.mapper

import com.yashraj.chat_data.model.response.ChatResponseDto
import com.yashraj.chat_domain.model.response.ChatResponse

fun ChatResponseDto.toChatResponse(): ChatResponse = ChatResponse(
    choices = choices.map { it.toChoice() },
    created = created,
    id = id,
    `object` = objectX,
    usage = usage.toUsage()
)

fun ChatResponse.toChatResponseDto(): ChatResponseDto = ChatResponseDto(
    choices = choices.map { it.toChoiceDto() },
    created = created,
    id = id,
    objectX = `object`,
    usage = usage.toUsageDto()
)