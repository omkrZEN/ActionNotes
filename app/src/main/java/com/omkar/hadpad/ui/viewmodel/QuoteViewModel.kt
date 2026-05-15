package com.omkar.hadpad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkar.hadpad.data.remote.QuoteDto
import com.omkar.hadpad.data.repository.QuoteRepository
import com.omkar.hadpad.ui.state.QuoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val repository: QuoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuoteUiState>(QuoteUiState.Loading) // Mutable state flow main
    val uiState: StateFlow<QuoteUiState> = _uiState.asStateFlow() // Outgoing stateflow - immutable

    init {
        fetchQuote()
    }

    fun fetchQuote() {
        viewModelScope.launch {

            _uiState.value = QuoteUiState.Loading

            try {

                val quote: QuoteDto = repository.getTodayQuote()
                _uiState.value = QuoteUiState.Success(quote)

            } catch (e: Exception) {

                _uiState.value = QuoteUiState.Error(
                    e.message ?: "Something went wrong"
                )

            }
        }
    }
}