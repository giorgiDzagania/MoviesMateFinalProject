package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {

    suspend fun registerNewUser(email : String, password : String) : OperationStatus<FirebaseUser>

}