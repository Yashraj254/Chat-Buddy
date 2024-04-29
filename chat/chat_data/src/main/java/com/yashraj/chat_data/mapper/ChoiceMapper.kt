package com.yashraj.chat_data.mapper

import com.yashraj.chat_data.model.response.ChoiceDto
import com.yashraj.chat_domain.model.response.Choice

fun ChoiceDto.toChoice(): Choice = Choice(
    finish_reason = finishReason,
    index = index,
    message = message.toMessage()
)

fun Choice.toChoiceDto(): ChoiceDto = ChoiceDto(
    finishReason = finish_reason,
    index = index,
    message = message.toMessageDto()
)