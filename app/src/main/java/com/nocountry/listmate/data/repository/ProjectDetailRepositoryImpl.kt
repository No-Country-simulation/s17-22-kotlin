package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.domain.ProjectDetailRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProjectDetailRepositoryImpl(private val firebase: FirebaseFirestore) :
    ProjectDetailRepository {

    override suspend fun getProjectTasks(projectId: String): Flow<List<Task>> = callbackFlow {
        firebase.collection("tasks")
            .whereEqualTo("projectId", projectId)
            .get()
            .addOnSuccessListener { result ->
                val tasks = result.documents.mapNotNull { document ->
                    val task = document.toObject(Task::class.java)
                    task?.let { it.id = document.id }
                    task
                }.toMutableList()
                trySend(tasks)
            }
            .addOnFailureListener { exception ->
                Log.d("ProjectDetailRepositoryImpl", "Error getting tasks: ", exception)
                trySend(emptyList())
            }
        awaitClose()
    }
}