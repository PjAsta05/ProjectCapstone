package com.example.capstoneproject.ui.admin.workshop.active

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
import com.example.capstoneproject.databinding.FragmentActiveBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.detail.admin.DetailAdminActivity
import com.example.capstoneproject.ui.workshop.WorkshopAdapter
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActiveFragment : Fragment() {
    private var _binding: FragmentActiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkshopViewModel by viewModels()
    private lateinit var adapter: WorkshopAdapter
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString(ARG_TOKEN).toString()
        Log.d("ActiveFragment", "Token: $token")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWorkshop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveBinding.inflate(inflater, container, false)
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
            if (!isSuccess) {
                Log.d("PendingFragment", "Failed to get workshops")
            } else {
                setupRecyclerView()
            }
            showLoading(false)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        observeWorkshop()
    }

    private fun observeWorkshop() {
        viewModel.workshops.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                binding.emptyTextView.visibility = View.GONE
                adapter = WorkshopAdapter(list)
                adapter.setOnItemClickCallback(object : WorkshopAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: WorkshopResponse) {
                        val intent = Intent(requireContext(), DetailAdminActivity::class.java)
                        intent.putExtra(DetailAdminActivity.INTENT_PARCELABLE, data)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    }
                })
                binding.recyclerView.adapter = adapter
            } else {
                binding.emptyTextView.visibility = View.VISIBLE
                adapter = WorkshopAdapter(emptyList())
                binding.recyclerView.adapter = adapter
            }
        }
    }

    fun newInstance(token: String): ActiveFragment {
        val fragment = ActiveFragment()
        val args = Bundle()
        args.putString(ARG_TOKEN, token)
        fragment.arguments = args
        return fragment
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