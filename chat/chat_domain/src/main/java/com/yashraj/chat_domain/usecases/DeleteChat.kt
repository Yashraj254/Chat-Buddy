package com.example.chat_domain.usecases

import com.yashraj.chat_domain.repository.ChatRepository

class DeleteChat(private val repository: ChatRepository) {
    suspend operator fun invoke(chatTitle:String) {
        repository.deleteChat(chatTitle)
    }
}