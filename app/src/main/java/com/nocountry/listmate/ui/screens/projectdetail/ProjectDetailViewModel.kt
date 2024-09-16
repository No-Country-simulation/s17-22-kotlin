package com.nocountry.listmate.ui.screens.projectdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.domain.ProjectDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectDetailViewModel(
    private val projectDetailRepository: ProjectDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _projectTasks = MutableLiveData<List<Task>>(emptyList())
    val projectsTasks: LiveData<List<Task>> get() = _projectTasks

    val projectId = savedStateHandle.get<String>("projectId")

    init {
        if (projectId != null) {
            getProjectTask(projectId = projectId)
        }
    }

    fun getProjectTask(projectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                projectDetailRepository.getProjectTasks(projectId = projectId).collect { taskList ->
                    _projectTasks.postValue(taskList)
                }
            } catch (e: Exception) {
                Log.d("ProjectDetailViewModel", "Error getting project tasks: ${e.message}")
            }
        }
    }
}