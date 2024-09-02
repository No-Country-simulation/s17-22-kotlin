package com.nocountry.listmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.domain.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(val firebase: FirebaseFirestore) : HomeRepository {
    override suspend fun getProjects(): Flow<List<Project>> {
        TODO("Not yet implemented")
    }
}