package com.nocountry.listmate.ui.screens.sharedviewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nocountry.listmate.data.Task

class CreateProjectTaskSharedViewModel: ViewModel() {
    private val _projectParticipants = MutableLiveData<List<String>>()
    val projectParticipants: LiveData<List<String>> = _projectParticipants

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    private val _projectTitle = MutableLiveData<String>()
    val projectTitle: LiveData<String> = _projectTitle

    fun setProjectParticipants(projectParticipants: List<String>){
        _projectParticipants.value = projectParticipants
    }

    fun setTasks(tasks: MutableList<Task>){
        _tasks.value = tasks
    }

    fun setProjectTitle(projectTitle: String){
        _projectTitle.value = projectTitle
    }
}