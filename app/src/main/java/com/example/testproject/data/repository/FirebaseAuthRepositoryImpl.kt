package com.example.testproject.data.repository

import android.util.Log
import com.example.testproject.core.OperationStatus
import com.example.testproject.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl : FirebaseAuthRepository {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun registerNewUser(email: String, password: String):
            OperationStatus<FirebaseUser> {
        return try {
            // Register the user with email and password
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d("FirebaseAuthRepositoryImpl", "User created: ${user.uid}")
                OperationStatus.Success(user)
            } else {
                OperationStatus.Failure(Exception("User creation failed but no exception was thrown."))
            }
        } catch (e: Exception) {
            Log.d("FirebaseAuthRepositoryImpl", "Error during registration: ${e.message}")
            OperationStatus.Failure(e)
        }
    }

}