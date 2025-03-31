package com.example.todoappmvp.data.repository.main

import com.example.todoappmvp.data.database.NoteDao
import com.example.todoappmvp.data.model.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao) {
    fun getAllNotes(): Observable<List<NoteEntity>> = dao.getAllNotes()
    fun deleteNote(noteEntity: NoteEntity): Completable = dao.deleteNote(noteEntity)
}