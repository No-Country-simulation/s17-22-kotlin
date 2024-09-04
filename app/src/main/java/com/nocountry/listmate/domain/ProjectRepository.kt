package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun createProject(
        projectName: String,
        ownerId: String,
        participants: List<User>,
        tasks: List<Task>?
    ): Flow<Project>

    suspend fun createTasks(projectId: String, tasks: List<Task>): Flow<List<Task>>
}