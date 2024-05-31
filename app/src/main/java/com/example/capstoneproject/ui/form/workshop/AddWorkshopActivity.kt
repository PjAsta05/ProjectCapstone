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
    private var ownerValid = false
    private var phoneValid = false
    private var emailValid = false
    private var addressValid = false
    private var descriptionValid = false

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnGallery()
        input()
        btnEnabled()
        btnPayment()
        setupActionBar()
    }

    private fun input() {
        val nama = binding.etNama
        val owner = binding.etOwner
        val phone = binding.etPhone
        val email = binding.etEmail
        val address = binding.etAddress
        val description = binding.etDescription


        nama.addTextChangedListener {
            nameValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        owner.addTextChangedListener {
            ownerValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        phone.addTextChangedListener {
            phoneValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        email.addTextChangedListener {
            emailValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        address.addTextChangedListener {
            addressValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        description.addTextChangedListener {
            descriptionValid = it.toString().isNotEmpty()
            btnEnabled()
        }
    }

    private fun btnEnabled() {
        val btn = binding.btnPay
        btn.isEnabled = nameValid && ownerValid && phoneValid && emailValid && addressValid && descriptionValid
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
                startActivity(Intent(this, PacketActivity::class.java))
            }
            .setNegativeButton("Back") { dialog, id ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Confirm Details")
        alert.show()
    }


    private fun com.google.android.material.textfield.TextInputEditText.addValidation(validation: (String) -> Unit) {
        addTextChangedListener {
            validation(it.toString())
            btnEnabled()
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