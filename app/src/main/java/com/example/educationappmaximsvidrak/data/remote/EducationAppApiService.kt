package com.example.educationappmaximsvidrak.data.remote

import com.example.educationappmaximsvidrak.model.ChatCompletionRequest
import com.example.educationappmaximsvidrak.model.ChatCompletionResponse
import com.example.educationappmaximsvidrak.model.Message
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://api.openai.com/v1/"

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface EducationAppApiService {
    @POST("chat/completions")
    suspend fun sendMessage(
        @Header("Authorization") authHeader: String,
        @Body requestBody: ChatCompletionRequest
    ): ChatCompletionResponse
//testTEST

}

object EducationApi {
    val retrofitService: EducationAppApiService by lazy { retrofit.create(EducationAppApiService::class.java) }
}