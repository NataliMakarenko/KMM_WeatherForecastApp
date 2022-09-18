package com.batist.weatherforecast.domain

import com.batist.weatherforecast.data.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface GetWeatherRepositoryContract {
     fun getWeather(): Flow<Result<WeatherInfo>>
}
