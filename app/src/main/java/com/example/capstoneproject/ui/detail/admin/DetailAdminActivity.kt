package com.example.capstoneproject.ui.detail.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityDetailAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}