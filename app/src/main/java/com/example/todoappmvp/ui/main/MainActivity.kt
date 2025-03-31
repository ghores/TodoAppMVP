package com.example.todoappmvp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.data.repository.main.MainRepository
import com.example.todoappmvp.databinding.ActivityMainBinding
import com.example.todoappmvp.ui.add.AddFragment
import com.example.todoappmvp.utils.DELETE
import com.example.todoappmvp.utils.EDIT
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitView
        binding.apply {
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
                        Toast.makeText(this@MainActivity, "Edit", Toast.LENGTH_SHORT).show()
                    }

                    DELETE -> {
                        Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
}