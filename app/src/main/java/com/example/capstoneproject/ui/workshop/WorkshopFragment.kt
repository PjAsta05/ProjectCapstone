package com.example.capstoneproject.ui.workshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentWorkshopBinding
import com.google.android.material.tabs.TabLayoutMediator

class WorkshopFragment : Fragment() {
    private var _binding: FragmentWorkshopBinding? = null
    private val binding get() = _binding!!
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = arguments?.getString("token").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkshopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTabs()
    }

    private fun changeTabs() {
        val adapter = ViewPagerAdapter(this, token)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Active"
                1 -> "Pending"
                2 -> "Inactive"
                else -> null
            }
        }.attach()
    }
}