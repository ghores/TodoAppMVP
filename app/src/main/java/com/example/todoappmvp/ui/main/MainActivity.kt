package com.example.todoappmvp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoappmvp.R
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.data.repository.main.MainRepository
import com.example.todoappmvp.databinding.ActivityMainBinding
import com.example.todoappmvp.ui.add.AddFragment
import com.example.todoappmvp.utils.ALL
import com.example.todoappmvp.utils.BUNDLE_ID
import com.example.todoappmvp.utils.DELETE
import com.example.todoappmvp.utils.EDIT
import com.example.todoappmvp.utils.HIGH
import com.example.todoappmvp.utils.LOW
import com.example.todoappmvp.utils.NORMAL
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {
    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var presenter: MainPresenter

    /*//Other
    private val presenter by lazy { MainPresenter(repository, this) }*/
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitView
        binding.apply {
            //Set action view
            setSupportActionBar(notesToolbar)
            //Note detail
            addNoteBtn.setOnClickListener {
                AddFragment().show(supportFragmentManager, AddFragment().tag)
            }
            //Get All Notes
            presenter.getAllNotes()
            //Clicks
            noteAdapter.setOnItemClickListener { noteEntity, state ->
                when (state) {
                    EDIT -> {
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID, noteEntity.id)
                        val fragment = AddFragment()
                        fragment.arguments = bundle
                        fragment.show(supportFragmentManager, AddFragment().tag)
                    }

                    DELETE -> {
                        presenter.deleteNote(noteEntity)
                    }
                }
            }
            //Filter
            notesToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionFilter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchNote(newText.toString())
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun showAllNotes(list: List<NoteEntity>) {
        binding.emptyLay.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE
        noteAdapter.setData(list)
        binding.noteList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun showEmpty() {
        binding.emptyLay.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }

    override fun deleteMessage() {
        Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun filterByPriority() {
        val priorities = arrayOf(ALL, HIGH, NORMAL, LOW)
        AlertDialog.Builder(this)
            .setSingleChoiceItems(priorities, selectedItem) { dialog, which ->
                when (which) {
                    0 -> {
                        presenter.getAllNotes()
                    }

                    in 1..3 -> {
                        presenter.filterNote(priorities[which])
                    }
                }
                selectedItem = which
                dialog.dismiss()
            }.create().show()
    }
}