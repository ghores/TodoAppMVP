package com.example.todoappmvp.ui.add

import com.example.todoappmvp.base.BasePresenterImpl
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.data.repository.add.AddNoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddPresenter @Inject constructor(
    private val repository: AddNoteRepository,
    private val view: AddContracts.View
) : BasePresenterImpl(), AddContracts.Presenter {
    override fun saveNote(noteEntity: NoteEntity) {
        disposable = repository.saveNote(noteEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.close()
            }
    }

    override fun detailNote(id: Int) {
        disposable = repository.detailNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.loadNote(it)
            }
    }
}