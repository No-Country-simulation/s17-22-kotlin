package com.nocountry.listmate.data

data class Project(
    val id: Int,
    val projectName: String,
    val tasksCount: String,
    val participantsCount: String
)

val dummyProjects: List<Project> = listOf(
    Project(
        id = 1,
        projectName = "Housework",
        tasksCount = "10 tasks",
        participantsCount = "2 participants"
    ),
    Project(
        id = 2,
        projectName = "Journey friends",
        tasksCount = "20 tasks",
        participantsCount = "5 participants"
    ),
)
