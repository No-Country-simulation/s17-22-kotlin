package com.nocountry.listmate.data.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val projectId: String = "",
    val assignedUsers: List<String> = emptyList()
)
