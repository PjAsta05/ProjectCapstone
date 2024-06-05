package com.example.capstoneproject.ui.workshop

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, token: String): FragmentStateAdapter(fragment) {
    private val fragmentList = listOf(
        ActiveFragment().newInstance(token),
        PendingFragment().newInstance(token),
        InactiveFragment().newInstance(token)
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}