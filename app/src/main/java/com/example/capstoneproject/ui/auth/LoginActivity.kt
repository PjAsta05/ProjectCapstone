package com.example.capstoneproject.ui.auth

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.capstoneproject.R

class LoginActivity : AppCompatActivity() {
    private var emailValid = false
    private var passwordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        email.addTextChangedListener {
            Log.d("email", emailValid.toString())
            emailValid = email.text.toString().isNotEmpty()
            buttonEnabled()
        }
        password.addTextChangedListener {
            Log.d("password", passwordValid.toString())
            passwordValid = password.text.toString().isNotEmpty()
            buttonEnabled()
        }
        buttonEnabled()
    }

    private fun buttonEnabled() {
        val button = findViewById<Button>(R.id.btn_login)
        button.isEnabled = emailValid && passwordValid
    }
}