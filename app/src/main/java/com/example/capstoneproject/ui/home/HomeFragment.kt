package com.example.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.ui.auth.LoginActivity
import com.example.capstoneproject.ui.auth.SigninActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        signin()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signin() {
        binding.btnSignin.setOnClickListener {
            val intent = Intent(activity, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}