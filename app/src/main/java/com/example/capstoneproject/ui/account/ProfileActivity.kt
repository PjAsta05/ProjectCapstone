package com.example.capstoneproject.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityProfileBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.WelcomeActivity
import com.example.capstoneproject.ui.account.editprofile.EditProfileActivity
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.form.workshop.AddWorkshopActivity
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    private val viewModel2: WorkshopViewModel by viewModels()
    private lateinit var adapter: ProfileWorkshopAdapter

    private var id: Int? = 0
    private var name: String? = ""
    private var email: String? = ""
    private var photoUrl: String? = ""
    private lateinit var token: String

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
            id = user.id
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
        getWorkshop()
    }

    private fun getWorkshop() {
        Log.d("Token", token)
        lifecycleScope.launch {
            val isSuccess = viewModel2.getWorkshops(null, id, token)
            if (!isSuccess) {
                Log.d("ProfileActivity", "Failed to get workshops")
            } else {
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        observeWorkshop()
    }

    private fun observeWorkshop() {
        viewModel2.workshops.observe(this) { list ->
            if (list != null) {
                adapter = ProfileWorkshopAdapter(list)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            navigateToWelcomeActivity()
            showToast("Logout Success")
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
            intent.putExtra("token", token)
            intent.putExtra("userId", id)
            startActivity(intent)
        }
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}