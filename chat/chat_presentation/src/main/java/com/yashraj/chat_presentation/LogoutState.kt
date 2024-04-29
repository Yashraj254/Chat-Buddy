package com.yashraj.chat_presentation

data class LogoutState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false,
    val error: String? = "Failed to logout"
)