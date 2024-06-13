package com.example.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.model.ClassificationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    val listDance = MutableLiveData<List<BalineseDance>>()
    val balineseDance = MutableLiveData<BalineseDance>()
    val classification = MutableLiveData<ClassificationResponse>()

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

    suspend fun classifyTari(image: MultipartBody.Part, token: String): Boolean {
        return try {
            val response = repository.imageClassification(image, token)
            classification.postValue(response)
            true
        } catch (e: HttpException) {
            Log.d("classifyTari", "${e.message}")
            false
        }
    }


}