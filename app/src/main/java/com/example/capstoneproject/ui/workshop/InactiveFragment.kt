package com.example.capstoneproject.ui.workshop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentInactiveBinding
import com.example.capstoneproject.model.WorkshopResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InactiveFragment : Fragment() {
    private var _binding: FragmentInactiveBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkshopViewModel by viewModels()
    private lateinit var adapter: WorkshopAdapter
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString(ARG_TOKEN).toString()
        Log.d("InactiveFragment", "Token: $token")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWorkshop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInactiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getWorkshop() {
        lifecycleScope.launch {
            val isSuccess = viewModel.getWorkshops("done", null, token)
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
            if (list != null) {
                adapter = WorkshopAdapter(list)
                adapter.setOnItemClickCallback(object : WorkshopAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: WorkshopResponse) {
                        TODO("Not yet implemented")
                    }
                })
                binding.recyclerView.adapter = adapter
            }
        }
    }

    fun newInstance(token: String): InactiveFragment {
        val fragment = InactiveFragment()
        val args = Bundle()
        args.putString(ARG_TOKEN, token)
        fragment.arguments = args
        return fragment
    }

    companion object {
        private const val ARG_TOKEN = "token"
    }
}