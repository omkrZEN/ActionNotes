package com.omkar.hadpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth
import com.omkar.hadpad.data.local.DatabaseProvider
import com.omkar.hadpad.data.repository.TaskRepository
import com.omkar.hadpad.auth.AuthRoot
import com.omkar.hadpad.auth.AuthViewModel
import com.omkar.hadpad.auth.GoogleAuthRepository
import com.omkar.hadpad.ui.ActionNotesHome
import com.omkar.hadpad.ui.theme.ActionNotesTheme
import com.omkar.hadpad.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appContext = applicationContext

            val db = remember { DatabaseProvider.getDatabase(appContext) }
            val dao = remember { db.taskDao() }
            val taskRepository = remember { TaskRepository(dao) }
            val taskViewModel = remember { TaskViewModel(taskRepository) }


            ActionNotesTheme {

                ActionNotesHome(viewModel = taskViewModel)

            }
        }
    }
}

