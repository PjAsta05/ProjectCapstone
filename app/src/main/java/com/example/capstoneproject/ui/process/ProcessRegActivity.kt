package com.example.capstoneproject.ui.process

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityProcessRegBinding
import com.example.capstoneproject.ui.MainActivity

class ProcessRegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProcessRegBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessRegBinding.inflate(layoutInflater)
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