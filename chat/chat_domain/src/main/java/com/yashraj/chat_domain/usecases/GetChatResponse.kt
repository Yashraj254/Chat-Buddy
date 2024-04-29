package com.yashraj.chat_domain.usecases

import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_domain.model.request.ChatRequest
import com.yashraj.chat_domain.repository.ChatRepository
import com.yashraj.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetChatResponse(private val repository: ChatRepository) {
    suspend operator fun invoke(messages: List<Message>, chatTitle:String): Flow<Resource<String>> {
        val request = ChatRequest(
            messages = messages,
            model = "gpt-3.5-turbo",
        )
        return repository.getChatResponse(request,chatTitle)
    }
}