package com.example.educationappmaximsvidrak.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.educationappmaximsvidrak.data.local.FlashcardDatabase
import com.example.educationappmaximsvidrak.data.remote.EducationApi
import com.example.educationappmaximsvidrak.model.ChatRequest
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Message

class Repository(private val database: FlashcardDatabase) {

    val API_KEY = "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"


    val flashcardList: LiveData<List<FlashcardData>> = database.flashcardDAO.getAll()

    suspend fun addFlashcard (flashcard: FlashcardData) {
        try {
            database.flashcardDAO.insert(flashcard)
        } catch (e: Exception) {
            Log.e ("RepositoryLog", e.message.toString())
        }
    }

    suspend fun updateFlashcard(flashcard: FlashcardData) {
        try {
            database.flashcardDAO.update(flashcard)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    suspend fun deleteFlashcard (flashcard: FlashcardData) {
        try {
            database.flashcardDAO.delete(flashcard)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    private var _chat = MutableLiveData<List<Message>>()
    val chat: LiveData<List<Message>> = _chat

//    suspend fun sendMessage (userMessage: String) {
//        try {
//            val chatRequest = ChatRequest (
//                model = "gpt-3.5-turbo",
//                messages = listOf(
//                    Message(role = "system", content = "You are a helpful assistant."),
//                    Message(role = "user", content = userMessage)
//                )
//            )
//            val response = EducationApi.retrofitService.getChat(chatRequest)
//            _chat.postValue(response. })
//        }
//    }

    suspend fun loadChat() {
        try {
            val response = EducationApi.retrofitService.getChat(API_KEY)
            _chat.postValue(response)
        } catch (e: Exception) {
            Log.e ("RepositoryLog", e.message.toString())
        }
    }

}