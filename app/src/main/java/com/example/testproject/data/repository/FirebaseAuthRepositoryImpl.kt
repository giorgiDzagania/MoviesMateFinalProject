package com.example.testproject.data.repository

import com.example.testproject.core.OperationStatus
import com.example.testproject.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl : FirebaseAuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun registerNewUser(email: String, password: String): OperationStatus<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            OperationStatus.Success(Unit)

        } catch (e: Exception) {
            OperationStatus.Failure(e)
        }
    }
}