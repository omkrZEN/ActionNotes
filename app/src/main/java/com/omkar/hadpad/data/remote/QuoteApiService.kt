package com.omkar.hadpad.data.remote

import com.omkar.hadpad.data.remote.QuoteDto
import retrofit2.http.GET

interface QuoteApiService {

    @GET("api/today")
    suspend fun getTodayQuote(): List<QuoteDto>
}