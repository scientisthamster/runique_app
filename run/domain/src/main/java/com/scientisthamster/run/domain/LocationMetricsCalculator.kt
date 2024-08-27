package com.scientisthamster.run.domain

import com.scientisthamster.core.domain.location.LocationTimestamp
import kotlin.math.roundToInt

object LocationMetricsCalculator {

    fun getTotalDistanceMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { firstLocation, secondLocation ->
                firstLocation.location.location.distanceTo(secondLocation.location.location)
            }.sum().roundToInt()
        }
    }
}