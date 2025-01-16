package com.example.testproject.core

sealed interface OperationStatus<T> {
    data class Success<T>(val value: T) : OperationStatus<T>
    data class Failure<T>(val exception: Exception) : OperationStatus<T>
}

// ---------------------- Mapper ---------------------
fun <FromData, ToData> OperationStatus<FromData>.map(mapper: (FromData) -> ToData, ): OperationStatus<ToData> {
    return when (this) {
        is OperationStatus.Success -> OperationStatus.Success(mapper(value))
        is OperationStatus.Failure -> OperationStatus.Failure(exception)
    }
}
