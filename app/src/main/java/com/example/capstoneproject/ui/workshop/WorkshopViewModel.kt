package com.example.capstoneproject.ui.workshop

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkshopViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {


}