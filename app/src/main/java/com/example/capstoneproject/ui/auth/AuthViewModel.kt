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
import com.example.capstoneproject.model.User
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val successResponse = MutableLiveData<AuthResponse>()
    val updateResponse = MutableLiveData<User>()
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

    suspend fun updateUser(
        id: Int,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        fullName: RequestBody ?= null,
        urlImage: MultipartBody.Part ?= null,
        token: String
    ): Boolean {
        return try {
            val response = repository.updateUser(id, email, password, fullName, urlImage, token)
            updateResponse.postValue(response.data)
            Log.d("Update response", response.toString())
            true
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(error, ErrorResponse::class.java)
            errorMessage.postValue(errorResponse.message)
            false
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    suspend fun signIn(
        email: String,
        password: String,
        fullname: String
    ): Boolean {
        return try {
            repository.register(email, password, fullname)
            Log.d("Register Success", "Success")
            true
        } catch (e: HttpException) {
            Log.d("Register Failed", "Failed")
            false
        }
    }

    suspend fun logIn(
        email: String,
        password: String
    ): Boolean {
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