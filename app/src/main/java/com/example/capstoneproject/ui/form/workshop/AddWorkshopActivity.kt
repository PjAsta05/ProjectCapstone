package com.example.capstoneproject.ui.form.workshop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.databinding.ActivityAddWorkshopBinding
import com.example.capstoneproject.ui.form.packet.PacketActivity

class AddWorkshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWorkshopBinding

    private var nameValid = false
    private var sanggarValid = false
    private var ownerValid = false
    private var phoneValid = false
    private var emailValid = false
    private var addressValid = false
    private var descriptionValid = false
    private var priceValid = false
    private var currentImageUri: Uri? = null
    private var token: String = ""
    private var userId: Int? = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getTokenAndId()
        btnGallery()
        input()
        btnEnabled()
        btnPayment()
        setupActionBar()
    }

    private fun getTokenAndId() {
        token = intent.getStringExtra("token").toString()
        userId = intent.getIntExtra("userId", 0)
    }

    private fun input() {
        binding.etWorkshop.isValidCallback {
            nameValid = it
            btnEnabled()
        }
        binding.etSanggar.isValidCallback {
            sanggarValid = it
            btnEnabled()
        }
        binding.etOwner.isValidCallback {
            ownerValid = it
            btnEnabled()
        }
        binding.etEmail.isValidCallback {
            emailValid = it
            btnEnabled()
        }
        binding.etPhone.isValidCallback {
            phoneValid = it
            btnEnabled()
        }
        binding.etAddress.isValidCallback {
            addressValid = it
            btnEnabled()
        }
        binding.etDescription.isValidCallback {
            descriptionValid = it
            btnEnabled()
        }
        binding.etPrice.isValidCallback {
            priceValid = it
            btnEnabled()
        }
    }

    private fun btnEnabled() {
        binding.btnPay.isEnabled = nameValid && sanggarValid && ownerValid && phoneValid && emailValid && addressValid && descriptionValid
    }

    private fun btnPayment() {
        binding.btnPay.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun btnGallery() {
        binding.btnadd.setOnClickListener {
            startGallery()
        }
    }

    private fun showConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are all the details correct?")
            .setCancelable(false)
            .setPositiveButton("Continue") { dialog, id ->
                val intent = Intent(this, PacketActivity::class.java)
                intent.putExtra("photo", currentImageUri.toString())
                intent.putExtra("workshop", binding.etWorkshop.text.toString())
                intent.putExtra("sanggar", binding.etSanggar.text.toString())
                intent.putExtra("owner", binding.etOwner.text.toString())
                intent.putExtra("phone", binding.etPhone.text.toString())
                intent.putExtra("email", binding.etEmail.text.toString())
                intent.putExtra("address", binding.etAddress.text.toString())
                intent.putExtra("description", binding.etDescription.text.toString())
                intent.putExtra("price", binding.etPrice.text.toString())
                intent.putExtra("token", token)
                intent.putExtra("id", userId)
                startActivity(intent)
            }
            .setNegativeButton("Back") { dialog, id ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Confirm Details")
        alert.show()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            Log.d("Image URI", "showImage: $uri")
            binding.ivWorkshop.setImageURI(uri)
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