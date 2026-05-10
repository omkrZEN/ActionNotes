package com.omkar.hadpad.auth

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignedIn: Boolean = false,
    val refreshProfile: Boolean = false
)

class AuthViewModel(
    private val repo: GoogleAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onGoogleSignInClick(activity: Activity) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            runCatching {
                repo.signIn(activity)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSignedIn = true,
                    refreshProfile = !_uiState.value.refreshProfile // flip the boolean to ! for any state it currently has
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Google sign-in failed"
                )
            }
        }
    }

}