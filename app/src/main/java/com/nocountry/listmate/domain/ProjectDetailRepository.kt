package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.Task
import kotlinx.coroutines.flow.Flow

interface ProjectDetailRepository {

    suspend fun getProjectTasks(projectId: String): Flow<List<Task>>

}