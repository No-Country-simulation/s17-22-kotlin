package com.nocountry.listmate.ui.screens.sharedviewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProjectTaskSharedViewModel(private val projectRepository: ProjectRepository) :
    ViewModel() {

    private val _users = MutableStateFlow(listOf<User>())

    private val _projectParticipants = MutableLiveData<MutableList<User>>(mutableListOf())
    val projectParticipants: LiveData<MutableList<User>> get() = _projectParticipants

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    private val _projectTitle = MutableLiveData<String>()
    val projectTitle: LiveData<String> get() = _projectTitle

    private val _projectDescription = MutableLiveData<String>()
    val projectDescription: LiveData<String> get() = _projectDescription

    private val _project = MutableLiveData<Project>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun setProjectParticipants(users: List<User>) {
        _users.value = users
    }

    fun setTasks(tasks: MutableList<Task>) {
        _tasks.value = tasks
    }

    fun setProjectTitle(projectTitle: String) {
        _projectTitle.value = projectTitle
    }

    fun setProjectDescription(projectDescription: String) {
        _projectDescription.value = projectDescription
    }

    fun createProjectAndTasks(
        ownerId: String,
        projectDescription: String,
        onProjectCreated: () -> Unit
    ) {
        val title = _projectTitle.value

        val participants = _projectParticipants.value
        val participantsId = participants?.map { it.uid }
        Log.d("ParticipantsId", "Participants IDs: $participantsId")

        val tasks = _tasks.value
        val tasksId = tasks?.map { it.id }
        Log.d("TasksId", "Tasks IDs: $tasksId")

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                if (title != null && participants?.isNotEmpty() == true) {
                    projectRepository.createProject(
                        title,
                        ownerId,
                        participantsId,
                        tasksId,
                        projectDescription
                    )
                        .collect { createdProject ->
                            _project.postValue(createdProject)
                            projectRepository.addParticipantsIds(createdProject.id, participants)
                                .collect { participantIds ->
                                    Log.d("CreateProject", "Added participants: $participantIds")
                                    if (tasks != null) {
                                        projectRepository.createTasks(createdProject.id, tasks)
                                            .collect { createdTasks ->
                                                _tasks.postValue(createdTasks.toMutableList())
                                                withContext(Dispatchers.Main) {
                                                    resetVariables()
                                                    onProjectCreated()
                                                    _loading.postValue(false)
                                                }
                                            }
                                    } else {
                                        withContext(Dispatchers.Main) {
                                            resetVariables()
                                            onProjectCreated()
                                            _loading.postValue(false)
                                            Log.d("CreateProject", "Navigating to Home screen")
                                        }
                                    }
                                }
                        }
                }
            } catch (e: Exception) {
                _loading.postValue(false)
                Log.e(
                    "CreateProjectTask",
                    "Error creating project and tasks: ${e.message ?: "Unknown error"}"
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    val users = searchText
        .debounce(1000L)
        .onEach {
            fetchUsers()
            _isSearching.update { true }
        }
        .combine(_users) { text, participants ->
            if (text.isBlank()) {
                participants
            } else {
                participants.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _users.value)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }


    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.fetchUsers().collect { participantsList ->
                _users.value = participantsList
            }
        }
    }

    fun onAddParticipantToProject(user: User) {
        _projectParticipants.value?.let { currentParticipants ->

            val updatedParticipants = currentParticipants.toMutableList().apply {
                add(user)
            }

            _projectParticipants.value = updatedParticipants
        }
    }

    fun fetchProjectParticipantsFromDb(projectParticipants: List<String>) {
        viewModelScope.launch {
            try {
                projectRepository.fetchProjectParticipantsFromDb(projectParticipants)
                    .flowOn(Dispatchers.IO)
                    .collect { participants ->
                        withContext(Dispatchers.Main) {
                            _projectParticipants.value = participants.toMutableList()
                        }
                    }
            } catch (e: Exception) {
                Log.e("ProjectViewModel", "Error collecting participants: ${e.message}", e)
            }
        }
    }

    fun fetchProjectTasksFromDb(projectTasks: List<String>) {
        viewModelScope.launch {
            try {
                projectRepository.fetchProjectTasksFromDb(projectTasks)
                    .flowOn(Dispatchers.IO)
                    .collect { tasks ->
                        withContext(Dispatchers.Main) {
                            _tasks.value = tasks.toMutableList()
                        }
                    }
            } catch (e: Exception) {
                Log.e("ProjectViewModel", "Error collecting tasks: ${e.message}", e)
            }
        }
    }

    fun updateProjectAndTasks(
        projectId: String,
        projectName: String,
        projectDescription: String,
        participants: List<String>,
        onProjectUpdated: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.postValue(true)
                projectRepository.updateProjectAndTasks(
                    projectId,
                    projectName,
                    projectDescription,
                    participants
                ).collect {
                    withContext(Dispatchers.Main) {
                        onProjectUpdated()
                        resetVariables()
                    }
                }
            } catch (e: Exception) {
                Log.e("ProjectViewModel", "Project could not be updated: ${e.message}", e)
            } finally {
                _loading.postValue(false)
            }
        }
    }



    private fun resetVariables() {
        _projectTitle.value = ""
        _projectDescription.value = ""
        _tasks.value?.clear()
        _tasks.value = _tasks.value
        _projectParticipants.value?.clear()
        _projectParticipants.value = _projectParticipants.value
        _searchText.value = ""
    }
}