package com.example.capstoneproject.ui.form.reg

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityEditProfileBinding
import com.example.capstoneproject.databinding.ActivityRegFormBinding
import com.example.capstoneproject.ui.process.ProcessRegActivity

class RegFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegFormBinding

    private var nameValid = false
    private var genderValid = false
    private var phoneValid = false
    private var emailValid = false
    private var ageValid = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegFormBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupActionBar()
        btnEnabled()
        process()
    }

    private fun input() {
        val name = binding.etNama
        val email = binding.etEmail
        val phone = binding.etPhone
        val gender = binding.etGender
        val age = binding.etAge

        name.addTextChangedListener {
            nameValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        email.addTextChangedListener {
            emailValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        phone.addTextChangedListener {
            phoneValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        gender.addTextChangedListener {
            genderValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        age.addTextChangedListener {
            ageValid = it.toString().isNotEmpty()
            btnEnabled()
        }
    }

    private fun btnEnabled() {
        binding.btnReg.isEnabled = nameValid && emailValid && phoneValid && genderValid && ageValid
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun process() {
        binding.btnReg.setOnClickListener {
            val intent = Intent(this, ProcessRegActivity::class.java)
            startActivity(intent)
        }
    }
}