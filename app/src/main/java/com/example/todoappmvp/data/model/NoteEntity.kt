package com.example.todoappmvp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoappmvp.utils.NOTE_TABLE_NAME

@Entity(tableName = NOTE_TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var desc: String = "",
    var category: String = "",
    var priority: String = ""
)
