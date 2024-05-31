package com.example.capstoneproject.ui.form.packet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityPacketBinding


class PacketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPacketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPacketBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}