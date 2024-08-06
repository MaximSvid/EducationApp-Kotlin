package com.example.educationappmaximsvidrak.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.educationappmaximsvidrak.data.local.FlashcardDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData

class Repository(private val database: FlashcardDatabase) {
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

}