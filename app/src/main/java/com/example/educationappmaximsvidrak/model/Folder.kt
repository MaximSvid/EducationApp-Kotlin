package com.example.educationappmaximsvidrak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "folder_table")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)
