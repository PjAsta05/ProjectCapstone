package com.example.capstoneproject.ui.detail.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ActivityProofBinding

class ProofActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProofBinding
    private var proof: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProofBinding.inflate(layoutInflater)
        setContentView(binding.root)
        proof = intent.getStringExtra("proof")
        setupActionBar()
        displayProof()
    }

    private fun displayProof() {
        binding.apply {
            Glide.with(this@ProofActivity)
                .load(proof)
                .into(proofImage)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}