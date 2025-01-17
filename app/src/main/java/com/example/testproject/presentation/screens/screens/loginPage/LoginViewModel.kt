package com.example.testproject.presentation.screens.screens.loginPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginRepository = FirebaseAuthRepositoryImpl()

    private val _loginEvent = MutableSharedFlow<FirebaseUser>()
    val loginEvent: SharedFlow<FirebaseUser> = _loginEvent

    private val _showError = MutableSharedFlow<String?>()
    val showError: SharedFlow<String?> = _showError

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _isLoadingState.emit(true)
        when (val status = loginRepository.loginUser(email, password)) {
            is OperationStatus.Success -> {
                _loginEvent.emit(status.value)
            }

            is OperationStatus.Failure -> {
                val errorMessage = when (status.exception) {
                    is FirebaseAuthInvalidUserException -> "User with this email does not exist"
                    is FirebaseAuthInvalidCredentialsException -> "Invalid email or Incorrect password"
                    else -> status.exception.message ?: "An unknown error occurred."
                }
                _showError.emit(errorMessage)
            }
        }
        _isLoadingState.emit(false)
    }
}