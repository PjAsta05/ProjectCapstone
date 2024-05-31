package com.example.capstoneproject.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.data.pref.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
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

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}