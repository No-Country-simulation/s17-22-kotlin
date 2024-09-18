package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl(private val firebase: FirebaseFirestore) : ProjectRepository {
    override suspend fun createProject(
        projectName: String,
        ownerId: String,
        participants: List<String>?,
        tasks: List<String>?,
        projectDescription: String
    ): Flow<Project> = callbackFlow {
        val project = hashMapOf(
            "name" to projectName,
            "ownerId" to ownerId,
            "participants" to participants,
            "tasks" to tasks,
            "description" to projectDescription
        )

        firebase.collection("projects")
            .add(project)
            .addOnSuccessListener { docRef ->
                val projectId = docRef.id
                firebase.collection("projects").document(projectId)
                    .update("id", projectId)
                    .addOnSuccessListener {
                        val createProject = Project(
                            projectId,
                            projectName,
                            projectDescription,
                            participants ?: emptyList(),
                            tasks,
                            ownerId
                        )
                        trySend(createProject).isSuccess
                    }
                    .addOnFailureListener { close(it) }
            }
            .addOnFailureListener { close(it) }

        awaitClose()
    }


    override suspend fun createTasks(projectId: String, tasks: List<Task>): Flow<List<Task>> =
        callbackFlow {
            val updatedTasks = tasks.map { task ->
                val taskData = hashMapOf(
                    "projectId" to projectId,
                    "taskName" to task.taskName,
                    "assignedTo" to task.assignedTo,
                    "assignedToId" to task.assignedToId,
                    "description" to task.description,
                    "status" to task.status,
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

    override suspend fun addParticipantsIds(
        projectId: String,
        participants: List<User>
    ): Flow<List<String>> =
        callbackFlow {
            val participantsIds = mutableListOf<String>()

            participants.forEach { participant ->
                val querySnapshot = firebase.collection("users")
                    .whereEqualTo("email", participant.email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val userDoc = querySnapshot.documents.first()
                    val userId = userDoc.getString("uid")
                    if (userId != null) {
                        participantsIds.add(userId)
                    }
                }
            }

            firebase.collection("projects")
                .document(projectId)
                .update("participants", participantsIds)
                .await()

            trySend(participantsIds)
            awaitClose()
        }

    override suspend fun fetchUsers(): Flow<List<User>> = callbackFlow {
        val usersCollection = firebase.collection("users")
        val snapshotListener = usersCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val usersList = snapshot.toObjects(User::class.java)
                trySend(usersList).isSuccess
            }
        }
        awaitClose { snapshotListener.remove() }
    }

    override suspend fun deleteProject(projectId: String) {
        try {
            val tasksSnapshot = firebase.collection("tasks")
                .whereEqualTo("projectId", projectId)
                .get()
                .await()

            for (task in tasksSnapshot.documents) {
                firebase.collection("tasks").document(task.id).delete().await()
            }

            firebase.collection("projects")
                .document(projectId)
                .delete()
                .await()
        } catch (e: Exception) {
            Log.e("ProjectRepositoryImpl", "Error deleting project: ${e.message}", e)
            throw e
        }
    }

    override suspend fun fetchProjectParticipantsFromDb(projectParticipants: List<String>): Flow<List<User>> = flow {
        try {
            val users = mutableListOf<User>()
            val usersCollection = firebase.collection("users")

            val query = usersCollection.whereIn("uid", projectParticipants).get().await()

            query.documents.forEach { document ->
                document.toObject(User::class.java)?.let { user ->
                    users.add(user)
                }
            }

            emit(users)
        } catch (e: Exception) {
            Log.e("ProjectRepository", "Error fetching participants: ${e.message}")
            emit(emptyList())
        }
    }

    override suspend fun fetchProjectTasksFromDb(projectTasks: List<String>): Flow<List<Task>> = flow {
        try {
            val tasks = mutableListOf<Task>()
            val usersCollection = firebase.collection("tasks")

            projectTasks.forEach { uid ->
                val userDoc = usersCollection.document(uid).get().await()
                userDoc.toObject(Task::class.java)?.let { task ->
                    tasks.add(task)
                }
            }

            emit(tasks)
        } catch (e: Exception) {
            Log.e("ProjectRepository", "Error fetching participants: ${e.message}")
            emit(emptyList())
        }
    }

}