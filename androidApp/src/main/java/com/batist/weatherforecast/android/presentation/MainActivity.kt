package com.batist.weatherforecast.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.batist.weatherforecast.di.DependenciesProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DependenciesProvider.injectContext(applicationContext)
        setContent {
            ForecastScreen(DependenciesProvider.provideMainViewModel())
        }
    }
}
