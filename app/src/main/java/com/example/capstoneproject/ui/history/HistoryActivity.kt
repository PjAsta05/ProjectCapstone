package com.example.capstoneproject.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.database.History
import com.example.capstoneproject.databinding.ActivityHistoryBinding
import com.example.capstoneproject.ui.detail.workshop.DetailWorkshopActivity
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels()
    private val viewModel1: WorkshopViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getHistory()
        getToken()
    }

    private fun getToken() {
        token = intent.getStringExtra("token").toString()
    }

    private fun getHistory() {
        showLoading(true)
        viewModel.getHistory().observe(this) { list ->
            if (list != null) {
                setupRecyclerView()
                adapter = HistoryAdapter(list)
                adapter.setOnItemClickCallback(object: HistoryAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: History) {
                        getWorkshopById(data.id)
                    }
                })
                binding.recyclerView.adapter = adapter
            } else {
                showToast("Workshop not found")
            }
            showLoading(false)
        }
    }

    private fun getWorkshopById(id: Int) {
        lifecycleScope.launch {
            val isSuccess = viewModel1.getWorkshopById(id, token)
            if (isSuccess) {
                viewModel1.workshop.observe(this@HistoryActivity) { data ->
                    val intent = Intent(this@HistoryActivity, DetailWorkshopActivity::class.java)
                    intent.putExtra(DetailWorkshopActivity.INTENT_PARCELABLE, data)
                    startActivity(intent)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}