package com.example.capstoneproject.ui.admin.workshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentWorkshopAdminBinding
import com.example.capstoneproject.databinding.FragmentWorkshopBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout


class WorkshopAdminFragment : Fragment() {

    private var _binding: FragmentWorkshopAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkshopAdminBinding.inflate(inflater, container, false)

        val tabLayout: TabLayout = binding.tlAdmin
        val viewPager: ViewPager = binding.viewPager

        val adapter = ViewPagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
