package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.domain.HomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HomeRepositoryImpl(private val firebase: FirebaseFirestore) : HomeRepository {
    override suspend fun getProjectsById(userId: String): Flow<List<Project>> = callbackFlow {
        val projects = mutableListOf<Project>()

        firebase.collection("projects")
            .whereArrayContains("users", userId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result.documents) {
                    val project = document.toObject(Project::class.java)
                    if (project != null) {
                        projects.add(project)
                    }
                }
                trySend(projects)
            }
            .addOnFailureListener { exception ->
                Log.d("HomeRepositoryImpl", "Error getting projects: ", exception)
                trySend(emptyList())
            }

        awaitClose()
    }
}