package com.example.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.model.Tari
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.auth.login.LoginActivity
import com.example.capstoneproject.ui.auth.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var token : String =""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.getTari(token)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(emptyList())
        binding.rvDanceList.layoutManager = LinearLayoutManager(context)
        binding.rvDanceList.setHasFixedSize(true)
        binding.rvDanceList.adapter = adapter

        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Tari) {
                // Handle item click
            }
        })
    }

    private fun observeViewModel() {
        viewModel.listTari.observe(viewLifecycleOwner, Observer { tariList ->
            adapter = HomeAdapter(tariList)
            adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Tari) {
                    // Handle item click
                }
            })
            binding.rvDanceList.adapter = adapter
        })
    }




}
