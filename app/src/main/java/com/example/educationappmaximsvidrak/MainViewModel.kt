package com.example.educationappmaximsvidrak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.educationappmaximsvidrak.data.Repository
import com.example.educationappmaximsvidrak.data.local.getDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Message
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel (application) {

    private val database = getDatabase(application)
    private val repository = Repository(database)

    val message = repository.chat

    val error = repository.error

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

    private var _selectedCard = MutableLiveData<FlashcardData>()
    val selectedCard: LiveData<FlashcardData> = _selectedCard

    fun selectedFlashcard(flashcard: FlashcardData){
        _selectedCard.postValue(flashcard)
    }




    fun sendMessage(content: String) {
        viewModelScope.launch {
            repository.sendMessage(content)
        }
    }



    init {
//        loadMessage()

    }

//    fun loadMessage () {
//        viewModelScope.launch {
//            repository.loadMessage()
//        }
//    }
}