package com.batist.weatherforecast.data

import kotlinx.cinterop.useContents
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject
import platform.posix.err
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.native.internal.isLocal

enum class LocationAuthorizationStatus(val rawValue: Int) {
    NotSet(-1),
    NotDetermined(0),
    Restricted(1),
    Denied(2),
    AuthorizedAlways(3),
    AuthorizedWhenInUse(4);

    companion object {
        fun fromInt(value: Int) = values().first { it.rawValue == value }
    }
}

actual class LocationService(
    private val locationManager: CLLocationManager,
    private val mapper: LocationMapper
) : CLLocationManagerDelegateProtocol, NSObject() {


    private lateinit var continuation: Continuation<Result<Location>>
    actual suspend fun getCurrentLocation(): Result<Location> {
        return suspendCoroutine {
            println("Start get current location")
            continuation = it
            locationManager.requestAlwaysAuthorization()
            locationManager.requestWhenInUseAuthorization()
            if (CLLocationManager.locationServicesEnabled()) {
                print("Start updating")
                locationManager.delegate = this
                locationManager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
                locationManager.startUpdatingLocation()
            } else {
                continuation.resume(Result.failure(Error("Location services disabled")))
            }
        }
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        print("Did update")
        manager.location?.let { location ->
            location.coordinate.useContents {
                CLGeocoder().reverseGeocodeLocation(location) { list, error ->
                    val loc = (list?.get(0) as CLPlacemark?)?.let {
                        mapper.map(this, it)
                    }
                    print("Success location")
                    loc?.let {
                        print("Success location")
                        continuation.resume(Result.success(loc))
                    } ?: continuation.resume(Result.failure(Error(error?.localizedDescription())))
                }
            }
        } ?: continuation.resume(Result.failure(Error("Coordinates are nil")))
        locationManager.stopUpdatingLocation()
    }

    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        print("Authorization changed")
        when (LocationAuthorizationStatus.fromInt(didChangeAuthorizationStatus)) {
            LocationAuthorizationStatus.AuthorizedAlways,
            LocationAuthorizationStatus.AuthorizedWhenInUse -> {

            }
            LocationAuthorizationStatus.Denied -> {
                continuation.resume(Result.failure(Error("Location permissions denied")))
            }
            else -> {}
        }

    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        print("Error ${didFailWithError.localizedDescription}")
        //continuation.resume(Result.failure(Error(didFailWithError.localizedFailureReason)))
    }

}


