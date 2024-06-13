package com.example.capstoneproject.ui.process

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityProcessaddBinding
import com.example.capstoneproject.ui.MainActivity

class ProcessAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProcessaddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessaddBinding.inflate(layoutInflater)
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