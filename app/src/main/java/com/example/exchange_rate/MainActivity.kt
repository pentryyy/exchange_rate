package com.example.exchange_rate

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    private val currencyService = CurrencyService() // Создаем экземпляр CurrencyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.СonstraintLayout1)
        constraintLayout.setBackgroundColor(Color.WHITE)

        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val textView5 = findViewById<TextView>(R.id.textView5)

        lifecycleScope.launch {
            try {
                // Получение курса валют и их названий
                val currencies = currencyService.fetchCurrencies()
                val currencyNames = currencyService.fetchValuteNames()

                // Настройка адаптера для Spinner
                val adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    currencyNames
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter

                // Устанавливаем валюту по умолчанию
                val defaultCurrency = "USD"
                val defaultCurrencyPosition = currencyNames.indexOf(defaultCurrency)
                spinner2.setSelection(defaultCurrencyPosition)

                // Отображение обменного курса валюты по умолчанию
                textView4.text = "1 $defaultCurrency = ${currencies[defaultCurrency]?.value} RUB"

                // Задаем текущую дату
                val currentDate = Date()
                val dateFormat = SimpleDateFormat("d MMMM yyyy года", Locale("ru"))
                val formattedDate = dateFormat.format(currentDate)
                textView5.text = "Курс валют на $formattedDate"

                // Обработчик изменений в Spinner
                spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedCurrency = currencyNames[position]
                        textView4.text =
                            "1 $selectedCurrency = ${currencies[selectedCurrency]?.value} RUB"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Не делаем ничего, если ничего не выбрано
                    }
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error: ${e.message}")
            }
        }
    }
}