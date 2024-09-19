package com.nocountry.listmate.ui.screens.sharedviewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.local.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SharedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _userId = MutableStateFlow(savedStateHandle["userId"] ?: "")
    val userId: StateFlow<String> = _userId.asStateFlow()

    private val _name = MutableStateFlow(savedStateHandle["name"] ?: "")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _lastName = MutableStateFlow(savedStateHandle["lastName"] ?: "")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    init {
        viewModelScope.launch {
            val storedUserId = settingsDataStore.userFlow.first()
            _userId.value = storedUserId
            savedStateHandle["userId"] = storedUserId

            val storedName = settingsDataStore.getName.first()
            _name.value = storedName
            savedStateHandle["name"] = storedName

            val storedLastName = settingsDataStore.getLastName.first()
            _lastName.value = storedLastName
            savedStateHandle["lastName"] = storedLastName
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

    fun setName(name: String) {
        _name.value = name
        savedStateHandle["name"] = name

        viewModelScope.launch {
            try {
                settingsDataStore.saveName(name)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error saving name", e)
            }
        }
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
        savedStateHandle["lastName"] = lastName

        viewModelScope.launch {
            try {
                settingsDataStore.saveLastName(lastName)
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Error saving lastName", e)
            }
        }
    }
}