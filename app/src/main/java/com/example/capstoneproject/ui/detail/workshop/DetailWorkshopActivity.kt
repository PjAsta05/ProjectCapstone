package com.example.capstoneproject.ui.detail.workshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ActivityDetailWorkshopBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.form.reg.RegFormActivity

class DetailWorkshopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailWorkshopBinding
    private var token: String = ""
    private var workshopId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailWorkshopBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)
        detail()
        setupActionBar()


    }

    private fun detail() {
        val workshop = intent.getParcelableExtra<WorkshopResponse>(INTENT_PARCELABLE)!!
        token = intent.getStringExtra("token") ?: ""
        workshopId = workshop.id
        workshop.let {
            binding.apply {
                nameworkshop.text = it.workshopName
                tvSanggarWorkshop.text = it.sanggarName
                tvOwnerWorkshop.text = it.owner
                tvAddressWorkshop.text = it.address
                tvEmailWorkshop.text = it.email
                tvPhoneWorkshop.text = it.phone
                tvDescriptionWorkshop.text = it.description
                tvPrice.text = it.price.toString()

                Glide.with(this@DetailWorkshopActivity)
                    .load(it.photo)
                    .into(ivDetailWorkshop)
            }
        }
        showLoading(false)

        binding.btnReg.setOnClickListener {
            val intent = Intent(this, RegFormActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("workshopId", workshopId)
            intent.putExtra("workshopName", workshop.workshopName)
            Log.d("token&workshopId", "$token $workshopId")
            startActivity(intent)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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