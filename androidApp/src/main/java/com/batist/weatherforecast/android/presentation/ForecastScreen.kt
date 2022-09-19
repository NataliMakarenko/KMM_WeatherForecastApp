package com.batist.weatherforecast.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.batist.weatherforecast.android.R
import com.batist.weatherforecast.presentation.viewmodel.IconCode
import com.batist.weatherforecast.presentation.viewmodel.MainViewModelContract
import com.batist.weatherforecast.presentation.viewmodel.WeatherForecast
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LaunchScreen(viewModel: MainViewModelContract) {
    // Location permission state
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (locationPermissionState.allPermissionsGranted) {
        viewModel.refreshForecast()
        ForecastScreen(viewModel = viewModel)
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (locationPermissionState.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The location is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Location permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Error(errorMsg = textToShow)
            SideEffect {
                locationPermissionState.launchMultiplePermissionRequest()
            }
        }
    }
}

@Composable
fun ForecastScreen(viewModel: MainViewModelContract) {
    val state = viewModel.uiState.collectAsState()
    with(state.value) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            forecast?.let {
                ForecastView(forecast = it)
            }
            error?.let {
                Error(it)
            }
            if (isLoading) {
                Text(
                    "Loading...",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ForecastView(forecast: WeatherForecast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(
            "The weather in\n${forecast.city}, ${forecast.country}",
            color = Color.Blue,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
    Text(
        text = errorMsg,
        color = Color.Red,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center
    )
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



