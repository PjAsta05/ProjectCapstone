package com.example.capstoneproject.ui.form.workshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.databinding.ActivityAddWorkshopBinding
import com.example.capstoneproject.ui.form.packet.PacketActivity

class AddWorkshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddWorkshopBinding

    private var namaValid = false
    private var ownerValid = false
    private var phoneValid = false
    private var emailValid = false
    private var addressValid = false
    private var descriptionValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        input()
        btnEnabled()
        btnPayment()
    }

    private fun input() {
        val nama = binding.etNama
        val owner = binding.etOwner
        val phone = binding.etPhone
        val email = binding.etEmail
        val address = binding.etAddress
        val description = binding.etDescription

        nama.addTextChangedListener {
            namaValid = it.toString().isNotEmpty()
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
            val emailText = it.toString()
            emailValid = isValidEmail(emailText)
            if (emailValid) {
                email.error = null
            } else {
                email.error = "Email tidak valid"
            }
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
        btn.isEnabled = namaValid && ownerValid && phoneValid && emailValid && addressValid && descriptionValid
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    fun btnPayment() {
        binding.btnPay.setOnClickListener {
            val intent = Intent(this, PacketActivity::class.java)
            startActivity(intent)
        }
    }
}