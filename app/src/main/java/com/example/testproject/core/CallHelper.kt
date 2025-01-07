package com.example.testproject.core

import retrofit2.Response

object CallHelper {

    suspend fun <T> safeFirebaseCall(
        firebaseCall: suspend () -> T
    ): OperationStatus<T> {
        return try {
            val result = firebaseCall.invoke()
            OperationStatus.Success(result)
        } catch (e: Exception) {
            OperationStatus.Failure(e)
        }
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): OperationStatus<T>{
        return try {
            val result = apiCall.invoke()
            OperationStatus.Success(result.body()!!)
        }catch (e: Exception){
            OperationStatus.Failure(e)
        }
    }

}