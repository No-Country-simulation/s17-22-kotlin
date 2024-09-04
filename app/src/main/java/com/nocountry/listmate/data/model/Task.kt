package com.nocountry.listmate.data.model

data class Task(
    val id: String,
    val projectId: String,
    val taskName: String,
    val assignedTo: String,
    val description: String,
    val status: String,
)


