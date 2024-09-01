package com.nocountry.listmate.data

data class Task(
    val id: String,
    val taskName: String,
    val assignedTo: String
)

val tasks: MutableList<Task> = mutableListOf()
