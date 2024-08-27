package com.scientisthamster.run.presentation.active_run.google_map

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import com.scientisthamster.core.domain.location.LocationTimestamp
import kotlin.math.abs

object PolylineColorCalculator {

    fun calculatePolylineColor(location1: LocationTimestamp, location2: LocationTimestamp): Color {
        val distanceMeters = location1.location.location.distanceTo(location2.location.location)
        val elapsedTime =
            abs(location2.durationTimestamp.minus(location1.durationTimestamp).inWholeSeconds)
        val speedKmh = (distanceMeters / elapsedTime) * 3.6

        return interpolateColor(
            speedKmh = speedKmh,
            minSpeed = 5.0,
            maxSpeed = 20.0,
            fastSpeedColor = Color.Green,
            mediumSpeedColor = Color.Yellow,
            lowSpeedColor = Color.Red
        )
    }

    private fun interpolateColor(
        speedKmh: Double,
        minSpeed: Double,
        maxSpeed: Double,
        fastSpeedColor: Color,
        mediumSpeedColor: Color,
        lowSpeedColor: Color
    ): Color {
        val ratio = ((speedKmh - minSpeed) / (maxSpeed - minSpeed)).coerceIn(0.0..1.0)
        val colorInt = if (ratio <= 0.5) {
            val midRatio = ratio / 0.5
            ColorUtils.blendARGB(
                fastSpeedColor.toArgb(),
                mediumSpeedColor.toArgb(),
                midRatio.toFloat()
            )
        } else {
            val midToEndRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(
                mediumSpeedColor.toArgb(),
                lowSpeedColor.toArgb(),
                midToEndRatio.toFloat()
            )
        }

        return Color(colorInt)
    }
}