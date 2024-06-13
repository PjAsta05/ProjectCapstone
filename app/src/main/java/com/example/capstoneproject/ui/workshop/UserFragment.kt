package com.example.capstoneproject.ui.workshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentUserBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.detail.workshop.DetailWorkshopActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkshopViewModel by viewModels()
    private lateinit var adapter: WorkshopAdapter
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString(ARG_TOKEN).toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWorkshop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getWorkshop()
    }

    private fun getWorkshop() {
        showLoading(true)
        lifecycleScope.launch {
            val isSuccess = viewModel.getWorkshops("success", null, token)
            showLoading(false)
            if (!isSuccess) {
                Log.d("PendingFragment", "Failed to get workshops")
            } else {
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        observeWorkshop()
    }

    private fun observeWorkshop() {
        viewModel.workshops.observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty()) {
                adapter = WorkshopAdapter(list)
                adapter.setOnItemClickCallback(object : WorkshopAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: WorkshopResponse) {
                        val intent = Intent(requireContext(), DetailWorkshopActivity::class.java)
                        intent.putExtra(DetailWorkshopActivity.INTENT_PARCELABLE, data)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    }
                })
                binding.recyclerView.adapter = adapter
            } else {
                binding.emptyTextView.visibility = View.VISIBLE
            }
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
        private const val ARG_TOKEN = "token"
    }
}