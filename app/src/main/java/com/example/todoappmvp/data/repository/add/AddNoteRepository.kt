package com.example.todoappmvp.data.repository.add

import com.example.todoappmvp.data.database.NoteDao
import com.example.todoappmvp.data.model.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AddNoteRepository @Inject constructor(private val dao: NoteDao) {
    fun saveNote(noteEntity: NoteEntity): Completable = dao.saveNote(noteEntity)
    fun getNote(id: Int): Observable<NoteEntity> = dao.getNote(id)
    fun updateNote(noteEntity: NoteEntity): Completable = dao.updateNote(noteEntity)
}