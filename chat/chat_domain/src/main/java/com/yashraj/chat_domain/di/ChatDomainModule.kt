package com.yashraj.chat_domain.di

import com.yashraj.chat_domain.repository.ChatRepository
import com.yashraj.chat_domain.usecases.ChatUseCases
import com.yashraj.chat_domain.usecases.CreateNewChat
import com.example.chat_domain.usecases.DeleteChat
import com.yashraj.chat_domain.usecases.GetAllChats
import com.yashraj.chat_domain.usecases.GetChatResponse
import com.yashraj.chat_domain.usecases.GetFirebaseChats
import com.yashraj.chat_domain.usecases.Logout
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatDomainModule {

    @Singleton
    @Provides
    fun provideChatDomainModule(repository: ChatRepository) : ChatUseCases {
        return ChatUseCases(
            getChatResponse = GetChatResponse(repository),
            getFirebaseChats = GetFirebaseChats(repository),
            getAllChats = GetAllChats(repository),
            createNewChat = CreateNewChat(repository),
            deleteChat = DeleteChat(repository),
            logout = Logout(repository)
        )
    }
}