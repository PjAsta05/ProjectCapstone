package com.example.capstoneproject.ui.detail.tari

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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
        showLoading(true)
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
            val link = it.urlVideo
            binding.playButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                startActivity(intent)
            }
        }
        showLoading(false)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }
}