package com.batist.weatherforecast.data

interface LocationMapperContract<T, R> {
    fun map(nativeLocation: T, nativeGeoCoderData: R): Location
}



