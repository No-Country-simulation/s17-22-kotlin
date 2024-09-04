package com.nocountry.listmate.data

import com.nocountry.listmate.model.Usuario

//referencia https://firebase.google.com/docs/firestore/quickstart?hl=es-419#android_1
class UsuarioManager {
    fun guardarUsuario(usuario: Usuario) {
        FirestoreConnection.usuarioDB.add(usuario)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }
}