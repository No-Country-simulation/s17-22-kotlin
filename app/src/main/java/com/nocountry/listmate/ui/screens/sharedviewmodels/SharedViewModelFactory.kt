package com.nocountry.listmate.ui.screens.sharedviewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nocountry.listmate.data.local.SettingsDataStore

class SharedViewModelFactory(
    private val settingsDataStore: SettingsDataStore,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(
                savedStateHandle = savedStateHandle,
                settingsDataStore = settingsDataStore
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}