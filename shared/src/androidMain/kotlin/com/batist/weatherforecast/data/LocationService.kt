package com.batist.weatherforecast.data

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocationService(
    private val locationProvider: FusedLocationProviderClient,
    private val mapper: LocationMapper,
    private val geocoder: Geocoder
) : LocationServiceContract {

    @SuppressLint("MissingPermission")
    actual override suspend fun getCurrentLocation(): Result<Location> {
        return suspendCoroutine { continuation ->
            val task =
                locationProvider.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            task.addOnSuccessListener { location ->
                location?.let{
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val address = addresses?.firstOrNull()
                    address?.let {
                        continuation.resume(Result.success(mapper.map(location, address)))
                    } ?: continuation.resume(Result.failure(Error("Error while retrieve address")))
                }?: continuation.resume(Result.failure(Error("Error while retrieve location")))

            }
            task.addOnCanceledListener {
                continuation.resume(Result.failure(Throwable("Cancelled")))
            }
            task.addOnFailureListener { error ->
                continuation.resume(Result.failure(error))
            }
        }
    }
}