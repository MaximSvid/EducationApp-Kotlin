package com.example.educationappmaximsvidrak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_table")
class FlashcardData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var question: String,
    var answer: String
)