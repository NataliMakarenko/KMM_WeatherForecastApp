package com.batist.weatherforecast.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

actual class CacheDataSource(private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()) {

    actual fun getCache(): WeatherInfo? {
        return userDefaults.stringForKey(CACHE)?.let {
            val cacheData: WeatherInfo = Json.decodeFromString(it)
            cacheData
        }
    }

    actual fun saveCache(cacheData: WeatherInfo) {
        val json = Json.encodeToString(cacheData)
        userDefaults.setObject(json, CACHE)
        userDefaults.synchronize()
    }
}