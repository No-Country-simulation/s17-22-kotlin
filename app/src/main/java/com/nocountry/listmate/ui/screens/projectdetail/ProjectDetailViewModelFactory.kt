package com.nocountry.listmate.ui.screens.projectdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nocountry.listmate.domain.ProjectDetailRepository

class ProjectDetailViewModelFactory(
    private val projectDetailRepository: ProjectDetailRepository,
    projectId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    private val savedStateHandle = SavedStateHandle(mapOf("projectId" to projectId))
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailViewModel::class.java)) {
            return ProjectDetailViewModel(projectDetailRepository, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}