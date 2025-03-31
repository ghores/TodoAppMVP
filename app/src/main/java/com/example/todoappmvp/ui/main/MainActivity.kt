package com.example.todoappmvp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoappmvp.R
import com.example.todoappmvp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitView
        binding.apply {

        }
    }
}