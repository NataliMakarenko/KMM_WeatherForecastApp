package com.batist.weatherforecast.data

import android.location.Address
import android.location.Location

class LocationMapper : LocationMapperContract<Location, Address> {
    override fun map(
        nativeLocation: Location,
        address: Address
    ): com.batist.weatherforecast.data.Location {
        return Location(
            nativeLocation.latitude,
            nativeLocation.longitude,
            address.countryName,
            address.locality.filter { it.isLetterOrDigit() }
        )
    }
}