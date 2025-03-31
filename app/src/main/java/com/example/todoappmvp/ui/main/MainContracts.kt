package com.example.todoappmvp.ui.main

import com.example.todoappmvp.base.BasePresenter
import com.example.todoappmvp.data.model.NoteEntity

interface MainContracts {
    interface View {
        fun showAllNotes(list: List<NoteEntity>)
        fun showEmpty()
        fun deleteMessage()
    }

    interface Presenter : BasePresenter {
        fun getAllNotes()
        fun deleteNote(noteEntity: NoteEntity)
    }
}