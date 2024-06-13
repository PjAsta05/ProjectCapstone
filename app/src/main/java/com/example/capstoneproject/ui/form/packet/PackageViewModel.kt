package com.example.capstoneproject.ui.form.packet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.PackageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PackageViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val packages = MutableLiveData<List<PackageResponse>>()

    suspend fun getPackages(token: String): Boolean {
        return try {
            val response = repository.getListPackage(token)
            packages.postValue(response)
            true
        } catch (e: HttpException) {
            Log.d("getPackages", "${e.message}")
            false
        }
    }
}