package com.example.capstoneproject.ui.account.editprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.databinding.ActivityEditProfileBinding
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.reduceFileImage
import com.example.capstoneproject.ui.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    private var id: Int = 0
    private var token: String = ""
    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prevData()
        updateUser()
        updatePassword()
        setupActionBar()
        addImage()
    }

    private fun prevData() {
        lifecycleScope.launch {
            viewModel.getSession().observe(this@EditProfileActivity) {
                binding.etName.setText(it.name)
                binding.etEmail.setText(it.email)
                id = it.id
                token = it.token
                binding.apply {
                    Glide.with(binding.root)
                        .load(it.photo)
                        .error(R.drawable.baseline_image_24)
                        .into(imgProfile)
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgProfile.setImageURI(it)
        }
    }

    private fun addImage() {
        binding.btnAdd.setOnClickListener {
            launcherGallery.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private fun updatePassword() {
        binding.createNewPassword.setOnClickListener {
            val intent = Intent(this, NewPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUser() {
        binding.btnSave.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()

            val emailRequestBody = email.toRequestBody("text/plain".toMediaType())
            val nameRequestBody = name.toRequestBody("text/plain".toMediaType())
            var multipartBody: MultipartBody.Part? = null
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                val file = imageFile.asRequestBody("image/jpeg".toMediaType())
                multipartBody = MultipartBody.Part.createFormData(
                    "url_gambar",
                    imageFile.name,
                    file
                )
            }
            lifecycleScope.launch {
                Log.d("Edit Profile", "email: $email, name: $name")
                val isSuccess = viewModel.updateUser(id, emailRequestBody, null, nameRequestBody, multipartBody, token)
                if (!isSuccess) {
                    viewModel.errorMessage.observe(this@EditProfileActivity) { message ->
                        Log.d("Edit Profile", "Error: $message")
                    }
                }else {
                    viewModel.updateResponse.observe(this@EditProfileActivity) { response ->
                        viewModel.updateSession(UserModel(response.id, response.fullName, response.email, response.photo,"", response.role))
                        finish()
                    }
                }
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