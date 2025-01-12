package com.example.testproject.presentation.screens.screens.registerPage

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val registerRepo = FirebaseAuthRepositoryImpl()

    // ----------------------------- Flows ------------------------------

    private val _registerEvent = MutableSharedFlow<FirebaseUser>(replay = 0) // No old values replayed
    val registerEvent: SharedFlow<FirebaseUser> = _registerEvent

    private val _showError = MutableStateFlow<String?>(null) // Use StateFlow for latest state
    val showError: StateFlow<String?> = _showError

    // -------------------------------------- Register  ----------------------------------------------
    fun registerNewUser(email: String, password: String) = viewModelScope.launch {
        when (val status = registerRepo.registerNewUser(email, password)) {
            is OperationStatus.Success -> {
                d("MyLog", "${status.value}")
                _registerEvent.emit(status.value)

            }

            is OperationStatus.Failure -> {
                d("MyLog", "${status.exception}")
                val errorMessage = when (status.exception) {
                    is FirebaseAuthUserCollisionException -> "This email is already registered. Please log in."
                    else -> status.exception.message ?: "An unknown error occurred."
                }
                _showError.emit(errorMessage)

            }
        }
    }
}