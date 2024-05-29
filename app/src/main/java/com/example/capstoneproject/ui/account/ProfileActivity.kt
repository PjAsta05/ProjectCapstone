package com.example.capstoneproject.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityProfileBinding
import com.example.capstoneproject.ui.WelcomeActivity
import com.example.capstoneproject.ui.account.editprofile.EditProfileActivity
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.form.workshop.AddWorkshopActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        editProfile()
        addWorkshop()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            navigateToWelcomeActivity()
        }
    }
    private fun editProfile() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Edit Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addWorkshop() {
        binding.btnaddws.setOnClickListener {
            val intent = Intent(this, AddWorkshopActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Add Workshop Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}