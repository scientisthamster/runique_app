package com.scientisthamster.run.domain

import com.scientisthamster.core.domain.location.LocationTimestamp
import kotlin.time.Duration

data class RunData(
    val distanceInMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimestamp>> = emptyList()
)