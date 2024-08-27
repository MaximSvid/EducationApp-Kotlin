package com.example.educationappmaximsvidrak.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashcard_table",
    foreignKeys = [ForeignKey( //Обеспечивает целостность данных между таблицами flashcard_table и folder_table
        entity = Folder::class,
        parentColumns = ["id"],
        childColumns = ["folderId"],
        onDelete = ForeignKey.CASCADE // Если папка удалена, все связанные с ней заметки тоже будут удалены
    )]
)
class FlashcardData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var question: String,
    var answer: String,
    val folderId: Long,
    var studyDate: Long? = null
)