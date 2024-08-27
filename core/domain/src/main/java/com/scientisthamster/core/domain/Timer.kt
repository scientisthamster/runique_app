package com.scientisthamster.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer {

    fun calculateElapsedTime(): Flow<Duration> {
        return flow {
            var previousTime = System.currentTimeMillis()
            while (true) {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - previousTime
                emit(elapsedTime.milliseconds)
                previousTime = currentTime
            }
        }
    }
}