package com.batist.weatherforecast.di

import com.batist.weatherforecast.data.network.WeatherApi
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json


object SharedDependenciesProvider {
    private fun provideHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
            install(Logging)
        }
    }

    fun provideWeatherApi() = WeatherApi(provideHttpClient())

    fun provideDefaultDispatcher() = Dispatchers.Default
}