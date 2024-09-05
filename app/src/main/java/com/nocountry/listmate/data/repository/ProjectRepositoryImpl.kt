package com.nocountry.listmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl(private val firebase: FirebaseFirestore) : ProjectRepository {
    override suspend fun createProject(
        projectName: String,
        ownerId: String,
        participants: List<String>?,
        tasks: List<String>?,
    ): Flow<Project> = callbackFlow {
        val project = hashMapOf(
            "name" to projectName,
            "ownerId" to ownerId,
            "participants" to participants,
            "tasks" to tasks
        )

        firebase.collection("projects")
            .add(project)
            .addOnSuccessListener { docRef ->
                val projectId = docRef.id
                val createProject =
                    participants?.let { Project(projectId, projectName, "", it, tasks, ownerId ) }
                if (createProject != null) {
                    trySend(createProject).isSuccess
                }
            }
            .addOnFailureListener { close(it) }
        awaitClose()
    }

    override suspend fun createTasks(projectId: String, tasks: List<Task>): Flow<List<Task>> = callbackFlow {
        val updatedTasks = tasks.map { task ->
            val taskData = hashMapOf(
                "projectId" to projectId,
                "taskName" to task.taskName,
                "assignedTo" to task.assignedTo,
                "description" to task.description,
                "status" to task.status
            )
            val docRef = firebase.collection("tasks").document()
            docRef.set(taskData).await()
            task.copy(id = docRef.id)
        }

        val taskIds = updatedTasks.map { it.id }
        firebase.collection("projects")
            .document(projectId)
            .update("tasks", taskIds)
            .await()

        trySend(updatedTasks).isSuccess
        awaitClose()
    }

}