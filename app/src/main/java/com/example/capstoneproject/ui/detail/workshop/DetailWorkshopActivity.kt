package com.example.capstoneproject.ui.detail.workshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityDetailWorkshopBinding
import com.example.capstoneproject.model.WorkshopResponse

class DetailWorkshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailWorkshopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workshopData = intent.getParcelableExtra<WorkshopResponse>(INTENT_PARCELABLE)
        if (workshopData != null) {
        }

    }

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}