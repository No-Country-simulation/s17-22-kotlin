package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.Project
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getProjects(): Flow<List<Project>>
}