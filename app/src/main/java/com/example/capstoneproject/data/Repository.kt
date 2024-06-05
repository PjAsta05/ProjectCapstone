package com.example.capstoneproject.data

import com.example.capstoneproject.api.ApiService
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.model.PackageResponse
import com.example.capstoneproject.model.UpdateResponse
import com.example.capstoneproject.model.User
import com.example.capstoneproject.model.WorkshopResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun register(email: String, password: String, fullname: String): User {
        return apiService.register(email, password, fullname)
    }

    suspend fun login(email: String, password: String): AuthResponse {
        return apiService.login(email, password)
    }

    suspend fun updateUser(id: Int, email: RequestBody? = null, password: RequestBody? = null, fullName: RequestBody? = null, urlImage: MultipartBody.Part? = null, token: String): UpdateResponse {
        return apiService.updateUserProfile(id, email, password, fullName, urlImage,"Bearer $token")
    }

    suspend fun getListTari(token: String, name: String, origin: String): List<BalineseDance> {
        return apiService.getListTari("Bearer $token", name, origin)
    }

    suspend fun getListPackage(token: String): List<PackageResponse> {
        return apiService.getPackages(token)
    }

    suspend fun getListWorkshop(status: String?, userId: Int?, token: String): List<WorkshopResponse> {
        return apiService.getWorkshop(status, userId, "Bearer $token")
    }
}