package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {

    suspend fun registerNewUser(email : String, password : String) : OperationStatus<FirebaseUser>

    suspend fun loginUser(email: String, password: String) : OperationStatus<FirebaseUser>

    suspend fun getUserEmail(): OperationStatus<String>

}