@file:OptIn(ExperimentalCoroutinesApi::class)

package com.scientisthamster.run.domain

import com.scientisthamster.core.domain.Timer
import com.scientisthamster.core.domain.location.LocationTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class RunningTracker(
    private val locationObserver: LocationObserver,
    applicationScope: CoroutineScope
) {
    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    private val isTracking = MutableStateFlow(false)
    private val isObservingLocation = MutableStateFlow(false)

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation) {
                locationObserver.observeLocation(TRACKING_INTERVAL)
            } else flowOf()
        }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            null
        )

    init {
        isTracking
            .flatMapLatest { isTracking ->
                if (isTracking) {
                    Timer.calculateElapsedTime()
                } else flowOf()
            }
            .onEach { _elapsedTime.update { it } }
            .launchIn(applicationScope)

        combineTransform(
            currentLocation.filterNotNull(),
            isTracking
        ) { location, isTracking ->
            if (isTracking) {
                emit(location)
            }
        }
            .zip(_elapsedTime) { location, elapsedTime ->
                LocationTimestamp(
                    location = location,
                    durationTimestamp = elapsedTime
                )
            }
            .onEach { location ->
                val currentLocations = _runData.value.locations
                val updatedLocations = if (currentLocations.isNotEmpty()) {
                    currentLocations.last().plus(location)
                } else listOf(location)
                val mergedLocations = currentLocations.replaceLast(updatedLocations)

                val distanceMeters =
                    LocationMetricsCalculator.getTotalDistanceMeters(mergedLocations)
                val distanceKm = distanceMeters / 1000.0
                val currentDuration = location.durationTimestamp

                val avgSecondsPerKm =
                    if (distanceKm == .0) 0 else (currentDuration.inWholeSeconds / distanceKm).roundToInt()

                _runData.update {
                    RunData(
                        distanceMeters = distanceMeters,
                        pace = avgSecondsPerKm.seconds,
                        locations = mergedLocations
                    )
                }
            }
            .launchIn(applicationScope)
    }

    fun startObservingLocation() {
        isObservingLocation.update { true }
    }

    fun stopObservingLocation() {
        isObservingLocation.update { false }
    }

    fun setIsTracking(value: Boolean) {
        isTracking.update { value }
    }

    private fun <T> List<List<T>>.replaceLast(value: List<T>): List<List<T>> {
        if (isEmpty()) {
            return listOf(value)
        }
        return dropLast(1).plus(listOf(value))
    }

    private companion object {
        const val TRACKING_INTERVAL = 1000L
    }
}