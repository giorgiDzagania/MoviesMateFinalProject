package com.example.testproject.presentation.screens.screens.registerPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val registerRepo = FirebaseAuthRepositoryImpl()

    private val _registerEvent = MutableSharedFlow<FirebaseUser>()
    var registerEvent: SharedFlow<FirebaseUser> = _registerEvent

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private val _isLoadingState = MutableSharedFlow<Boolean>()
    val isLoadingState: SharedFlow<Boolean> = _isLoadingState

    fun registerNewUser(email: String, password: String) = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = registerRepo.registerNewUser(email, password)) {
            is OperationStatus.Success -> {
                _registerEvent.emit(status.value)
            }

            is OperationStatus.Failure -> {
                val errorMessage = when (status.exception) {
                    is FirebaseAuthUserCollisionException -> "This email is already registered. Please log in."
                    else -> status.exception.message ?: "An unknown error occurred."
                }
                _showError.emit(errorMessage)
            }
        }
        _isLoadingState.emit(false)
    }

}