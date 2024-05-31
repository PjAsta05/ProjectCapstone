package com.example.capstoneproject.ui.process

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityProcessaddBinding
import com.example.capstoneproject.ui.MainActivity

class ProcessAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProcessaddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessaddBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        backToMain()
    }

    private fun backToMain() {
        binding.btnBack.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}