package com.example.capstoneproject.ui.workshop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.WorkshopResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WorkshopViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val workshops = MutableLiveData<List<WorkshopResponse>>()

    suspend fun getWorkshops(status: String?, userId: Int?, token: String): Boolean {
        return try {
            val response = repository.getListWorkshop(status, userId, token)
            workshops.postValue(response)
            Log.d("getWorkshop", "Success")
            true
        } catch (e: HttpException) {
            Log.d("getWorkshop", "${e.message}")
            false
        }
    }

    suspend fun addWorkshop(
        packageId: Int,
        userId: Int,
        workshopName: String,
        sanggarName: String,
        address: String,
        email: String,
        phone: String,
        owner: String,
        description: String,
        price: Int,
        photo: MultipartBody.Part,
        proof: MultipartBody.Part,
        token: String
    ): Boolean {
        return try {
            val response = repository.addWorkshop(packageId, userId, workshopName, sanggarName, address, email, phone, owner, description, price, photo, proof, token)
            Log.d("addWorkshop", "Success")
            true
        } catch (e: HttpException) {
            Log.d("addWorkshop", "${e.message}")
            false
        }
    }

    suspend fun updateWorkshop(
        id: Int,
        packageId: Int? = null,
        userId: Int? = null,
        workshopName: String? = null,
        sanggarName: String? = null,
        address: String? = null,
        email: String? = null,
        phone: String? = null,
        owner: String? = null,
        description: String? = null,
        price: Int? = null,
        photo: MultipartBody.Part? = null,
        proof: MultipartBody.Part? = null,
        token: String
    ): Boolean {
        return try {
            val response = repository.updateWorkshop(id, packageId, userId, workshopName, sanggarName, address, email, phone, owner, description, price, photo, proof, token)
            true
        } catch (e: HttpException) {
            Log.d("updateWorkshop", "${e.message}")
            false
        }
    }

    suspend fun workshopRegistration(
        workshopId: Int,
        name: String,
        email: String,
        phone: String,
        age: Int,
        gender: String,
        token: String
    ): Boolean {
        return try {
            val response = repository.workshopRegistration(workshopId, name, email, phone, age, gender, token)
            true
        } catch (e: HttpException) {
            Log.d("workshopRegistration", "${e.message}")
            false
        }
    }
}