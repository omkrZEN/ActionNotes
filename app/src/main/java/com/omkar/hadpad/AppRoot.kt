package com.omkar.hadpad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.omkar.hadpad.auth.AuthViewModel
import com.omkar.hadpad.ui.ActionNotesHome
import com.omkar.hadpad.ui.viewmodel.TaskViewModel

enum class AppScreen {
    Home,
    Profile
}

@Composable
fun AppRoot(
    taskViewModel: TaskViewModel,
    authViewModel: AuthViewModel
) {
    var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Home) }

    when (currentScreen) {
        AppScreen.Home -> {
            ActionNotesHome(
                taskViewModel = taskViewModel,
                onProfileClick = { currentScreen = AppScreen.Profile }
            )
        }

        AppScreen.Profile -> {
            ProfileScreen(
                authViewModel = authViewModel,
                onBackClick = { currentScreen = AppScreen.Home }
            )
        }
    }
}

