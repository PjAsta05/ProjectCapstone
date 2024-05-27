package com.example.capstoneproject.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private var nameValid = false
    private var emailValid = false
    private var passwordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameValid()
        emailValid()
        passwordValid()
        buttonEnabled()
    }

    private fun nameValid() {
        binding.etName.addTextChangedListener {
            nameValid = binding.etName.text.toString().isNotEmpty()
            buttonEnabled()
        }
    }

    private fun emailValid() {
        binding.etEmail.addTextChangedListener {
            emailValid = binding.etEmail.text.toString().isNotEmpty()
            buttonEnabled()
        }
    }

    private fun passwordValid() {
        binding.etPassword.addTextChangedListener {
            passwordValid = binding.etPassword.text.toString().isNotEmpty()
            buttonEnabled()
        }
    }

    private fun buttonEnabled() {
        binding.btnLogin.isEnabled = emailValid && passwordValid
    }
}