package com.example.capstoneproject.ui.form.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.databinding.ActivityPaymentBinding
import com.example.capstoneproject.ui.process.ProcessAddActivity
import com.example.capstoneproject.ui.reduceFileImage
import com.example.capstoneproject.ui.uriToFile
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class ExtendPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: WorkshopViewModel by viewModels()

    private var currentImageUri: Uri? = null
    private var token: String = ""
    private var workshopId: Int = 0
    private var packageId: Int = 0
    private var packageName: String = ""
    private var packagePrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getExtra()
        setupPackage()
        btnGallery()
        setupActionBar()
        process()
    }

    private fun getExtra() {
        token = intent.getStringExtra("token").toString()
        workshopId = intent.getIntExtra("workshopId", 0)
        packageId = intent.getIntExtra("packageId", 0)
        packageName = intent.getStringExtra("packageName").toString()
        packagePrice = intent.getIntExtra("packagePrice", 0)
    }

    @SuppressLint("SetTextI18n")
    private fun setupPackage() {
        binding.tvItemId.text = packageId.toString()
        binding.tvItemNamapaket.text = packageName
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
        val formatterPrice = formatter.format(packagePrice)
        binding.tvItemHargapaket.text = "Rp.$formatterPrice"
    }


    private fun btnGallery() {
        binding.btnAdd.setOnClickListener {
            startGallery()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun process() {
        binding.btnProcess.setOnClickListener {
            showLoading(true)
            val packageIdRequestBody = packageId.toString().toRequestBody("text/plain".toMediaType())
            var multipartBody: MultipartBody.Part? = null
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                val file = imageFile.asRequestBody("image/jpeg".toMediaType())
                multipartBody = MultipartBody.Part.createFormData(
                    "bukti_pembayaran",
                    imageFile.name,
                    file
                )
            }
            lifecycleScope.launch {
                val isSuccess = viewModel.extendWorkshop(workshopId, packageIdRequestBody, multipartBody, token)
                if (!isSuccess) {
                    Log.d("Payment", "Failed to extend workshop")
                } else {
                    val intent = Intent(this@ExtendPaymentActivity, ProcessAddActivity::class.java)
                    startActivity(intent)
                    finish()
                    showToast("Success")
                }
                showLoading(false)
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            binding.ivPayment.setImageURI(uri)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}