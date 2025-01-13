package com.example.testproject.core

import com.example.testproject.data.remote.dto.ApiResponse
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

    suspend fun <T> safeApiResponseCall(
        apiCall: suspend () -> Response<ApiResponse<T>>
    ): OperationStatus<List<T>> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // You can return the list of movies (or generic items)
                    OperationStatus.Success(body.results)
                } else {
                    OperationStatus.Failure(Exception("API response body is null"))
                }
            } else {
                OperationStatus.Failure(Exception("HTTP error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            OperationStatus.Failure(e)
        }
    }
}