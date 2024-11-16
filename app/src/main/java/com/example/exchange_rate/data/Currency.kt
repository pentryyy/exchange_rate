package com.example.exchange_rate.data

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("ID") val id: String,
    @SerializedName("NumCode") val numCode: String,
    @SerializedName("CharCode") val charCode: String,
    @SerializedName("Nominal") val nominal: Int,
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: Double,
    @SerializedName("Previous") val previous: Double
)
