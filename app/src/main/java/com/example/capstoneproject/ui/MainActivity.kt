package com.example.capstoneproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.ui.akun.ProfileActivity
import com.example.capstoneproject.ui.home.HomeFragment
import com.example.capstoneproject.ui.workshop.WorkshopFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.background = null
        loadFragment(HomeFragment())
        changeFragment()
        changeActivity()
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

    private fun changeActivity() {
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
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