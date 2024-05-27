package com.example.capstoneproject.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var emailValid = false
    private var passwordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailValid()
        passwordValid()
        buttonEnabled()
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