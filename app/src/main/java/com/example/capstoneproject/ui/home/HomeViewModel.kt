package com.example.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.BalineseDance
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val listDance = MutableLiveData<List<BalineseDance>>()
    val balineseDance = MutableLiveData<BalineseDance>()

    suspend fun getTari(token: String, name: String = "", origin: String = "bali"): Boolean {
        return try {
            val response = repository.getListTari(token, name, origin)
            Log.d("getTari", "$response")
            listDance.postValue(response)
            true
        } catch (e: HttpException) {
            Log.d("getTari", "${e.message}")
            false
        }
    }

    suspend fun findTari(dance: String, token: String): Boolean{
        return try {
            val response = repository.findTari(dance, token)
            balineseDance.postValue(response)
            true
        } catch (e: HttpException) {
            Log.d("findTari", "${e.message}")
            false
        }
    }
}