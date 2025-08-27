package com.logmind.tasklog.data.service.remote

import com.logmind.tasklog.domain.entities.User
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {
    @GET("api/auth/login")
    suspend fun getUserInfo(): Response<User>


}