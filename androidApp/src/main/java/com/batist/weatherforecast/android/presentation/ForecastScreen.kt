package com.batist.weatherforecast.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.batist.weatherforecast.android.R
import com.batist.weatherforecast.presentation.viewmodel.IconCode
import com.batist.weatherforecast.presentation.viewmodel.MainViewModelContract
import com.batist.weatherforecast.presentation.viewmodel.WeatherForecast

@Composable
fun ForecastScreen(viewModel: MainViewModelContract) {
    val state = viewModel.uiState.collectAsState()
    with(state.value) {
        Column() {
            forecast?.let {
                ForecastView(forecast = it)
            }
            error?.let {
                Error(it)
            }
            if (isLoading) {
                Text(
                    "Loading...",
                )
            }
        }

    }
}

@Composable
fun ForecastView(forecast: WeatherForecast) {
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            "The weather in\n${forecast.city}, ${forecast.country}",
            color = Color.Blue,
            style = MaterialTheme.typography.h4
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            WeatherIcon(iconCode = forecast.icon)
            Column {
                WeatherText(text = "Wind ${forecast.wind}")
                WeatherText(text = "Temp ${forecast.temp}")
                WeatherText(text = "Feels like ${forecast.feelsLike}")
                WeatherText(text = "Pressure ${forecast.pressure}")
            }
        }
    }
}

@Composable
fun Error(errorMsg: String) {
    Text(text = errorMsg)
}

@Composable
fun WeatherIcon(iconCode: IconCode?) {
    fun getIconResource(iconCode: IconCode?): Int {
        return when (iconCode) {
            IconCode.CLEAR_SKY -> R.drawable.clear_sky
            IconCode.FEW_CLOUDS -> R.drawable.few_clouds
            IconCode.SCATTERED_CLOUDS -> R.drawable.few_clouds
            IconCode.BROKEN_CLOUDS -> R.drawable.few_clouds
            IconCode.SHOWER_RAIN -> R.drawable.shower_rain
            IconCode.RAIN -> R.drawable.rain
            IconCode.THUNDERSTORM -> R.drawable.thunderstorm
            IconCode.SNOW -> R.drawable.snow
            IconCode.MIST -> R.drawable.mist
            else -> R.drawable.clear_sky //TODO add default
        }
    }
    Image(
        painter = painterResource(id = getIconResource(iconCode)),
        contentDescription = "Weather icon",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun WeatherText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.body1,
        color = Color.Blue,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}



