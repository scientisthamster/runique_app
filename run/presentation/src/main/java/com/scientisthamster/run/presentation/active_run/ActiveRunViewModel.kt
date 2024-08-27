package com.scientisthamster.run.presentation.active_run

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class ActiveRunViewModel(
    private val runningTracker: RunningTracker
) : ViewModel() {

    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _activeRunEventChannel = Channel<ActiveRunEvent>()
    val activeRunEvent = _activeRunEventChannel.receiveAsFlow()

    private val isLocationPermissionGranted = MutableStateFlow(false)
    private val isTracking = combine(
        _state.map { it.shouldTrackRunning },
        isLocationPermissionGranted
    ) { shouldTrackRunning, isGranted ->
        shouldTrackRunning && isGranted
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        handleLocationPermissionChanges()
        handleTrackingChanges()
        watchCurrentLocation()
        watchRunData()
        watchElapsedTime()
    }

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnBackClick -> {
                _state.update { it.copy(shouldTrackRunning = false) }
            }
            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {
                _state.update { it.copy(shouldTrackRunning = true) }
            }

            ActiveRunAction.OnToggleRunClick -> {
                _state.update {
                    it.copy(
                        hasStartedRunning = true,
                        shouldTrackRunning = !it.shouldTrackRunning
                    )
                }
            }

            ActiveRunAction.OnDismissRationaleDialog -> {
                _state.update {
                    it.copy(
                        shouldShowLocationRationale = false,
                        shouldShowNotificationRationale = false
                    )
                }
            }

            is ActiveRunAction.OnSubmitLocationPermission -> {
                isLocationPermissionGranted.update { action.isGranted }
                _state.update {
                    it.copy(shouldShowLocationRationale = action.shouldShowRationale)
                }
            }

            is ActiveRunAction.OnSubmitNotificationPermission -> {
                _state.update {
                    it.copy(shouldShowNotificationRationale = action.shouldShowRationale)
                }
            }
        }
    }

    private fun handleLocationPermissionChanges() {
        isLocationPermissionGranted
            .onEach { isGranted ->
                if (isGranted) {
                    runningTracker.startObservingLocation()
                } else {
                    runningTracker.stopObservingLocation()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleTrackingChanges() {
        isTracking
            .onEach { runningTracker.setIsTracking(it) }
            .launchIn(viewModelScope)
    }

    private fun watchCurrentLocation() {
        runningTracker.currentLocation
            .onEach { location ->
                _state.update { it.copy(currentLocation = location?.location) }
            }
            .launchIn(viewModelScope)
    }

    private fun watchRunData() {
        runningTracker.runData
            .onEach { runData ->
                _state.update { it.copy(runData = runData) }
            }
            .launchIn(viewModelScope)
    }

    private fun watchElapsedTime() {
        runningTracker.elapsedTime
            .onEach { elapsedTime ->
                _state.update { it.copy(elapsedTime = elapsedTime) }
            }
            .launchIn(viewModelScope)
    }
}