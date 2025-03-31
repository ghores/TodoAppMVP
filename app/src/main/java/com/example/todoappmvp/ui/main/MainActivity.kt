package com.example.todoappmvp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.data.repository.main.MainRepository
import com.example.todoappmvp.databinding.ActivityMainBinding
import com.example.todoappmvp.ui.add.AddFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {
    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: MainRepository

    //Other
    private val presenter by lazy { MainPresenter(repository, this) }
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
        }
    }

    override fun showAllNotes(list: List<NoteEntity>) {
        binding.emptyLay.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE
        Toast.makeText(this, "${list.size}", Toast.LENGTH_SHORT).show()
    }

    override fun showEmpty() {
        binding.emptyLay.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }
}