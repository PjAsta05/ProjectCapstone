package com.example.capstoneproject.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.databinding.ActivitySigninBinding
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
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
        signIn()
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

    private fun signIn() {
        binding.btnSignin.setOnClickListener {
            //loading
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val fullName = binding.etName.text.toString()
            lifecycleScope.launch {
                val isSuccess = viewModel.signIn(email, password, fullName)
                if (!isSuccess) {
                    Log.d("Failed", "Email already in use")
                }else {
                    Log.d("Success", "Registration successful!")
                    navigateToLoginActivity()
                }
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}