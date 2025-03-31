package com.example.todoappmvp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.utils.NOTE_TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(noteEntity: NoteEntity): Completable

    @Delete
    fun deleteNote(noteEntity: NoteEntity): Completable

    @Update
    fun updateNote(noteEntity: NoteEntity): Completable

    @Query("SELECT * FROM $NOTE_TABLE_NAME")
    fun getAllNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE_NAME WHERE id ==:id")
    fun getNote(id: Int): Observable<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE_NAME WHERE priority ==:priority")
    fun filterNote(priority: String): Observable<List<NoteEntity>>
}