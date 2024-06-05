package com.example.capstoneproject.ui.form.packet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.databinding.ActivityPacketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PacketActivity : AppCompatActivity() {
    private val viewModel: PackageViewModel by viewModels()
    private lateinit var binding: ActivityPacketBinding
    private var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPacketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        getPackages(token)

//        viewModel.packages.observe(this, { packages ->
//
//            packageAdapter.submitList(packages)
//        })
    }

    private fun getPackages(token: String) {
        lifecycleScope.launch {
            viewModel.getPackages(token)
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