package com.example.capstoneproject.ui.form.packet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.ActivityPacketBinding
import com.example.capstoneproject.model.PackageResponse
import com.example.capstoneproject.ui.form.payment.ExtendPaymentActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExtendPackageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPacketBinding
    private val viewModel: PackageViewModel by viewModels()
    private lateinit var adapter: PackageAdapter

    private var workshopId: Int = 0
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPacketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        getExtra()
        getPackages()
    }

    private fun getExtra() {
        workshopId = intent.getIntExtra("workshopId", 0)
        token = intent.getStringExtra("token").toString()
    }

    private fun getPackages() {
        showLoading(true)
        lifecycleScope.launch {
            val isSuccess = viewModel.getPackages(token)
            if (!isSuccess) {
                Log.d("ExtendPackageActivity", "getPackages: Failed")
            } else {
                setupRecyclerView()
                Log.d("ExtendPackageActivity", "getPackages: Success")
            }
            showLoading(false)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        observePackage()
    }

    private fun observePackage() {
        viewModel.packages.observe(this) { list ->
            if (list != null) {
                adapter = PackageAdapter(list)
                adapter.setOnItemClickCallback(object: PackageAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: PackageResponse) {
                        val intent = Intent(this@ExtendPackageActivity, ExtendPaymentActivity::class.java)
                        intent.putExtra("workshopId", workshopId)
                        intent.putExtra("token", token)
                        intent.putExtra("packageId", data.id)
                        intent.putExtra("packageName", data.packageName)
                        intent.putExtra("packagePrice", data.price)
                        startActivity(intent)
                    }
                })
                binding.recyclerView.adapter = adapter
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

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}