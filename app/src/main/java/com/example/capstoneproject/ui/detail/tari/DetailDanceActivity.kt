package com.example.capstoneproject.ui.detail.tari

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ActivityDetailTariBinding
import com.example.capstoneproject.model.BalineseDance

class DetailDanceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTariBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTariBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupActionBar()
        showDetailTari()
    }

    private fun showDetailTari() {
        val balineseDance = intent.getParcelableExtra<BalineseDance>(INTENT_PARCELABLE)
        balineseDance?.let {
            binding.apply {
                nametari.text = it.namaTari
                origintari.text = it.asalTari
                descriptiontari.text = it.deskripsi

                Glide.with(this@DetailDanceActivity)
                    .load(it.urlGambar)
                    .into(image)
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