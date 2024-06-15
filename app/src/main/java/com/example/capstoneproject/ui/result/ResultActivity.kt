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
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityResultBinding
import com.example.capstoneproject.ui.detail.tari.DetailDanceActivity
import com.example.capstoneproject.ui.home.HomeViewModel
import com.example.capstoneproject.ui.reduceFileImage
import com.example.capstoneproject.ui.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private val viewModel: HomeViewModel by viewModels()

    private var currentImageUri: Uri? = null
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
        token = intent.getStringExtra("Token").toString()
        classifyImage()
    }

    private fun classifyImage() {
        showLoading(true)
        var multipartBody: MultipartBody.Part? = null
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri,this).reduceFileImage()
            val file = imageFile.asRequestBody("image/jpeg".toMediaType())
            multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                file
            )
        }
        lifecycleScope.launch {
            val isSuccess = multipartBody?.let { viewModel.classifyTari(it) }
            isSuccess?.let {
                if (!isSuccess) {
                    binding.tvNameTari.text = getString(R.string.error)
                    binding.descrtiption.text = getString(R.string.error_classification)
                    binding.tvPercentage.visibility = View.GONE
                    binding.btnResult.text = getString(R.string.try_again)
                    binding.btnResult.setOnClickListener {
                        classifyImage()
                    }
                } else {
                    observeResult()
                }
                showLoading(false)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeResult() {
        viewModel.classification.observe(this) { classification ->
            if (classification != null) {
                if (classification.data.confidence.toInt() >= 70) {
                    when (classification.data.label) {
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
                        else -> {
                            setUnknown()
                        }
                    }
                    binding.tvPercentage.visibility = View.VISIBLE
                    score = classification.data.confidence.toInt()
                } else {
                    setUnknown()
                }
            } else {
                setUnknown()
            }
        }
    }

    private fun setUnknown() {
        binding.tvNameTari.text = getString(R.string.unknown)
        binding.tvScore.text = "0%"
        binding.descrtiption.text = getString(R.string.unknown_classification)
        binding.btnResult.isEnabled = false
    }

    private fun findDance(dance: String) {
        lifecycleScope.launch {
            val isSuccess = viewModel.findTari(dance, token)
            if (!isSuccess) {
                Log.d("ResultActivity", "findDance: $isSuccess")
            } else {
                observeDance()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDance() {
        viewModel.balineseDance.observe(this) { dance ->
            if (dance != null) {
                binding.tvNameTari.text = dance.namaTari
                binding.tvScore.text = "$score%"
                binding.descrtiption.text = dance.deskripsi
                binding.btnResult.text = getString(R.string.more_detail)
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
            binding.cardResult.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.cardResult.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }
}