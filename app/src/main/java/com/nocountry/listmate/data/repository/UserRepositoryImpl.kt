package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Usuario
import com.nocountry.listmate.domain.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryImpl(private val firebase: FirebaseFirestore) : UserRepository {
    override fun getUserById(userId: String): Flow<Usuario?> = callbackFlow {
        val userDocRef = firebase.collection("users").document(userId)

        userDocRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d("UserRepositoryImpl", "Error getting user: ", error)
                trySend(null)
            } else {
                val user = snapshot?.toObject(Usuario::class.java)
                trySend(user)
            }
        }
        awaitClose()
    }
}