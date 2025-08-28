package com.logmind.tasklog.data.interceptor

import com.logmind.tasklog.domain.repositories.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (shouldSkipAuth(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        val accessToken = runBlocking {
            tokenRepository.getAccessToken()
        }

        val authenticatedRequest = if (!accessToken.isNullOrBlank()) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(authenticatedRequest)
    }

    private fun shouldSkipAuth(request: Request): Boolean {
        val path = request.url.encodedPath
        return path.contains("api/auth/login") ||
                path.contains("api/auth/join") ||
                path.contains("api/auth/refresh")


    }
}