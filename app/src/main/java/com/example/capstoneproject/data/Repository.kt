package com.example.capstoneproject.data

import androidx.lifecycle.LiveData
import com.example.capstoneproject.api.ApiService
import com.example.capstoneproject.data.pref.UserModel
import com.example.capstoneproject.data.pref.UserPreference
import com.example.capstoneproject.database.History
import com.example.capstoneproject.database.HistoryDao
import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.model.ClassificationResponse
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
    private val apiService: ApiService,
    private val historyDao: HistoryDao
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

    suspend fun register(
        email: String,
        password: String,
        fullname: String
    ): User {
        return apiService.register(
            email,
            password,
            fullname)
    }

    suspend fun login(
        email: String,
        password: String
    ): AuthResponse {
        return apiService.login(email, password)
    }

    suspend fun updateUser(
        id: Int,
        email: RequestBody? = null,
        password: RequestBody? = null,
        fullName: RequestBody? = null,
        urlImage: MultipartBody.Part? = null,
        token: String): UpdateResponse {
        return apiService.updateUserProfile(id, email, password, fullName, urlImage,"Bearer $token")
    }

    suspend fun getListTari(
        token: String,
        name: String,
        origin: String
    ): List<BalineseDance> {
        return apiService.getListTari("Bearer $token", name, origin)
    }

    suspend fun findTari(dance: String, token: String): BalineseDance {
        return apiService.findTari(dance, "Bearer $token")
    }

    suspend fun getListPackage(token: String): List<PackageResponse> {
        return apiService.getPackages("Bearer $token")
    }

    suspend fun getListWorkshop(
        status: String?,
        userId: Int?,
        token: String
    ): List<WorkshopResponse> {
        return apiService.getWorkshop(status, userId, "Bearer $token")
    }

    suspend fun addWorkshop(
        packageId: RequestBody,
        userId: RequestBody,
        workshopName: RequestBody,
        sanggarName: RequestBody,
        address: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        owner: RequestBody,
        description: RequestBody,
        price: RequestBody,
        photo: MultipartBody.Part ?= null,
        proof: MultipartBody.Part ?= null,
        token: String
    ): UpdateResponse {
        return apiService.addWorkshop(packageId, userId, workshopName, sanggarName, address, email, phone, owner, description, price, photo, proof, "Bearer $token")
    }

    suspend fun updateWorkshop(
        id: Int,
        packageId: RequestBody? = null,
        userId: RequestBody? = null,
        workshopName: RequestBody? = null,
        sanggarName: RequestBody? = null,
        address: RequestBody? = null,
        email: RequestBody? = null,
        phone: RequestBody? = null,
        owner: RequestBody? = null,
        description: RequestBody? = null,
        price: RequestBody? = null,
        status: RequestBody? = null,
        photo: MultipartBody.Part? = null,
        proof: MultipartBody.Part? = null,
        token: String
    ): UpdateResponse {
        return apiService.updateWorkshop(id, packageId, userId, workshopName, sanggarName, address, email, phone, owner, description, price, status, photo, proof, "Bearer $token")
    }

    suspend fun deleteWorkshop(id: Int, token: String): UpdateResponse {
        return apiService.deleteWorkshop(id, "Bearer $token")
    }

    suspend fun extendWorkshop(
        id: Int,
        packageId: RequestBody,
        proof: MultipartBody.Part? = null,
        token: String
    ): UpdateResponse {
        return apiService.extendWorkshop(id, packageId, proof, "Bearer $token")
    }

    suspend fun getWorkshopById(id: Int, token: String): WorkshopResponse {
        return apiService.getWorkshopById(id, "Bearer $token")
    }

    suspend fun workshopRegistration(
        workshopId: Int,
        name: String,
        email: String,
        phone: String,
        age: Int,
        gender: String,
        token: String
    ): UpdateResponse {
        return apiService.workshopRegistration(workshopId, name, email, phone, age, gender, "Bearer $token")
    }

    suspend fun addHistory(history: History) {
        historyDao.addHistory(history)
    }

    fun getHistory(): LiveData<List<History>> {
        return historyDao.getHistory()
    }

    suspend fun imageClassification(
        image: MultipartBody.Part
    ): ClassificationResponse {
        return apiService.imageClassification(image)
    }
}