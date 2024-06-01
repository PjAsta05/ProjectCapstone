package com.example.capstoneproject.data

import com.example.capstoneproject.api.ApiService
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.model.AuthResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun updateSession(user: UserModel) {
        userPreference.updateSession(user)
    }

    suspend fun logout() {
        userPreference.deleteSession()
    }

    suspend fun register(email: String, password: String, fullname: String): Boolean {
        return apiService.register(email, password, fullname)
    }

    suspend fun login(email: String, password: String): AuthResponse {
        return apiService.login(email, password)
    }
}