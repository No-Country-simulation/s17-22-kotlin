package com.nocountry.listmate.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.HomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HomeRepositoryImpl(private val firebase: FirebaseFirestore) : HomeRepository {
    override suspend fun getProjects(): Flow<List<Project>> = callbackFlow {

        val tasksMap = mutableMapOf<String, Task>()
        val usersMap = mutableMapOf<String, User>()
        val tasksToFetch = mutableSetOf<String>()
        val usersToFetch = mutableSetOf<String>()


        // Getting projects
        firebase.collection("projects")
            .get()
            .addOnSuccessListener { result ->
                val projects = mutableListOf<Project>()
                for (projectDocument in result.documents) {
                    val project = projectDocument.toObject(Project::class.java)
                    if (project != null) {

                        project.tasks?.let { tasksToFetch.addAll(it) }
                        usersToFetch.addAll(project.participants)
                        projects.add(project)
                    }
                }

                // Getting tasks
                tasksToFetch.forEach { taskId ->
                    firebase.collection("tasks").document(taskId)
                        .get()
                        .addOnSuccessListener { taskDocument ->
                            taskDocument.toObject(Task::class.java)?.let { task ->
                                tasksMap[task.id] = task
                            }
                        }
                }

                // Getting users
                usersToFetch.forEach { userId ->
                    firebase.collection("users").document(userId)
                        .get()
                        .addOnSuccessListener { userDocument ->
                            userDocument.toObject(User::class.java)?.let { user ->
                                usersMap[user.id] = user
                            }
                        }
                }

                // Update projects with tasks and users
                val updatedProjects = projects.map { project ->
                    val projectTasks = project.tasks?.mapNotNull { tasksMap[it] }
                    val projectUsers = project.participants.mapNotNull { usersMap[it] }

                    val updatedProject = project.copy(
                        tasks = projectTasks?.map { it.id },
                        participants = projectUsers.map { it.id }
                    )
                    updatedProject
                }

                trySend(projects)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting projects: ", exception)
            }

        awaitClose()
    }
}