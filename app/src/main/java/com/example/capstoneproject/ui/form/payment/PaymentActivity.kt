package com.example.capstoneproject.ui.form.payment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: WorkshopViewModel by viewModels()

    private var currentImageUri: Uri? = null
    private var photo: Uri? = null
    private var uri: String = ""
    private var workshop: String = ""
    private var sanggar: String = ""
    private var owner: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var address: String = ""
    private var description: String = ""
    private var price: Int = 0
    private var token: String = ""
    private var userId: Int = 0
    private var packageId: Int = 0
    private var packageName: String = ""
    private var packagePrice: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getArguments()
        setupPackage()
        btnGallery()
        setupActionBar()
        process()
    }

    private fun getArguments(){
        uri = intent.extras?.getString("photo").toString()
        photo = uri.let { Uri.parse(it) }
        workshop = intent.getStringExtra("workshop").toString()
        sanggar = intent.getStringExtra("sanggar").toString()
        owner = intent.getStringExtra("owner").toString()
        email = intent.getStringExtra("email").toString()
        phone = intent.getStringExtra("phone").toString()
        address = intent.getStringExtra("address").toString()
        description = intent.getStringExtra("description").toString()
        price = intent.getStringExtra("price").toString().toInt()
        token = intent.getStringExtra("token").toString()
        userId = intent.getIntExtra("id", 0)
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

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            binding.ivPayment.setImageURI(uri)
        }
    }

    private fun process() {
        binding.btnProcess.setOnClickListener {
            val packageIdRequestBody = packageId.toString().toRequestBody("text/plain".toMediaType())
            val userIdRequestBody = userId.toString().toRequestBody("text/plain".toMediaType())
            val workshopRequestBody = workshop.toRequestBody("text/plain".toMediaType())
            val sanggarRequestBody = sanggar.toRequestBody("text/plain".toMediaType())
            val addressRequestBody = address.toRequestBody("text/plain".toMediaType())
            val emailRequestBody = email.toRequestBody("text/plain".toMediaType())
            val phoneRequestBody = phone.toRequestBody("text/plain".toMediaType())
            val ownerRequestBody = owner.toRequestBody("text/plain".toMediaType())
            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
            val priceRequestBody = price.toString().toRequestBody("text/plain".toMediaType())

            var multipartBody1: MultipartBody.Part? = null
            var multipartBody2: MultipartBody.Part? = null
            photo?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                val file = imageFile.asRequestBody("image/jpeg".toMediaType())
                multipartBody1 = MultipartBody.Part.createFormData(
                    "url_gambar",
                    imageFile.name,
                    file
                )
            }
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                val file = imageFile.asRequestBody("image/jpeg".toMediaType())
                multipartBody2 = MultipartBody.Part.createFormData(
                    "bukti_pembayaran",
                    imageFile.name,
                    file
                )
            }
            Log.d("PaymentActivity","$packageId, $userId, $workshop, $sanggar, $address, $email, $phone, $owner, $description, $price, $multipartBody1, $multipartBody2")
            lifecycleScope.launch {
                val isSuccess = viewModel.addWorkshop(packageIdRequestBody, userIdRequestBody, workshopRequestBody, sanggarRequestBody, addressRequestBody, emailRequestBody, phoneRequestBody, ownerRequestBody, descriptionRequestBody, priceRequestBody, multipartBody1, multipartBody2, token)
                if (!isSuccess) {
                    Log.d("PaymentActivity", "Failed to add workshop")
                } else {
                    val intent = Intent(this@PaymentActivity, ProcessAddActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
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