package com.logmind.tasklog.data.remote

import com.logmind.tasklog.data.models.requests.AddTaskRequest
import com.logmind.tasklog.data.models.requests.UpdateTaskRequest
import com.logmind.tasklog.data.models.responses.NetworkTask
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {
    @GET("api/tasks")
    suspend fun getTasks(): Response<List<NetworkTask>>

    @GET("api/tasks/{id}")
    suspend fun getTaskById(@Path("id") id: Long): Response<NetworkTask>

    @POST("api/tasks")
    suspend fun addTask(@Body task: AddTaskRequest): Response<NetworkTask>

    @PATCH("api/tasks/{id}")
    suspend fun updateTask(
        @Path("id") id: Long,
        @Body task: UpdateTaskRequest
    ): Response<NetworkTask>

    @POST("api/tasks/{id}")
    suspend fun updateTaskStatus(
        @Path("id") id: Long,
        @Body isCompleted: Boolean
    ): Response<Unit>
}