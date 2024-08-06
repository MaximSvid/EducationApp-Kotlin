package com.example.educationappmaximsvidrak.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.educationappmaximsvidrak.model.FlashcardData

@Dao
interface EducationAppDatabaseDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert (card: FlashcardData)

    @Delete
    suspend fun delete(card: FlashcardData)

    @Update
    suspend fun update(card: FlashcardData)

    @Query ("SELECT * FROM flashcard_table")
    fun getAll(): LiveData<List<FlashcardData>>


}