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


    val flashcardList: LiveData<List<FlashcardData>> = database.flashcardDAO.getAllCards()
    val folderList: LiveData<List<Folder>> = database.flashcardDAO.getAllFolders()

     fun getCardsByFolder(folderId: Long): LiveData<List<FlashcardData>> {
        return try {
            database.flashcardDAO.getCardsByFolder(folderId)
        } catch (e: Exception) {
            Log.e("RepositoryLog", e.message.toString())
            MutableLiveData<List<FlashcardData>>() // Вернуть пустой LiveData, если произошла ошибка
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
        //Асинхронные операции: Сетевые запросы выполняются внутри withContext(Dispatchers.IO), что обеспечивает выполнение в фоновом потоке.
        withContext(Dispatchers.IO) {
            try {
                val currentMessages = _chat.value ?: mutableListOf()

                // Добавляем сообщение пользователя
                val userMessage = Message(role = "user", content = content)
                currentMessages.add(userMessage)

                // Создаем запрос с учетом всех предыдущих сообщений
                val request = ChatCompletionRequest(
                    model = "gpt-3.5-turbo",
                    messages = currentMessages
                )

                // Отправляем запрос в API
                val response = EducationApi.retrofitService.sendMessage("Bearer $API_KEY", request)

                // Получаем ответ от ChatGPT
                val gptMessage = response.choices.first().message

                // Добавляем ответ в список сообщений
                currentMessages.add(gptMessage)

                // Обновляем LiveData
                _chat.postValue(currentMessages)
            } catch (e: Exception) {
                Log.e("RepositoryLog", e.message.toString())
            }
        }
    }
}







