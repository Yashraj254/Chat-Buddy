package com.yashraj.chat_domain.usecases

import com.yashraj.chat_domain.repository.ChatRepository
import com.yashraj.utils.Resource
import kotlinx.coroutines.flow.Flow

class Logout(private val repository: ChatRepository) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return repository.logout()
    }
}