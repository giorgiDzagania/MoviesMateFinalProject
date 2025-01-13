package com.example.testproject.data.repository

import com.example.testproject.core.CallHelper
import com.example.testproject.core.OperationStatus
import com.example.testproject.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl : FirebaseAuthRepository {
    private val auth = FirebaseAuth.getInstance()

    // -------------------------- Register -------------------------
    override suspend fun registerNewUser(email: String, password: String):
            OperationStatus<FirebaseUser> {
        return CallHelper.safeFirebaseCall {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("User creation failed.")
        }
    }


    // ------------------------------ Log In --------------------------
    override suspend fun loginUser(email: String, password: String): OperationStatus<FirebaseUser> {
        return CallHelper.safeFirebaseCall {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user ?: throw Exception("Login failed, but no exception was thrown.")
        }
    }

    override suspend fun getUserEmail(): OperationStatus<String> {
        return CallHelper.safeFirebaseCall {
            val currentUser = auth.currentUser
            currentUser?.email ?: ""
        }
    }

}