package com.omkar.hadpad.ui.state

import com.omkar.hadpad.data.remote.QuoteDto

sealed class QuoteUiState {

    data object Loading : QuoteUiState()

    data class Success(val quote: QuoteDto) : QuoteUiState()

    data class Error(val message: String) : QuoteUiState()
}