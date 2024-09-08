package com.nocountry.listmate.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val projects: List<String> = emptyList()
){
    fun doesMatchSearchQuery(query: String): Boolean{
        val matchingCombinations = listOf(
            "$name$lastName",
            "$name $lastName",
            "$lastName$name",
            "$lastName $name"
        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}
