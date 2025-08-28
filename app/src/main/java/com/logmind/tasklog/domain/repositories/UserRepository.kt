package com.logmind.tasklog.domain.repositories

import com.logmind.tasklog.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>
}