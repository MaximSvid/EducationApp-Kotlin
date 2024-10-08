package com.example.educationappmaximsvidrak.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder

@Dao
interface EducationAppDatabaseDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (card: FlashcardData)

    @Delete
    suspend fun delete(card: FlashcardData)

    @Delete
    suspend fun deleteFolder(folder: Folder)

    @Update
    suspend fun update(card: FlashcardData)

    @Query ("SELECT * FROM flashcard_table")
    fun getAllCards(): LiveData<List<FlashcardData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: Folder)

    @Query("SELECT * FROM folder_table")
    fun getAllFolders(): LiveData<List<Folder>>

    @Query("SELECT * FROM flashcard_table WHERE folderId = :folderId")
    fun getCardsByFolder(folderId: Long): LiveData<List<FlashcardData>>

    @Query("SELECT DISTINCT studyDate FROM flashcard_table WHERE studyDate is NOT NULL")
    fun getStudyDates(): LiveData<List<Long>> // Получение уникальных дат обучения

    @Query("SELECT * FROM flashcard_table WHERE id = :cardId LIMIT 1")
    suspend fun getCardById(cardId: Long): FlashcardData?





}