package com.example.educationappmaximsvidrak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.educationappmaximsvidrak.data.Repository
import com.example.educationappmaximsvidrak.data.local.getDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel (application) {

    private val database = getDatabase(application)
    private val repository = Repository(database)

    val flashcardList: LiveData<List<FlashcardData>> = repository.flashcardList

    fun addFlashcard (flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.addFlashcard(flashcard)
        }
    }

    fun updateFlashcard (flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.updateFlashcard(flashcard)
        }
    }

    fun deleteFlashcard(flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.deleteFlashcard(flashcard)
        }
    }

    init {
        loadChat()
    }

    fun loadChat () {
        viewModelScope.launch {
            repository.loadChat()
        }
    }
}