package com.example.capstoneproject.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.databinding.ActivityResultBinding
import com.example.capstoneproject.ui.detail.tari.DetailDanceActivity
import com.example.capstoneproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private val viewModel: HomeViewModel by viewModels()

    private var currentImageUri: Uri? = null
    private var label: String? = null
    private var score: Int? = null
    private var token: String = ""
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
        token = intent.getStringExtra("token").toString()
        label = intent.getStringExtra("Label")
        score = intent.getIntExtra("Score", 0)
        getDanceData()
    }

    private fun getDanceData() {
        when (label) {
            "Baris" -> {
                findDance("tari-baris")
            }
            "Barong" -> {
                findDance("tari-barong")
            }
            "Condong" -> {
                findDance("tari-condong")
            }
            "Janger" -> {
                findDance("tari-janger")
            }
            "Kecak" -> {
                findDance("tari-kecak")
            }
            "Pendet_Penyambutan" -> {
                findDance("tari-pendet-penyambutan")
            }
            "Rejang_Sari" -> {
                findDance("tari-rejang-sari")
            }
        }
    }

    private fun findDance(dance: String) {
        showLoading(true)
        lifecycleScope.launch {
            val isSuccess = viewModel.findTari(dance, token)
            if (!isSuccess) {
                Log.d("ResultActivity", "findDance: $isSuccess")
            } else {
                observeDance()
            }
            showLoading(false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDance() {
        viewModel.balineseDance.observe(this) { dance ->
            if (dance != null) {
                binding.tvNameTari.text = dance.namaTari
                binding.tvPrecentace.text = "$score%"
                binding.descrtiption.text = dance.deskripsi
                binding.btnResult.setOnClickListener {
                    val intent = Intent(this, DetailDanceActivity::class.java)
                    intent.putExtra(DetailDanceActivity.INTENT_PARCELABLE, dance)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setImageView() {
        binding.ivResult.setImageURI(currentImageUri)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}