package com.logmind.tasklog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logmind.tasklog.data.local.dao.TaskDao
import com.logmind.tasklog.data.local.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskLogDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}