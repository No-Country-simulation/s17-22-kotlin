package com.nocountry.listmate.data.model

data class Project(
    val id: String = "",
    val title: String = "",
    val tasks: List<String> = emptyList(),
    val users: List<String> = emptyList()
)
