package com.example.capstoneproject.ui.form.packet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.ActivityPacketBinding
import com.example.capstoneproject.model.PackageResponse
import com.example.capstoneproject.ui.form.payment.PaymentActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PacketActivity : AppCompatActivity() {
    private val viewModel: PackageViewModel by viewModels()
    private lateinit var binding: ActivityPacketBinding
    private lateinit var adapter: PackageAdapter

    private var photo: Uri? = null
    private var uri: String = ""
    private var workshop: String = ""
    private var sanggar: String = ""
    private var owner: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var address: String = ""
    private var description: String = ""
    private var price: String = ""
    private var token: String = ""
    private var userId: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPacketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        getArguments()
        getPackages(token)
    }

    private fun getArguments(){
        uri = intent.getStringExtra("photo").toString()
        workshop = intent.getStringExtra("workshop").toString()
        sanggar = intent.getStringExtra("sanggar").toString()
        owner = intent.getStringExtra("owner").toString()
        email = intent.getStringExtra("email").toString()
        phone = intent.getStringExtra("phone").toString()
        address = intent.getStringExtra("address").toString()
        description = intent.getStringExtra("description").toString()
        price = intent.getStringExtra("price").toString()
        token = intent.getStringExtra("token").toString()
        userId = intent.getIntExtra("id", 0)

        Log.d("GetExtra", "$photo, $workshop, $sanggar, $owner, $email, $phone, $address, $description, $price, $token, $userId")
    }

    private fun getPackages(token: String) {
        showLoading(true)
        lifecycleScope.launch {
            val isSuccess = viewModel.getPackages(token)
            if (!isSuccess) {
                Log.d("PacketActivity", "Failed")
            } else {
                setupRecyclerView()
                Log.d("PacketActivity", "Success")
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
                        val intent = Intent(this@PacketActivity, PaymentActivity::class.java)
                        intent.putExtra("photo", uri)
                        intent.putExtra("workshop", workshop)
                        intent.putExtra("sanggar", sanggar)
                        intent.putExtra("owner", owner)
                        intent.putExtra("phone", phone)
                        intent.putExtra("email", email)
                        intent.putExtra("address", address)
                        intent.putExtra("description", description)
                        intent.putExtra("price", price)
                        intent.putExtra("token", token)
                        intent.putExtra("id", userId)
                        intent.putExtra("packageId", data.id)
                        startActivity(intent)
                        Log.d("PacketActivity", "$photo, $workshop, $sanggar, $owner, $email, $phone, $address, $description, $price, $token, $userId")
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