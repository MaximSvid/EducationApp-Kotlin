package com.example.educationappmaximsvidrak.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.educationappmaximsvidrak.model.FlashcardData
import com.example.educationappmaximsvidrak.model.Folder

@Database (entities = [FlashcardData::class, Folder::class], version = 2)
abstract class FlashcardDatabase: RoomDatabase() {

    abstract val flashcardDAO: EducationAppDatabaseDao

}

private lateinit var INSTANCE: FlashcardDatabase

fun getDatabase (context: Context): FlashcardDatabase {
    synchronized(FlashcardDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                FlashcardDatabase::class.java,
                "flashcard_database"
            )
//                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}