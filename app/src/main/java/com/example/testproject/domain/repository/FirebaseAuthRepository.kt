package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus

interface FirebaseAuthRepository {

    suspend fun registerNewUser(email : String, password : String) : OperationStatus<Unit>

}