package com.example.educationappmaximsvidrak.model

data class ChatRequest (
    val model: String,
    val messages: List<Message>
)