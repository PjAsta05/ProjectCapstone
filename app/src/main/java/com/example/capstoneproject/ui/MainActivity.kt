package com.example.capstoneproject.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.ui.account.ProfileActivity
import com.example.capstoneproject.ui.auth.AuthViewModel
import com.example.capstoneproject.ui.history.HistoryActivity
import com.example.capstoneproject.ui.home.HomeFragment
import com.example.capstoneproject.ui.admin.workshop.WorkshopFragment
import com.example.capstoneproject.ui.camera.CameraActivity
import com.example.capstoneproject.ui.workshop.UserFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()
    private var token: String = ""
    private var role: String = ""
    private var isHome: Boolean = true

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.background = null
        binding.toolbarLayout.background = null

        observeSession()
        changeFragment()
        changeActivity()
        navigateToCameraActivity()
    }

    private fun observeSession(){
        lifecycleScope.launch {
            viewModel.getSession().observe(this@MainActivity) { user ->
                if (!user.isLogin) {
                    navigateToWelcomeActivity()
                } else {
                    token = user.token
                    role = user.role
                    if (isHome) {
                        binding.toolbar.title = "Home"
                        loadFragmentHome()
                    } else {
                        binding.toolbar.title = "Workshop"
                        loadFragmentWorkshop()
                    }
                }
            }
        }
    }

    private fun changeFragment() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragmentHome()
                    true
                }
                R.id.workshop -> {
                    loadFragmentWorkshop()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragmentHome() {
        binding.toolbar.title = "Home"
        isHome = true
        loadFragment(HomeFragment())
    }

    private fun loadFragmentWorkshop() {
        binding.toolbar.title = "Workshop"
        isHome = false
        if (role == "admin") {
            loadFragment(WorkshopFragment())
        }else {
            loadFragment(UserFragment())
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
                    intent.putExtra("token", token)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToCameraActivity() {
        binding.scanButton.setOnClickListener {
            if (!allPermissionsGranted()) {
                requestPermissionLauncher.launch(REQUIRED_PERMISSION)
            } else {
                val intent = Intent(this@MainActivity, CameraActivity::class.java)
                intent.putExtra("token", token)
                startActivity(intent)
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}