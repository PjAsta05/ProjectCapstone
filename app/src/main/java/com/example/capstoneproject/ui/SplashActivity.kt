package com.example.capstoneproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreen.setKeepOnScreenCondition { true }
        observeSession()
    }

    private fun observeSession(){
        lifecycleScope.launch {
            viewModel.getSession().observe(this@SplashActivity) { user ->
                if (!user.isLogin) {
                    navigateToWelcomeActivity()
                } else {
                    navigateToMainActivity()
                }
            }
        }
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}