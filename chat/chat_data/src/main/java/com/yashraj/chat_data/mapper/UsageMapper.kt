package com.yashraj.chat_data.mapper

import com.yashraj.chat_data.model.response.UsageDto
import com.yashraj.chat_domain.model.response.Usage

fun UsageDto.toUsage(): Usage = Usage(
    completion_tokens = completionTokens,
    prompt_tokens = promptTokens,
    total_tokens = totalTokens
)

fun Usage.toUsageDto(): UsageDto = UsageDto(
    completionTokens = completion_tokens,
    promptTokens = prompt_tokens,
    totalTokens = total_tokens
)