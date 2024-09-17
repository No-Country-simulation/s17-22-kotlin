package com.nocountry.listmate.data.model

data class User(
    val uid: String = "",
    var name: String = "",
    var lastName: String = "",
    val email: String = "",
    val projects: List<Project> = emptyList()
)
