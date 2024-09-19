package com.nocountry.listmate.ui.screens.sharedviewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.local.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SharedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _userId = MutableStateFlow(savedStateHandle["userId"] ?: "")
    val userId: StateFlow<String> = _userId.asStateFlow()

    init {
        viewModelScope.launch {
            val storedUserId = settingsDataStore.userFlow.first()
            _userId.value = storedUserId
            savedStateHandle["userId"] = storedUserId
        }
    }

    fun setUserId(userId: String) {
        _userId.value = userId
        savedStateHandle["userId"] = userId

        viewModelScope.launch {
            try {
                settingsDataStore.saveUserId(userId)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error saving userId", e)
            }
        }
    }
}