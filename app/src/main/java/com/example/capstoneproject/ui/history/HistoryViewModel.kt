package com.example.capstoneproject.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.Repository
import com.example.capstoneproject.database.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    fun addHistory(id: Int, workshopName: String) {
        viewModelScope.launch {
            val date = getCurrentDateTime()
            val history = History(id, workshopName, date)
            repository.addHistory(history)
        }
    }

    fun getHistory() = repository.getHistory()

    private fun getCurrentDateTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
    }
}