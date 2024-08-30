package com.scientisthamster.run.domain

import com.scientisthamster.core.domain.location.LocationTimestamp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationMetricsCalculator {

    fun getTotalDistanceMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { firstLocation, secondLocation ->
                firstLocation.location.location.distanceTo(secondLocation.location.location)
            }.sum().roundToInt()
        }
    }

    fun getMaxSpeedKmh(locations: List<List<LocationTimestamp>>): Double {
        return locations.maxOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { firstLocation, secondLocation ->
                val distanceMeters =
                    firstLocation.location.location.distanceTo(secondLocation.location.location)
                val elapsedTimeHours =
                    secondLocation.durationTimestamp
                        .minus(firstLocation.durationTimestamp)
                        .toDouble(DurationUnit.HOURS)

                if (elapsedTimeHours == .0) {
                    .0
                } else {
                    (distanceMeters / 1000.0) / elapsedTimeHours
                }
            }.maxOrNull() ?: .0
        }
    }

    fun getTotalElevationMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { firstLocation, secondLocation ->
                val firstLocationAltitude = firstLocation.location.altitude
                val secondLocationAltitude = secondLocation.location.altitude
                secondLocationAltitude.minus(firstLocationAltitude).coerceAtLeast(.0)
            }.sum().roundToInt()
        }
    }
}