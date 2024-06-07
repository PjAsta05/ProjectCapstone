package com.example.capstoneproject.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityResultBinding
import com.example.capstoneproject.ui.detail.tari.DetailDanceActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private var currentImageUri: Uri? = null
    private var label: String? = null
    private var score: Float? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtra()
        setImageView()
    }

    private fun getExtra() {
        val image = intent.getStringExtra("Image")
        currentImageUri = Uri.parse(image)
        label = intent.getStringExtra("Label")
        score = intent.getFloatExtra("Score", 0.0f)
    }

    @SuppressLint("SetTextI18n")
    private fun setImageView() {
        binding.ivResult.setImageURI(currentImageUri)
        binding.tvNameTari.text = label
        val percentage = (score!! * 100).toInt()
        binding.tvPrecentace.text = "${percentage}%"
    }

    private fun moveToResult() {
        val intent = Intent(this, DetailDanceActivity::class.java)
        startActivity(intent)
    }
}