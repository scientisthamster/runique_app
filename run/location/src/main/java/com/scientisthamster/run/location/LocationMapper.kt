package com.scientisthamster.run.location

import android.location.Location
import com.scientisthamster.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.scientisthamster.core.domain.location.Location(
            latitude = latitude,
            longitude = longitude
        ),
        altitude = altitude
    )
}