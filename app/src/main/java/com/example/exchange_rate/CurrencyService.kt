package com.example.exchange_rate

import android.util.Log
import com.example.exchange_rate.data.Currency
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.exchange_rate.data.CurrencyResponse

class CurrencyService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(CurrencyApi::class.java)

    suspend fun fetchCurrencies(): Map<String, Currency> {
        return try {
            val response = api.getCurrencyData()
            response.valute ?: emptyMap()  // Если valute == null, возвращаем пустую коллекцию
        } catch (e: Exception) {
            Log.e("CurrencyService", "Error: ${e.message}")
            emptyMap()
        }
    }
}