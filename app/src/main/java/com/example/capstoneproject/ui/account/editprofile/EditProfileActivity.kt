package com.example.capstoneproject.ui.account.editprofile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.databinding.ActivityEditProfileBinding
import com.example.capstoneproject.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prevData()
        updateUser()
        setupActionBar()
    }

    private fun prevData() {
        lifecycleScope.launch {
            viewModel.getSession().observe(this@EditProfileActivity) {
                binding.etName.setText(it.name)
                binding.etEmail.setText(it.email)
                //Glide load photo url
            }
        }
    }

    private fun updateUser() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()

            lifecycleScope.launch {
                //Update user
                viewModel.updateSession(UserModel(0, name, email, "", ""))
                finish()
            }
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