package com.example.educationappmaximsvidrak

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.educationappmaximsvidrak.data.Repository
import com.example.educationappmaximsvidrak.data.local.getDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = Repository(database)

    val message = repository.chat


    val flashcardList: LiveData<List<FlashcardData>> = repository.flashcardList

    val folderList: LiveData<List<Folder>> = repository.folderList

    val studyDates: LiveData<List<Long>> = repository.statisticsInfo

    // Наблюдение за списком папок
    fun observeFolderList() {
        folderList.observeForever { folders ->
            if (folders.isNotEmpty() && _selectedFolder.value == null) {
                _selectedFolder.value = folders.first()
            }
        }
    }


    fun checkFolderExist(): LiveData<List<Folder>> {
        return repository.checkFolderExist()
    }

    fun getItemsInFolder(folderId: Long): LiveData<List<FlashcardData>> {
        return repository.getItemInFolder(folderId)
    }

    fun addStudyDate(cardId: Long, date: Long) {
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

    fun selectFolder1(folder: Folder) {
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
            Log.i("New Flashcard", "done")
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
        //TODO postValue oder setValue
        /*
        TODO Die postValue-Methode wird verwendet, um den LiveData-Wert von einem beliebigen Thread aus zu aktualisieren,
         was besonders in einer Umgebung mit mehreren Threads nützlich ist.
         Im Gegensatz zur setValue-Methode, die nur vom Haupt-Thread aus aufgerufen werden kann,
         kann postValue von jedem Thread aus aufgerufen werden und überträgt Änderungen sicher an den Haupt-Thread,
         um Beobachter zu informieren.
         */

        _selectedCard.postValue(flashcard)
    }


    fun sendMessage(content: String) {
        viewModelScope.launch {
            repository.sendMessage(content)
        }
    }

}