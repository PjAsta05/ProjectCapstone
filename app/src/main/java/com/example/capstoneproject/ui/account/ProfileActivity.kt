package com.example.capstoneproject.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityProfileBinding
import com.example.capstoneproject.ui.account.editprofile.EditProfileActivity
import com.example.capstoneproject.ui.form.workshop.AddWorkshopActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logout()
        editProfile()
        addWorkshop()
    }

    fun logout() {
        binding.btnLogout.setOnClickListener {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        }
    }
    fun editProfile() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Edit Berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    fun addWorkshop() {
        binding.btnaddws.setOnClickListener {
            val intent = Intent(this, AddWorkshopActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Add Workshop Berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}