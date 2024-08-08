package com.example.educationappmaximsvidrak.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.educationappmaximsvidrak.data.local.FlashcardDatabase
import com.example.educationappmaximsvidrak.data.remote.EducationApi
import com.example.educationappmaximsvidrak.model.ChatCompletionRequest
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



//    suspend fun loadMessage() {
//        try {
//            val response = EducationApi.retrofitService.sendMessage(API_KEY)
//            _chat.postValue(response)
//        } catch (e: Exception) {
//            Log.e ("RepositoryLog", e.message.toString())
//        }
//    }

    suspend fun sendMessage(content: String) {
        try {
            val request = ChatCompletionRequest(
                 "gpt-3.5-turbo",
                messages = listOf(Message(role =  "user", content =  content))
            )
            val response = EducationApi.retrofitService.sendMessage("Bearer $API_KEY", request)
            _chat.postValue(response.choices.map { it.message })
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

}