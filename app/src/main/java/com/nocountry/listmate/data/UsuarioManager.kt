package com.nocountry.listmate.data

import com.nocountry.listmate.data.model.User

//referencia https://firebase.google.com/docs/firestore/quickstart?hl=es-419#android_1
class UsuarioManager {
    fun guardarUsuario(usuario: User) {
        FirestoreConnection.usuarioDB.add(usuario)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }
}