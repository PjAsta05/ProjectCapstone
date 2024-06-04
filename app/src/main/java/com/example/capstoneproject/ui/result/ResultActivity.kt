package com.example.capstoneproject.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityResultBinding
import com.example.capstoneproject.ui.detail.tari.DetailDanceActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToResult()
    }

    private fun moveToResult() {
        val intent = Intent(this, DetailDanceActivity::class.java)
        startActivity(intent)
    }
}