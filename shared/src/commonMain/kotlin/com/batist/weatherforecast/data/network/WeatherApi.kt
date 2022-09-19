package com.batist.weatherforecast.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.logging.*


class WeatherApi(private val httpClient: HttpClient) {

    companion object {
        private const val API_KEY = "APPID=f19a99039f51dfbb7eb919baf424c0c9"
        private const val FORECAST_ENDPOINT =
            "https://api.openweathermap.org/data/2.5/weather?lat=%.d&lon=%.d&APPID=%s"
        private const val FORECAST_ENDPOINT_CITY =
            "https://api.openweathermap.org/data/2.5/weather?q="
        private const val UNITS = "&units=metric"
    }

    suspend fun getForecast(city: String): WeatherForecastEntity? {
        return try {
            val response = httpClient.get(
                FORECAST_ENDPOINT_CITY.plus(city)
                    .plus(UNITS)
                    .plus("&${API_KEY}")
            )
            if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}