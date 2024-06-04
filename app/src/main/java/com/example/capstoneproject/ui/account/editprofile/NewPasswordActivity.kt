package com.example.capstoneproject.ui.account.editprofile

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityNewPasswordBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.color.utilities.MaterialDynamicColors
import com.google.android.material.theme.overlay.MaterialThemeOverlay

class NewPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewPasswordBinding
    private var passwordValid = false
    private var passwordConfirmation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPasswordEditText()
        setConfirmPasswordEditText()
        setButtonEnable()
        setupActionBar()
    }

    private fun setPasswordEditText() {
        binding.etNewPassword.isValidCallback {
            passwordValid = it
            setButtonEnable()
        }
    }

    private fun setConfirmPasswordEditText() {
        binding.etConfirmPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateConfirmPassword()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun validateConfirmPassword() {
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        if (confirmPassword == newPassword) {
            passwordConfirmation = true
            binding.tilConfirmPassword.helperText = "Passwords match"
            binding.tilConfirmPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.success)))
            setButtonEnable()
        } else {
            passwordConfirmation = false
            binding.tilConfirmPassword.helperText = "Passwords do not match"
            binding.tilConfirmPassword.setHelperTextColor(ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.error)))
            setButtonEnable()
        }
    }

    private fun setButtonEnable() {
        binding.btnSave.isEnabled = passwordValid && passwordConfirmation
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}