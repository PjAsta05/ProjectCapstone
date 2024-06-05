package com.example.capstoneproject.ui.workshop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.WorkshopResponse
import dagger.hilt.android.lifecycle.HiltViewModel
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
}