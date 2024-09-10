package com.nocountry.listmate.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val projects: List<Project> = emptyList()
)
