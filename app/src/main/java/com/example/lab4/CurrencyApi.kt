package com.example.lab4

import com.example.lab4.data.CurrencyResponse
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/daily_json.js")
    suspend fun getCurrencyData(): CurrencyResponse
}