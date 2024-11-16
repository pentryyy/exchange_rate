package com.example.exchange_rate

import android.util.Log
import com.example.exchange_rate.data.Currency
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyService() {

    // Инициализация Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Создание API интерфейса
    private val api = retrofit.create(CurrencyApi::class.java)

    // Метод для получения данных о валютах
    suspend fun fetchCurrencies(): Map<String, Currency> {
        try {
            val response = api.getCurrencyData()
            return response.valute // Возвращаем коллекцию валют
        } catch (e: Exception) {
            Log.e("CurrencyService", "Error: ${e.message}")
            return emptyMap() // В случае ошибки возвращаем пустую коллекцию
        }
    }

    // Метод для списка имен валют
    suspend fun fetchValuteNames(): List<String> {
        return try {
            val response = api.getCurrencyData()
            return response.valute?.keys?.toList() ?: emptyList() // Возвращаем список валют
        } catch (e: Exception) {
            Log.e("fetchValuteNames", "Error: ${e.message}")
            return emptyList() // В случае ошибки возвращаем пустой список
        }
    }
}