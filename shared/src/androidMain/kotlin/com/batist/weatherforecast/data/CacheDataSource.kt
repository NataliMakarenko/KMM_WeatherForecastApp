package com.batist.weatherforecast.data

import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

actual class CacheDataSource(private val sharedPrefs: SharedPreferences) {

    actual fun getCache(): WeatherInfo? {
        val cache = sharedPrefs.getString(CACHE, null)
        return cache?.let {
            val cacheData: WeatherInfo = Json.decodeFromString(cache)
            cacheData
        }

    }

    actual fun saveCache(cacheData: WeatherInfo) {
        val json = Json.encodeToString(cacheData)
        sharedPrefs.edit().putString(CACHE, json).commit()
    }
}