package com.example.capstoneproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.home.HomeFragment
import com.example.capstoneproject.workshop.WorkshopFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.background = null
        loadFragment(HomeFragment())
        changeFragment()
    }

    private fun changeFragment() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    binding.toolbar.title = "Home"
                    loadFragment(HomeFragment())
                    true
                }
                R.id.workshop -> {
                    binding.toolbar.title = "Workshop"
                    loadFragment(WorkshopFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}