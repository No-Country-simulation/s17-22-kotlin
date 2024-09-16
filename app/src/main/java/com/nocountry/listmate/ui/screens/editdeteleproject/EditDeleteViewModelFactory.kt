package com.nocountry.listmate.ui.screens.editdeteleproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nocountry.listmate.domain.ProjectRepository


class EditDeleteViewModelFactory(private val projectRepository: ProjectRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditDeleteProjectViewModel::class.java)) {
            return EditDeleteProjectViewModel(projectRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}