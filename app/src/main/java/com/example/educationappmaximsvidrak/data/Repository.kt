package com.example.educationappmaximsvidrak.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.educationappmaximsvidrak.data.local.FlashcardDatabase
import com.example.educationappmaximsvidrak.data.remote.EducationApi
import com.example.educationappmaximsvidrak.model.ChatCompletionRequest
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder
import com.example.educationappmaximsvidrak.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: FlashcardDatabase) {

    val API_KEY =
        "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"
//test

    val flashcardList: LiveData<List<FlashcardData>> = database.flashcardDAO.getAllCards()

    val folderList: LiveData<List<Folder>> = database.flashcardDAO.getAllFolders()

    val statisticsInfo: LiveData<List<Long>> = database.flashcardDAO.getStudyDates()


     fun checkFolderExist (): LiveData<List<Folder>> {
       return folderList
    }

     fun getItemInFolder (folderId: Long): LiveData<List<FlashcardData>> {
        return try {
             database.flashcardDAO.getCardsByFolder(folderId)
        }catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
            MutableLiveData<List<FlashcardData>>() // Leere LiveData zurückgeben, wenn ein Fehler auftritt
        }
    }


    suspend fun addStudyDate (cardId: Long, date: Long) {
        val card = database.flashcardDAO.getCardById(cardId)
        try {
            card?.let {
                it.studyDate = date
                database.flashcardDAO.update(it)
                Log.e("RepositoryLog", "Card edit!!!")
            }
        } catch (e:Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }

    }

     fun getCardsByFolder(folderId: Long): LiveData<List<FlashcardData>> {
        return try {
            database.flashcardDAO.getCardsByFolder(folderId)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
            MutableLiveData<List<FlashcardData>>() // Leere LiveData zurückgeben, wenn ein Fehler auftritt
        }
    }

    suspend fun addFolder(folder: Folder) {
        try {
            database.flashcardDAO.insertFolder(folder)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    suspend fun addFlashcard(flashcard: FlashcardData) {
        try {
            database.flashcardDAO.insert(flashcard)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    suspend fun updateFlashcard(flashcard: FlashcardData) {
        try {
            database.flashcardDAO.update(flashcard)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    suspend fun deleteFlashcard(flashcard: FlashcardData) {
        try {
            database.flashcardDAO.delete(flashcard)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }

    suspend fun deleteFolder (folder: Folder) {
        try {
            database.flashcardDAO.deleteFolder(folder)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
        }
    }


    private val _chat = MutableLiveData<MutableList<Message>>(mutableListOf())
    val chat: LiveData<MutableList<Message>> get() = _chat

    suspend fun sendMessage(content: String) {
        /*TODO Asynchrone Operationen: Netzanfragen werden innerhalb von withContext(Dispatchers.IO) ausgeführt,
        TODO was die Ausführung im Hintergrund-Thread gewährleistet.
         */
        withContext(Dispatchers.IO) {
            try {
                val currentMessages = _chat.value ?: mutableListOf()
                // TODO Benutzernachricht hinzufügen
                val userMessage = Message(role = "user", content = content)
                currentMessages.add(userMessage)

                // TODO Erstellen Sie eine Abfrage, die alle vorherigen Nachrichten berücksichtigt
                val request = ChatCompletionRequest(
                    model = "gpt-3.5-turbo",
                    messages = currentMessages
                )

                // TODO Anfrage an API senden
                val response = EducationApi.retrofitService.sendMessage("Bearer $API_KEY", request)

                // TODO Empfangen einer Antwort von ChatGPT
                val gptMessage = response.choices.first().message

                // TODO Hinzufügen der Antwort zur Nachrichtenliste
                currentMessages.add(gptMessage)

                // TODO LiveDaten aktualisieren
                _chat.postValue(currentMessages)
            } catch (e: Exception) {
                Log.e("RepositoryLog", e.message.toString())
            }
        }
    }
}







