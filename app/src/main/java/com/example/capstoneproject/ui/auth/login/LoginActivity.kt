package com.example.capstoneproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.databinding.ActivityLoginBinding
import com.example.capstoneproject.ui.MainActivity
import com.example.capstoneproject.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var emailValid = false
    private var passwordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEmailEditText()
        setPasswordEditText()
        setButtonEnable()
        loginAction()
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
        val button = findViewById<Button>(R.id.btn_login)
        button.isEnabled = emailValid && passwordValid
    }

    private fun loginAction() {
        binding.btnLogin.setOnClickListener {
            //Loading
            val email = binding.etEmail.text
            val password = binding.etPassword.text

            lifecycleScope.launch {
                viewModel.saveSession(UserModel("1", "Puja", "$email", "$password"))
                navigateToMainActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}