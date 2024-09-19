package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun createProject(
        projectName: String,
        ownerId: String,
        participants: List<String>?,
        tasks: List<String>?,
        projectDescription: String
    ): Flow<Project>

    suspend fun createTasks(projectId: String, tasks: List<Task>): Flow<List<Task>>

    suspend fun addParticipantsIds(projectId: String, participants: List<User>): Flow<List<String>>

    suspend fun fetchUsers(): Flow<List<User>>

    suspend fun deleteProject(projectId: String)

    suspend fun fetchProjectParticipantsFromDb(projectParticipants: List<String>): Flow<List<User>>

    suspend fun fetchProjectTasksFromDb(projectTasks: List<String>): Flow<List<Task>>

    suspend fun updateProjectAndTasks(
        projectId: String,
        projectName: String,
        projectDescription: String,
        participants: List<String>
    ): Flow<Project>
}