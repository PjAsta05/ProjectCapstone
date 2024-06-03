package com.example.capstoneproject.ui.detail.tari

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityDetailTariBinding

class DetailTariActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTariBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTariBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}