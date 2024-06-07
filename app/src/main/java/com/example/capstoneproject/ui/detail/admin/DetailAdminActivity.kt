package com.example.capstoneproject.ui.detail.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
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
    private var status: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        token = intent.getStringExtra("token").toString()
        setupActionBar()
        showDetailWorkshop()
        showProof()
        radioGroup()
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
                etWorkshop.setText(it.workshopName)
                etSanggar.setText(it.sanggarName)
                etOwner.setText(it.owner)
                etPhone.setText(it.phone)
                etEmail.setText(it.email)
                etAddress.setText(it.address)
                etDescription.setText(it.description)
                etPrice.setText(it.price.toString())
                tvItemId.text = it.paketId.toString()
                tvPacketName.text = it.paket.packageName
                tvPrice.text = it.paket.price.toString()
                when(it.status) {
                    "success" -> binding.activeStatus.isChecked = true
                    "done" -> binding.inactiveStatus.isChecked = true
                    else -> binding.pendingStatus.isChecked = true
                }
            }
        }
    }

    private fun radioGroup() {
        binding.status.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.active_status -> {
                        status = "success"
                    }
                    R.id.inactive_status -> {
                        status = "done"
                    }
                    R.id.pending_status -> {
                        status = "pending"
                    }
                }
            }
        })
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
        binding.btnConfirm.setOnClickListener {
            val workshop = binding.etWorkshop.text.toString()
            val sanggar = binding.etSanggar.text.toString()
            val address = binding.etAddress.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val owner = binding.etOwner.text.toString()
            val description = binding.etDescription.text.toString()
            val price = binding.etPrice.text.toString()

            val packageIdRequestBody = packageId.toString().toRequestBody("text/plain".toMediaType())
            val userIdRequestBody = userId.toString().toRequestBody("text/plain".toMediaType())
            val workshopRequestBody = workshop.toRequestBody("text/plain".toMediaType())
            val sanggarRequestBody = sanggar.toRequestBody("text/plain".toMediaType())
            val addressRequestBody = address.toRequestBody("text/plain".toMediaType())
            val emailRequestBody = email.toRequestBody("text/plain".toMediaType())
            val phoneRequestBody = phone.toRequestBody("text/plain".toMediaType())
            val ownerRequestBody = owner.toRequestBody("text/plain".toMediaType())
            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
            val priceRequestBody = price.toRequestBody("text/plain".toMediaType())
            val statusRequestBody = status.toRequestBody("text/plain".toMediaType())
            Log.d("Update Workshop","$id, $packageId, $userId, $workshop, $sanggar, $address, $email, $phone, $owner, $description, $price, $status, $token")
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
        lifecycleScope.launch {
            val isSuccess = viewModel.deleteWorkshop(id, token)
            if (!isSuccess) {
                Log.d("DetailAdminActivity", "Error")
            } else {
                finish()
            }
        }
    }

    private fun showAlert() {
        binding.btnDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Are you sure you want to delete this workshop?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    deleteWorkshop()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = dialogBuilder.create()
            alert.setTitle("Delete Workshop")
            alert.show()
        }
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