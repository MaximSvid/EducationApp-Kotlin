package com.example.educationappmaximsvidrak.model

data class ChatCompletionRequest(
    val model: String,
    val messages: List<Message>
)
