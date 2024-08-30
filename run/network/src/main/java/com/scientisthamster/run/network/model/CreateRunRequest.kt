package com.scientisthamster.run.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRunRequest(
    val id: String,
    val durationMillis: Long,
    val distanceMeters: Int,
    val epochMillis: Long,
    @SerialName("lat")
    val latitude: Double,
    @SerialName("long")
    val longitude: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationMeters: Int
)