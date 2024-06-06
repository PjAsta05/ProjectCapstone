package com.example.capstoneproject.ui.detail.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ActivityDetailAdminBinding
import com.example.capstoneproject.model.WorkshopResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        showDetailWorkshop()
        showProof()
    }

    private fun showDetailWorkshop() {
        val workshop = intent.getParcelableExtra<WorkshopResponse>(INTENT_PARCELABLE)
        workshop?.let {
            binding.apply {
                Glide.with(this@DetailAdminActivity)
                    .load(it.photo)
                    .into(imageView)

                textView2.text = it.workshopName
                etNama.setText(it.sanggarName)
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