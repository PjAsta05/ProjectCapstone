package com.example.capstoneproject.ui.account.editprofile

import android.os.Bundle
import android.text.Editable
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
        setLoadPrevUserData()
        saveUserData()
    }

    private fun setLoadPrevUserData() {
        val intent = intent
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")

        binding.etName.text = Editable.Factory.getInstance().newEditable(name ?: "")
        binding.etEmail.text = Editable.Factory.getInstance().newEditable(email ?: "")
    }

    private fun saveUserData() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()

            lifecycleScope.launch {
                viewModel.updateSession(UserModel("", name, email, ""))
                finish()
            }
        }
    }

}