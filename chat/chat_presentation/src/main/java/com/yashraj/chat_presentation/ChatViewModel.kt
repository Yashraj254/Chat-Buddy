package com.yashraj.chat_presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.chat_domain.model.common.Message
import com.yashraj.chat_domain.usecases.ChatUseCases
import com.yashraj.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private val _allChats = MutableStateFlow(AllChatsState())
    val allChats = _allChats.asStateFlow()

    private val _chatResponse = MutableStateFlow(ChatResponseState())
    val chatResponse = _chatResponse.asStateFlow()

    private val _logoutState = MutableStateFlow(LogoutState())
    val logoutState = _logoutState.asStateFlow()

    init {
        getAllChats()
    }

    fun getChatResponse(messages: List<Message>, chatTitle: String) {

        viewModelScope.launch {
            chatUseCases.getChatResponse.invoke(messages, chatTitle).collect {
                when (it) {
                    is Resource.Success -> {
                        _chatResponse.value = _chatResponse.value.copy(
                            chatResponse = it.data,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _chatResponse.value = _chatResponse.value.copy(
                            chatResponse = "Something went wrong",
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _chatResponse.value =
                            _chatResponse.value.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

    fun getTitleChats(chatTitle: String) {
        viewModelScope.launch {
            chatUseCases.getFirebaseChats.invoke(chatTitle).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(chats = it.data, isLoading = false, error = null)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = it.message, isLoading = false)
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true, error = null)
                    }

                    else -> {}
                }
            }
        }
    }

    fun createNewChat(chatTitle: String) {
        viewModelScope.launch {
            chatUseCases.createNewChat.invoke(chatTitle).collect {
                when (it) {
                    is Resource.Success -> {
                        Log.d("TAG", "createNewChat: ${it.data}")
//                        _state.value = _state.value.copy(chats = it, isLoading = false, error = null)
                    }

                    is Resource.Error -> {
//                        _state.value = _state.value.copy(error = it.message, isLoading = false)
                    }

                    is Resource.Loading -> {
//                        _state.value = _state.value.copy(isLoading = true, error = null)
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteChat(chatTitle: String) {
        viewModelScope.launch {
            chatUseCases.deleteChat.invoke(chatTitle)
        }
    }

    private fun getAllChats() {
        viewModelScope.launch {
            chatUseCases.getAllChats.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _allChats.value =
                            _allChats.value.copy(chats = it.data, isLoading = false, error = null)
                    }

                    is Resource.Error -> {

                        _allChats.value =
                            _allChats.value.copy(error = it.message, isLoading = false)
                    }

                    is Resource.Loading -> {
                        _allChats.value = _allChats.value.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            chatUseCases.logout.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _logoutState.value = _logoutState.value.copy(
                            isLoggedOut = true,
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _logoutState.value =
                            _logoutState.value.copy(error = it.message, isLoading = false)
                    }

                    is Resource.Loading -> {
                        _logoutState.value = _logoutState.value.copy(isLoading = true, error = null)
                    }
                }
            }
        }
    }

}