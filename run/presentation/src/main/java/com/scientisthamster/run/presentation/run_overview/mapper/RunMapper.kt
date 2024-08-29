package com.scientisthamster.run.presentation.run_overview.mapper

import com.scientisthamster.core.domain.run.Run
import com.scientisthamster.core.presentation.ui.formatted
import com.scientisthamster.core.presentation.ui.toFormattedKm
import com.scientisthamster.core.presentation.ui.toFormattedKmh
import com.scientisthamster.core.presentation.ui.toFormattedMeters
import com.scientisthamster.core.presentation.ui.toFormattedPace
import com.scientisthamster.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTimeZone = dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault())
    val formattedTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTimeZone)
    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id.orEmpty(),
        duration = duration.formatted(),
        dateTime = formattedTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}