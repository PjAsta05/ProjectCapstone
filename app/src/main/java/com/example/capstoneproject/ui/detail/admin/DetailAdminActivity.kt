package com.example.capstoneproject.ui.detail.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ActivityDetailAdminBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class DetailAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAdminBinding
    private val viewModel: WorkshopViewModel by viewModels()

    private var id: Int = 0
    private var packageId = 0
    private var userId = 0
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        token = intent.getStringExtra("token").toString()
        setupActionBar()
        showDetailWorkshop()
        showProof()
        editWorkshop()
        deleteAction()
    }

    private fun showDetailWorkshop() {
        val workshop = intent.getParcelableExtra<WorkshopResponse>(INTENT_PARCELABLE)
        workshop?.let {
            binding.apply {
                Glide.with(this@DetailAdminActivity)
                    .load(it.photo)
                    .into(imageView)
                id = it.id
                packageId = it.paketId
                userId = it.userId
                textView2.text = it.workshopName
                etWorkshop.setText(it.sanggarName)
                etOwner.setText(it.owner)
                etPhone.setText(it.phone)
                etEmail.setText(it.email)
                etAddress.setText(it.address)
                etDescription.setText(it.description)
                etPrice.setText(it.price.toString())
                when(it.status) {
                    "success" -> binding.activeStatus.isChecked = true
                    "done" -> binding.inactiveStatus.isChecked = true
                    else -> binding.pendingStatus.isChecked = true
                }
            }
        }
    }

    private fun showProof() {
        binding.proof.setOnClickListener {
            val proof = intent.getParcelableExtra<WorkshopResponse>(INTENT_PARCELABLE)
            proof?.let {
                val intent = Intent(this, ProofActivity::class.java)
                intent.putExtra("proof", it.proof)
                startActivity(intent)
            }
        }
    }

    private fun editWorkshop() {
        val packageIdRequestBody = packageId.toString().toRequestBody("text/plain".toMediaType())
        val userIdRequestBody = userId.toString().toRequestBody("text/plain".toMediaType())
        val workshopRequestBody = binding.etWorkshop.text.toString().toRequestBody("text/plain".toMediaType())
        val sanggarRequestBody = binding.etOwner.text.toString().toRequestBody("text/plain".toMediaType())
        val addressRequestBody = binding.etAddress.text.toString().toRequestBody("text/plain".toMediaType())
        val emailRequestBody = binding.etEmail.text.toString().toRequestBody("text/plain".toMediaType())
        val phoneRequestBody = binding.etPhone.text.toString().toRequestBody("text/plain".toMediaType())
        val ownerRequestBody = binding.etOwner.text.toString().toRequestBody("text/plain".toMediaType())
        val descriptionRequestBody = binding.etDescription.text.toString().toRequestBody("text/plain".toMediaType())
        val priceRequestBody = binding.etPrice.text.toString().toRequestBody("text/plain".toMediaType())
        val statusRequestBody = when {
            binding.activeStatus.isChecked -> "success"
            binding.inactiveStatus.isChecked -> "done"
            else -> "pending"
        }.toRequestBody("text/plain".toMediaType())
        binding.btnConfirm.setOnClickListener {
            lifecycleScope.launch {
                val isSuccess = viewModel.updateWorkshop(id, packageIdRequestBody, userIdRequestBody, workshopRequestBody, sanggarRequestBody, addressRequestBody, emailRequestBody, phoneRequestBody, ownerRequestBody, descriptionRequestBody, priceRequestBody, statusRequestBody, null, null, token)
                if (!isSuccess) {
                    Log.d("DetailAdminActivity", "Error")
                } else {
                    finish()
                }
            }
        }
    }

    private fun deleteAction() {
        binding.btnDelete.setOnClickListener {
            showAlert()
        }
    }
    private fun deleteWorkshop() {
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                val isSuccess = viewModel.deleteWorkshop(id, token)
                if (!isSuccess) {
                    Log.d("DetailAdminActivity", "Error")
                } else {
                    finish()
                }
            }
        }
    }

    private fun showAlert() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this workshop?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                deleteWorkshop()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Delete Workshop")
        alert.show()
    }
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}