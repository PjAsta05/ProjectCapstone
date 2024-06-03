package com.example.capstoneproject.ui.admin.workshop

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.capstoneproject.ui.admin.workshop.active.ActiveFragment
import com.example.capstoneproject.ui.admin.workshop.inactive.InactiveFragment
import com.example.capstoneproject.ui.admin.workshop.pending.PendingFragment

class ViewPagerAdapter(fm : FragmentManager, behavior : Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ActiveFragment()
            1 -> PendingFragment()
            2 -> InactiveFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Active"
            1 -> "Pending"
            2 -> "Inactive"
            else -> null
        }
    }
}