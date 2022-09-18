package com.batist.weatherforecast.data

import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLPlacemark

class LocationMapper : LocationMapperContract<CLLocationCoordinate2D, CLPlacemark> {
    override fun map(nativeLocation: CLLocationCoordinate2D, nativeGeoCoderData: CLPlacemark): Location {
        return Location(
                nativeLocation.latitude, nativeLocation.longitude, nativeGeoCoderData.country
                    ?: "",
                nativeGeoCoderData.locality ?: ""
            )

    }
}