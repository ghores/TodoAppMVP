package com.example.todoappmvp.ui.add

import com.example.todoappmvp.data.model.NoteEntity

interface AddContracts {
    interface View {
        fun close()
    }

    interface Presenter {
        fun saveNote(noteEntity: NoteEntity)
    }
}