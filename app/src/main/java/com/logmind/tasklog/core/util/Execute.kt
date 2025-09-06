package com.logmind.tasklog.core.util

import android.util.Log
import retrofit2.Response

inline fun <T> execute(operation: String, block: () -> Response<T>): Result<T> {
    return runCatching {
        val response = block()
        when {
            response.isSuccessful -> response.body() ?: throw Exception("Empty response")
            else -> throw Exception("Failed to $operation")
        }
    }.onFailure {
        Log.e("TaskRepositoryImpl", "$operation failed", it)
    }
}