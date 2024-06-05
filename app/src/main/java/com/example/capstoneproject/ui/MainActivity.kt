package com.example.capstoneproject.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.ui.account.ProfileActivity
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.history.HistoryActivity
import com.example.capstoneproject.ui.home.HomeFragment
import com.example.capstoneproject.ui.workshop.WorkshopFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.background = null
        binding.toolbarLayout.background = null
        observeSession()
        changeFragment()
        changeActivity()
    }

    private fun observeSession(){
        lifecycleScope.launch {
            viewModel.getSession().observe(this@MainActivity) { user ->
                if (!user.isLogin) {
                    navigateToWelcomeActivity()
                } else {
                    token = user.token
                    binding.toolbar.title = "Home"
                    loadFragment(HomeFragment())
                }
            }
        }
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
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.history -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        val bundle = Bundle()
        bundle.putString("token", token)
        Log.d("Load Fragment", token)
        fragment.arguments = bundle
        fragmentTransaction.commit()
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}