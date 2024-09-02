package com.nocountry.listmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.repository.HomeRepositoryImpl
import com.nocountry.listmate.domain.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getProjects()
    }

    private fun getProjects() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val projects = homeRepository.getProjects().first()
                _uiState.update { it.copy(isLoading = false, projects = projects) }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = "Error getting projects from database ${e.message}"
                    )
                }
            }
        }
    }


    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                return HomeScreenViewModel(
                    HomeRepositoryImpl(FirebaseFirestore.getInstance())
                ) as T
            }
        }
    }

}