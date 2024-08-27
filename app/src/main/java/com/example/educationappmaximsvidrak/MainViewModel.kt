package com.example.educationappmaximsvidrak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.educationappmaximsvidrak.data.Repository
import com.example.educationappmaximsvidrak.data.local.getDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder
import com.example.educationappmaximsvidrak.model.Message
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = Repository(database)

    val message = repository.chat


    val flashcardList: LiveData<List<FlashcardData>> = repository.flashcardList
    val folderList: LiveData<List<Folder>> = repository.folderList

    val studyDates: LiveData<List<Long>> = repository.statisticsInfo

    fun addStudyDate (cardId: Long, date: Long) {
        viewModelScope.launch {
            repository.addStudyDate(cardId, date)
        }
    }

    fun addFolder(folder: Folder) {
        viewModelScope.launch {
            repository.addFolder(folder)
        }
    }

    private var _selectedFolder = MutableLiveData<Folder>()
    val selectedFolder: LiveData<Folder> = _selectedFolder

    fun selectFolder(folder: Folder) {
        _selectedFolder.postValue(folder)
    }

    fun getFlashcardsBySelectedFolder(): LiveData<List<FlashcardData>> {
        val folder = _selectedFolder.value
        return if (folder != null) {
            repository.getCardsByFolder(folder.id)
        } else {
            MutableLiveData(emptyList()) // Возвращаем пустой список, если папка не выбрана
        }
    }


    fun addFlashcard(flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.addFlashcard(flashcard)
        }
    }

    fun updateFlashcard(flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.updateFlashcard(flashcard)
        }
    }

    fun deleteFlashcard(flashcard: FlashcardData) {
        viewModelScope.launch {
            repository.deleteFlashcard(flashcard)
        }
    }

    fun deleteFolder(folder: Folder) {
        viewModelScope.launch {
            repository.deleteFolder(folder)
        }
    }

    private var _selectedCard = MutableLiveData<FlashcardData>()
    val selectedCard: LiveData<FlashcardData> = _selectedCard

    fun selectedFlashcard(flashcard: FlashcardData) {
        _selectedCard.postValue(flashcard)
    }


    fun sendMessage(content: String) {
        viewModelScope.launch {
            repository.sendMessage(content)
        }
    }

}