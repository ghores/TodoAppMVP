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

    //Other
    private val presenter by lazy { AddPresenter(repository, this) }
    private lateinit var categoriesList: Array<String>
    private var category = ""
    private lateinit var prioritiesList: Array<String>
    private var priority = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitView
        binding.apply {
            closeImg.setOnClickListener {
                dismiss()
            }
            //Spinner
            categoriesSpinnerItems()
            prioritiesSpinnerItems()
            //Save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                //Entity
                noteEntity.id = 0
                noteEntity.title = title
                noteEntity.desc = desc
                noteEntity.category = category
                noteEntity.priority = priority
                presenter.saveNote(noteEntity)
            }
        }
    }

    private fun categoriesSpinnerItems() {
        categoriesList = arrayOf("Work", "Home", "Shopping", "Education", "Health")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener = object :OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categoriesList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun prioritiesSpinnerItems() {
        prioritiesList = arrayOf("High", "Normal", "Low")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioritiesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener = object :OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                priority = prioritiesList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun close() {
        dismiss()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}