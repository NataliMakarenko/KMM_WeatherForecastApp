package com.batist.weatherforecast.presentation.viewmodel

data class WeatherForecast(
    val country: String,
    val city: String,
    val weatherDescription: String,
    val wind: String,
    val temp: String,
    val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val icon: IconCode?
)

enum class IconCode {
    CLEAR_SKY,
    FEW_CLOUDS,
    SCATTERED_CLOUDS,
    BROKEN_CLOUDS,
    SHOWER_RAIN,
    RAIN,
    THUNDERSTORM,
    SNOW,
    MIST;

    companion object {
        fun fromId(id: String?): IconCode? {
            if (id == null) {
                return null
            }
            return when {
                id.startsWith("01") -> CLEAR_SKY
                id.startsWith("02") -> FEW_CLOUDS
                id.startsWith("03") -> SCATTERED_CLOUDS
                id.startsWith("04") -> BROKEN_CLOUDS
                id.startsWith("09") -> SHOWER_RAIN
                id.startsWith("10") -> RAIN
                id.startsWith("11") -> THUNDERSTORM
                id.startsWith("13") -> SNOW
                id.startsWith("50") -> MIST
                else -> null
            }
        }
    }
}