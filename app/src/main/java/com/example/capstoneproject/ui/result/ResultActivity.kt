package com.example.capstoneproject.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityResultBinding
import com.example.capstoneproject.ui.detail.tari.DetailTariActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToResult()
    }

    private fun moveToResult() {
        val intent = Intent(this, DetailTariActivity::class.java)
        startActivity(intent)
    }
}