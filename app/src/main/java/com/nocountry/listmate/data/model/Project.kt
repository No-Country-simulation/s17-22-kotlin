package com.nocountry.listmate.data.model

data class Project(
    val id: String = "",
    var name: String = "",
    var description: String = "",
    val participants: List<String> = emptyList(),
    val tasks: List<String>? = emptyList(),
    val ownerId: String = ""

)
