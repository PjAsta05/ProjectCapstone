package com.example.capstoneproject.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
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

    private var name: String? = ""
    private var email: String? = ""
    private var photoUrl: String? = ""
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        editProfile()
        addWorkshop()
        observeSession()
        setupActionBar()
    }

    override fun onResume() {
        super.onResume()
        observeSession()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            name = user.name
            email = user.email
            photoUrl = user.photo
            token = user.token
            setUpProfile()
        }
    }

    private fun setUpProfile() {
        binding.name.text = name
        binding.email.text = email
        binding.apply {
            Glide.with(binding.root)
                .load(photoUrl)
                .error(R.drawable.baseline_image_24)
                .into(imageProfile)
        }
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