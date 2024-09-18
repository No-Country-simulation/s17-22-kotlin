package com.nocountry.listmate.ui.screens.editdeteleproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDeleteProjectViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _projectTasks = MutableLiveData<List<Task>>(emptyList())
    val projectsTasks: LiveData<List<Task>> get() = _projectTasks


    fun deleteProject(projectId: String, onDeleteCompleted: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                projectRepository.deleteProject(projectId)
                kotlinx.coroutines.delay(1000)
                _loading.postValue(false)
                withContext(Dispatchers.Main) {
                    onDeleteCompleted()
                }
            } catch (e: Exception){
                Log.d("EditDeleteViewModel", "Error deleting the project: ${e.message}")
            }
        }
    }
}