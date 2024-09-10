package com.nocountry.listmate.singleton

class GlobalUser {
    companion object{
        var uid: String = ""
        var name: String = ""
        var lastName: String = ""
        var email: String = ""
        fun initialize(user: MutableMap<String, Any>){
            uid = user["uid"].toString()
            name = user["name"].toString()
            lastName = user["lastName"].toString()
            email = user["email"].toString()
        }

    }
}