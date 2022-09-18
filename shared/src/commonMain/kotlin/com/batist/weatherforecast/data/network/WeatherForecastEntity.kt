package com.batist.weatherforecast.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastEntity(
    @SerialName("weather")
    val weather: List<Weather>?=null,
    @SerialName("main")
    val weatherDescription: WeatherDescription,
    @SerialName("wind")
    val wind: Wind?=null
)

@Serializable
data class Weather(
    @SerialName("main")
    val main: String?=null,
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val iconId: String
)

@Serializable
data class WeatherDescription(
    @SerialName("temp")
    val temp: Float,
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("temp_min")
    val minTemp: Float,
    @SerialName("temp_max")
    val maxTemp: Float,
    @SerialName("pressure")
    val pressure: Float,
    @SerialName("humidity")
    val humidity: Float
)

@Serializable
data class Wind(
    @SerialName("speed")
    val speed: Float
)