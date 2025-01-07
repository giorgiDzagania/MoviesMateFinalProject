package com.example.testproject.data.repository

import android.util.Log
import com.example.testproject.core.OperationStatus
import com.example.testproject.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl : FirebaseAuthRepository {
    private val auth = FirebaseAuth.getInstance()

    // -------------------------- Register -------------------------
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

    // ------------------------------ Log In --------------------------
    override suspend fun loginUser(email: String, password: String): OperationStatus<FirebaseUser> {
        return try {
            // Attempt to sign in with the email and password
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                Log.d("FirebaseAuthRepositoryImpl", "User logged in: ${user.uid}")
                OperationStatus.Success(user) // Success, return the user
            } else {
                // In case there's an issue, though this should not happen
                OperationStatus.Failure(Exception("Login failed, but no exception was thrown."))
            }
        } catch (e: Exception) {
            // Handle any exceptions like invalid credentials
            Log.d("FirebaseAuthRepositoryImpl", "Error during login: ${e.message}")
            OperationStatus.Failure(e)
        }
    }




    /* override suspend fun logInUser(email: String, password: String): OperationStatus<AuthResult> {

         return OperationStatus<AuthResult> {
              try {
                 val result = auth.signInWithEmailAndPassword(email,password)
                 val user = result.result
                 if (user != null){
                     Log.d("FirebaseAuthRepositoryImpl", "User loggedIn: ${user.user}")
                     OperationStatus.Success(user)
                 }
                 else {
                     OperationStatus.Failure(Exception("User login failed but no exception was thrown."))
                 }

             } catch (e: Exception) {
                 Log.d("FirebaseAuthRepositoryImpl", "Error during registration: ${e.message}")
                 OperationStatus.Failure(e)
             }
         }

     }
 */
}