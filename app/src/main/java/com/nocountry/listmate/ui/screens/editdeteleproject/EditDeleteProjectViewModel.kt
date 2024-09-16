package com.nocountry.listmate.ui.screens.editdeteleproject

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDeleteProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    fun deleteProject(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectRepository.deleteProject(projectId)
            } catch (e: Exception){
                Log.d("EditDeleteViewModel", "Error deleting the project: ${e.message}")
            }
        }
    }
}