package com.example.capstoneproject.ui.auth.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivitySigninBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding

    private var nameValid = false
    private var emailValid = false
    private var passwordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNameEditText()
        setEmailEditText()
        setPasswordEditText()
        setButtonEnable()
    }

    private fun setNameEditText() {
        binding.etName.isValidCallback {
            nameValid = it
            setButtonEnable()
        }
    }

    private fun setEmailEditText() {
        binding.etEmail.isValidCallback {
            emailValid = it
            setButtonEnable()
        }
    }

    private fun setPasswordEditText() {
        binding.etPassword.isValidCallback {
            passwordValid = it
            setButtonEnable()
        }
    }

    private fun setButtonEnable() {
        binding.btnSignin.isEnabled = nameValid && emailValid && passwordValid
    }
}