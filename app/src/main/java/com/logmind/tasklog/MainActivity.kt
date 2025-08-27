package com.logmind.tasklog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.logmind.tasklog.navigation.TaskLogNavGraph
import com.logmind.tasklog.presentation.theme.TaskLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskLogTheme {
                TaskLogNavGraph()
            }
        }
    }
}
