package com.example.todoappmvp.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.example.todoappmvp.data.model.NoteEntity
import com.example.todoappmvp.data.repository.add.AddNoteRepository
import com.example.todoappmvp.databinding.FragmentAddBinding
import com.example.todoappmvp.utils.BUNDLE_ID
import com.example.todoappmvp.utils.EDIT
import com.example.todoappmvp.utils.EDUCATION
import com.example.todoappmvp.utils.HEALTH
import com.example.todoappmvp.utils.HIGH
import com.example.todoappmvp.utils.HOME
import com.example.todoappmvp.utils.LOW
import com.example.todoappmvp.utils.NEW
import com.example.todoappmvp.utils.NORMAL
import com.example.todoappmvp.utils.WORK
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment(), AddContracts.View {
    //Binding
    private lateinit var binding: FragmentAddBinding

    @Inject
    lateinit var noteEntity: NoteEntity

    @Inject
    lateinit var repository: AddNoteRepository

    @Inject
    lateinit var presenter: AddPresenter

    //Other
    //private val presenter by lazy { AddPresenter(repository, this) }
    private lateinit var categoriesList: Array<String>
    private var category = ""
    private lateinit var prioritiesList: Array<String>
    private var priority = ""
    private var noteId = 0
    private var type = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteId > 0) {
            EDIT
        } else {
            NEW
        }
        //InitView
        binding.apply {
            closeImg.setOnClickListener {
                dismiss()
            }
            //Spinner
            categoriesSpinnerItems()
            prioritiesSpinnerItems()
            //Set default value
            if (type == EDIT) {
                presenter.detailNote(noteId)
            }
            //Save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                //Entity
                //noteEntity.id = 0
                noteEntity.id = noteId
                noteEntity.title = title
                noteEntity.desc = desc
                noteEntity.category = category
                noteEntity.priority = priority
                if (type == NEW) {
                    presenter.saveNote(noteEntity)
                } else {
                    presenter.updateNote(noteEntity)
                }
            }
        }
    }

    private fun categoriesSpinnerItems() {
        categoriesList = arrayOf(WORK, HOME, EDUCATION, HEALTH)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = categoriesList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun prioritiesSpinnerItems() {
        prioritiesList = arrayOf(HIGH, NORMAL, LOW)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioritiesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                priority = prioritiesList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun close() {
        dismiss()
    }

    override fun loadNote(noteEntity: NoteEntity) {
        if (this.isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    titleEdt.setText(noteEntity.title)
                    descEdt.setText(noteEntity.desc)
                    categoriesSpinner.setSelection(getIndex(categoriesList, noteEntity.category))
                    prioritySpinner.setSelection(getIndex(prioritiesList, noteEntity.priority))
                }
            }
        }
    }

    private fun getIndex(list: Array<String>, item: String): Int {
        var index = 0
        //for(i in 0 until list.size)
        for (i in list.indices) {
            if (list[i] == item) {
                index = i
                break
            }
        }
        return index
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}