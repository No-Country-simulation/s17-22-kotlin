package com.nocountry.listmate.ui.screens.sharedviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.data.repository.ProjectRepositoryImpl
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProjectTaskSharedViewModel(private val projectRepository: ProjectRepository) :
    ViewModel() {
    private val _projectParticipants = MutableLiveData<List<User>>()
    val projectParticipants: LiveData<List<User>> get() = _projectParticipants

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    private val _projectTitle = MutableLiveData<String>()
    val projectTitle: LiveData<String> get() = _projectTitle

    private val _project = MutableLiveData<Project>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun setProjectParticipants(projectParticipants: List<User>) {
        _projectParticipants.value = projectParticipants
    }

    fun setTasks(tasks: MutableList<Task>) {
        _tasks.value = tasks
    }

    fun setProjectTitle(projectTitle: String) {
        _projectTitle.value = projectTitle
    }

    fun createProjectAndTasks(ownerId: String, onProjectCreated: () -> Unit) {
        val title = _projectTitle.value
        val participants = _projectParticipants.value
        val participantsId = participants?.map { it.uid }

        val tasks = _tasks.value
        val tasksId = tasks?.map { it.id }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                if (title != null && participants?.isNotEmpty() == true) {
                    projectRepository.createProject(title, ownerId, participantsId, tasksId)
                        .collect { createdProject ->
                            _project.postValue(createdProject)
                            if (tasks != null) {
                                projectRepository.createTasks(createdProject.id, tasks).collect { createdTasks ->
                                    _tasks.postValue(createdTasks.toMutableList())
                                    withContext(Dispatchers.Main) {
                                        resetVariables()
                                        onProjectCreated()
                                        _loading.postValue(false)
                                    }
                                }
                            }

                        }

                }
            } catch (e: Exception) {
                _loading.postValue(false)
                Log.e("CreateProjectTask", "Error creating project and tasks: ${e.message ?: "Unknown error"}")
            }
        }
    }

    private fun resetVariables() {
        _projectTitle.value = ""
        _tasks.value?.clear()
        _tasks.value = _tasks.value
        _projectParticipants.value = emptyList()
    }
}