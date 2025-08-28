package com.logmind.tasklog.data.service.remote

import com.logmind.tasklog.data.models.requests.JoinRequest
import com.logmind.tasklog.data.models.requests.LoginRequest
import com.logmind.tasklog.domain.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {

    @POST("api/auth/join")
    suspend fun join(@Body request: JoinRequest): Response<User>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<User>

    @POST("api/auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("api/auth/{id}")
    suspend fun getUserInfo(@Path("id") id: Long): Response<User>


}