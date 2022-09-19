package com.batist.weatherforecast.presentation.viewmodel

import com.batist.weatherforecast.domain.GetWeatherUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class WeatherForecastUiState(
    val isLoading: Boolean,
    val forecast: WeatherForecast?,
    val error: String?
)

interface MainViewModelContract {
    val uiState: StateFlow<WeatherForecastUiState>
    val viewModelScope: CoroutineScope
    fun refreshForecast()
    fun clear()
}

internal expect class MainViewModel {
    val uiState: StateFlow<WeatherForecastUiState>
    fun refreshForecast()
    fun clear()
}

internal class BaseMainViewModel(
    override val viewModelScope: CoroutineScope,
    private val weatherUseCase: GetWeatherUseCase
) : MainViewModelContract {
    private val _uiState: MutableStateFlow<WeatherForecastUiState> =
        MutableStateFlow(WeatherForecastUiState(false, null, null))
    override val uiState: StateFlow<WeatherForecastUiState> = _uiState


    override fun refreshForecast() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            weatherUseCase.getWeather().collect { result ->
                if (result.isSuccess) {
                    val cacheData = result.getOrNull()
                    _uiState.value = cacheData?.let {
                        val weather = it.weatherForecastEntity.weather?.firstOrNull()
                        val forecast = WeatherForecast(
                            it.location.country, it.location.city,
                            weatherDescription = weather?.main
                                ?: "Unknown",
                            wind = it.weatherForecastEntity.wind?.speed.toString(),
                            temp = it.weatherForecastEntity.weatherDescription.temp.toString(),
                            feelsLike = it.weatherForecastEntity.weatherDescription.feelsLike.toString(),
                            humidity = it.weatherForecastEntity.weatherDescription.humidity.toString(),
                            pressure = it.weatherForecastEntity.weatherDescription.pressure.toString(),
                            icon = IconCode.fromId(weather?.iconId)
                        )
                        _uiState.value.copy(
                            isLoading = false,
                            forecast = forecast
                        )
                    } ?: _uiState.value.copy(
                        error = result.exceptionOrNull()?.message ?: "Unknown error",
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = result.exceptionOrNull()?.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun clear() {
        viewModelScope.cancel()
    }
}