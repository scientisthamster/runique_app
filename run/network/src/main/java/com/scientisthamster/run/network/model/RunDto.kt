package com.scientisthamster.run.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunDto(
    val id: String,
    val dateTimeUtc: String,
    val durationMillis: Long,
    val distanceMeters: Int,
    @SerialName("lat")
    val latitude: Double,
    @SerialName("long")
    val longitude: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?
)