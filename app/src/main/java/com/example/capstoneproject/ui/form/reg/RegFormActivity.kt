package com.example.capstoneproject.ui.form.reg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityRegFormBinding
import com.example.capstoneproject.ui.history.HistoryViewModel
import com.example.capstoneproject.ui.process.ProcessRegActivity
import com.example.capstoneproject.ui.workshop.WorkshopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RegFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegFormBinding
    private val viewModel : WorkshopViewModel by viewModels()
    private val viewModel2 : HistoryViewModel by viewModels()
    private var token: String = ""
    private var workshopId: Int = 0
    private var workshopName: String = ""
    private var gender: String = ""

    private var nameValid = false
    private var genderValid = false
    private var phoneValid = false
    private var emailValid = false
    private var ageValid = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        getTokenAndId()
        input()
        btnEnabled()
        reg()
    }

    private fun input() {
        val name = binding.etName
        val email = binding.etEmail
        val phone = binding.etPhone
        val age = binding.etAge

        name.addTextChangedListener {
            nameValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        email.addTextChangedListener {
            emailValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        phone.addTextChangedListener {
            phoneValid = it.toString().isNotEmpty()
            btnEnabled()
        }
        age.addTextChangedListener {
            ageValid = it.toString().isNotEmpty()
            btnEnabled()
        }

        binding.rbGender.setOnCheckedChangeListener (object : RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                genderValid = checkedId == R.id.gender_pria || checkedId == R.id.gender_wanita
                when (checkedId) {
                    R.id.gender_pria -> gender = "pria"
                    R.id.gender_wanita -> gender = "wanita"
                }
                btnEnabled()
            }
        })
    }


    private fun btnEnabled() {
        binding.btnReg.isEnabled = nameValid && emailValid && phoneValid && ageValid && genderValid
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    private fun reg() {
        binding.btnReg.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val age = binding.etAge.text.toString().toInt()
            showLoading(true)
            Log.d("RegFormActivity", "Sending request with token: $token, name: $name, email: $email, phone: $phone, gender: $gender, age: $age")
            lifecycleScope.launch {
                val isSuccess = viewModel.workshopRegistration(workshopId, name, email, phone, age, gender, token)
                if (isSuccess) {
                    viewModel2.addHistory(workshopId, workshopName)
                    val intent = Intent(this@RegFormActivity, ProcessRegActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d("RegFormActivity", "Registration successful")
                    showToast("Registration successful")
                } else {
                    Toast.makeText(this@RegFormActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                    Log.d("RegFormActivity", "Registration failed")
                    showToast("Registration failed")
                }
                showLoading(false)
            }
        }
    }


    private fun getTokenAndId() {
        token = intent.getStringExtra("token").toString()
        workshopId = intent.getIntExtra("workshopId", 0)
        workshopName = intent.getStringExtra("workshopName").toString()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}