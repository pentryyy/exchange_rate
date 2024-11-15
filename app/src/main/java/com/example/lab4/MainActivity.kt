package com.example.lab4

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CurrencyService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    suspend fun fetchData(): Double {
        try {
            val response = api.getCurrencyData().execute()
            if (response.isSuccessful) {
                val currencyData = response.body()
                val usdValue = currencyData?.getAsJsonObject("Valute")?.getAsJsonObject("USD")?.get("Value")?.asDouble
                return usdValue ?: 0.0
            }
            else {
                Log.e("CurrencyService", "Error fetching data: ${response.code()}")
            }
        } catch (error: Throwable) {
            Log.e("CurrencyService", "Error fetching data: $error")
        }
        return 0.0
    }

    private interface Api {
        @GET("/daily_json.js")
        fun getCurrencyData(): Call<JsonObject>
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView5 = findViewById<TextView>(R.id.textView5)
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("d MMMM yyyy года", Locale("ru"))
        val formattedDate = dateFormat.format(currentDate)
        textView5.text = formattedDate

        val textView4 = findViewById<TextView>(R.id.textView4)
        val currencyService = CurrencyService()

        runBlocking {
            GlobalScope.launch(Dispatchers.IO) {
                val usdValue = currencyService.fetchData()
                textView4.text = String.format("%.2f", usdValue)+" RUR"
            }.join()
        }
    }
}