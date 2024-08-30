package com.nocountry.listmate.data

data class Task(
    val id: String,
    val taskName: String,
)

val tasks: List<Task> = listOf(
    Task(id = "1", taskName = "Task #1"),
    Task(id = "2", taskName = "Task #2"),
    Task(id = "3", taskName = "Task #3"),
    Task(id = "4", taskName = "Task #4"),
    Task(id = "5", taskName = "Task #5"),
)
