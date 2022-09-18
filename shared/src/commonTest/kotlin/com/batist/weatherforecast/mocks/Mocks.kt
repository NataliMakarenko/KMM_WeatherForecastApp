package com.batist.weatherforecast.mocks

import com.batist.weatherforecast.data.Location
import com.batist.weatherforecast.data.network.WeatherDescription
import com.batist.weatherforecast.data.network.WeatherForecastEntity

object Mocks {

    val location = Location(0.0, 0.0, "Some country", "Some city")
    val weatherForecastForCache =
        WeatherForecastEntity(weatherDescription = WeatherDescription(0f, 0f, 0f, 0f, 0f, 0f))
    val weatherForecast = WeatherForecastEntity(
        weatherDescription = WeatherDescription(
            10f,
            9f,
            0f,
            0f,
            0f,
            80f
        )
    )
}