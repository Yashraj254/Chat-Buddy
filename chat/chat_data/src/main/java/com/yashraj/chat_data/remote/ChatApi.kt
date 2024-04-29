package com.yashraj.chat_data.remote

import com.yashraj.chat_data.model.request.ChatRequestDto
import com.yashraj.chat_data.model.response.ChatResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApi {
    companion object {
        const val BASE_URL_CHAT = "https://api.openai.com/v1/chat/"
        const val API_KEY = "BuildConfig.EMP_COMMENTS_API_KEY"
    }

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $API_KEY"
    )
    @POST("completions")
    suspend fun getChat(
        @Body chatRequest: ChatRequestDto
    ) : Response<ChatResponseDto>
}