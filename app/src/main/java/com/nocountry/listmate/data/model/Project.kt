package com.nocountry.listmate.data.model

data class Project(
    val id: String = "",
    val title: String = "",
    val tasks: List<Task> = emptyList(),
    val users: List<User> = emptyList()
)
