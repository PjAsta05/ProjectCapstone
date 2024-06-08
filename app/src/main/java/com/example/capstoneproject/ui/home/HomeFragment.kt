package com.example.capstoneproject.ui.home


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
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.ui.detail.admin.DetailAdminActivity
import com.example.capstoneproject.ui.detail.tari.DetailDanceActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString("token").toString()
        Log.d("HomeFragment", "Token: $token")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTari()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getTari() {
        showLoading(true)
        lifecycleScope.launch {
            val isSuccess = viewModel.getTari(token)
            if (!isSuccess) {
                Log.d("HomeFragment", "Failed to get data")
            } else {
                setupRecyclerView()
            }
            showLoading(false)
        }
    }

    private fun setupRecyclerView() {
        Log.d("HomeFragment", "Setup RecyclerView")
        binding.rvDanceList.layoutManager = LinearLayoutManager(context)
        binding.rvDanceList.setHasFixedSize(true)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listDance.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                adapter = HomeAdapter(list)
                adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: BalineseDance) {
                        val intent = Intent(requireContext(), DetailDanceActivity::class.java)
                        intent.putExtra(DetailDanceActivity.INTENT_PARCELABLE, data)
                        startActivity(intent)
                    }
                })
                binding.rvDanceList.adapter = adapter
                Log.d("HomeFragment", "Data: $list")
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
}
