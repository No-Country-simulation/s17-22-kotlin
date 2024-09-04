package com.nocountry.listmate.data.model

data class Project(
    val id: String,
    val name: String,
    val ownerId: String,
    val participants: List<User>,
    val tasks: List<Task>?
)

val dummyProjects = Project(
    id = "project1",
    name = "Project Alpha",
    ownerId = "user1",
    participants = listOf(
        User(id = "user1", name = "Alice", email = "alice@example.com"),
        User(id = "user2", name = "Bob", email = "bob@example.com")
    ),
    tasks = listOf(
        Task(
            id = "task1",
            projectId = "project1",
            taskName = "Design UI",
            assignedTo = "user1",
            description = "Create the user interface for the application.",
            status = "In Progress"
        ),
        Task(
            id = "task2",
            projectId = "project1",
            taskName = "Implement Backend",
            assignedTo = "user2",
            description = "Develop the backend services and APIs.",
            status = "Not Started"
        )
    )
)


