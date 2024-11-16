package com.example.exchange_rate

import com.example.exchange_rate.data.CurrencyResponse
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/daily_json.js")
    suspend fun getCurrencyData(): CurrencyResponse
}