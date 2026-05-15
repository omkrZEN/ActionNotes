package com.omkar.hadpad.data.repository

import com.omkar.hadpad.data.remote.QuoteApiService
import com.omkar.hadpad.data.remote.QuoteDto

class QuoteRepository(
    private val apiService: QuoteApiService
) {
    suspend fun getTodayQuote(): QuoteDto {
        return apiService.getTodayQuote().first()
    }
}