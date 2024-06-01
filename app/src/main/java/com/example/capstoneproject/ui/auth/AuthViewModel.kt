package com.example.capstoneproject.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val successResponse = MutableLiveData<AuthResponse>()
    val errorMessage = MutableLiveData<String>()

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun updateSession(user: UserModel) {
        viewModelScope.launch {
            repository.updateSession(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    suspend fun signIn(email: String, password: String, fullname: String): Boolean {
        return try {
            repository.register(email, password, fullname)
            true
        } catch (e: HttpException) {
            false
        }
    }

    suspend fun logIn(email: String, password: String): Boolean {
        return try {
            val response = repository.login(email, password)
            successResponse.postValue(response)
            true
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
            errorMessage.postValue(errorResponse.message)
            false
        }
    }
}