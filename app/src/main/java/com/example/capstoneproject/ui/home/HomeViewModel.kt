package com.example.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.model.Tari
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _listTari = MutableLiveData<List<Tari>>()
    val listTari: LiveData<List<Tari>> = _listTari

    fun getTari(token: String, name: String = "", origin: String = "bali") {
        viewModelScope.launch {
            try {
                val response = repository.getListTari(token, name, origin)
                _listTari.postValue(response.listTari)
                Log.d("Get Tari", "$response")
            } catch (e: Exception) {
                Log.e("Get Tari", e.toString())
            }
        }
    }
}