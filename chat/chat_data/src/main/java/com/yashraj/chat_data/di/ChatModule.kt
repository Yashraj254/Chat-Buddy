package com.yashraj.chat_data.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yashraj.chat_data.remote.ChatApi
import com.yashraj.chat_data.repository.ChatRepositoryImpl
import com.yashraj.chat_domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideChatApi(moshi: Moshi): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL_CHAT)

            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
//                    .connectTimeout(60, TimeUnit.SECONDS)
//                    .readTimeout(60, TimeUnit.SECONDS)
//                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build())
            .build()
            .create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFirestoreDatabase(): FirebaseFirestore {
        return  FirebaseFirestore.getInstance()
    }


    @Provides
    @Singleton
    fun provideChatRepository(@ApplicationContext context: Context, chatApi: ChatApi, firebaseAuth: FirebaseAuth, firestoreDatabase: FirebaseFirestore): ChatRepository {
        return ChatRepositoryImpl(context,chatApi, firebaseAuth, firestoreDatabase)
    }

}