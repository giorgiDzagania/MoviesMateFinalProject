package com.example.testproject.presentation.screens.screens.registerPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val registerRepo = FirebaseAuthRepositoryImpl()

    private val _registerEvent = MutableSharedFlow<Unit>()
    val registerEvent: SharedFlow<Unit> = _registerEvent

    private val _showError = MutableSharedFlow<Exception>()
    val showError: SharedFlow<Exception> = _showError

    fun registerNewUser(email: String, password: String) = viewModelScope.launch {
        when (val status = registerRepo.registerNewUser(email, password)) {

            is OperationStatus.Success -> {
                _registerEvent.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _showError.emit(status.exception)
            }
        }
        registerRepo.registerNewUser(email, password)
    }
}