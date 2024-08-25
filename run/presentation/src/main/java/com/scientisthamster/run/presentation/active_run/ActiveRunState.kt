package com.scientisthamster.run.presentation.active_run

import com.scientisthamster.core.domain.location.Location
import com.scientisthamster.run.domain.RunData
import kotlin.time.Duration

internal data class ActiveRunState(
    val elapsedTime: Duration = Duration.ZERO,
    val runData: RunData = RunData(),
    val shouldTrackRunning: Boolean = false,
    val hasStartedRunning: Boolean = false,
    val currentLocation: Location? = null,
    val isRunFinished: Boolean = false,
    val isSavingRun: Boolean = false
)