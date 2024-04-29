package com.yashraj.chat_domain.usecases

import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_domain.repository.ChatRepository
import com.yashraj.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetFirebaseChats(private val repository: ChatRepository) {
    suspend operator fun invoke(chatTitle:String) : Flow<Resource<List<Message>>> {
        return repository.getFirebaseChats(chatTitle)
    }
}