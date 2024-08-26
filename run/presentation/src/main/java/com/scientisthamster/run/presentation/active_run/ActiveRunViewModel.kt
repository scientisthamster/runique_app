package com.scientisthamster.run.presentation.active_run

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.run.domain.RunningTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

internal class ActiveRunViewModel(
    private val runningTracker: RunningTracker
) : ViewModel() {

    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _activeRunEventChannel = Channel<ActiveRunEvent>()
    val activeRunEvent = _activeRunEventChannel.receiveAsFlow()

    private val _isLocationPermissionGranted = MutableStateFlow(false)

    init {
        handleLocationPermissionChanges()

        // TODO will be deleted
        runningTracker.currentLocation
            .onEach {
                Timber.d("New location $it")
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnFinishRunClick -> {}
            ActiveRunAction.OnResumeRunClick -> {}
            ActiveRunAction.OnToggleRunClick -> {}
            ActiveRunAction.OnDismissRationaleDialog -> {
                _state.update {
                    it.copy(
                        shouldShowLocationRationale = false,
                        shouldShowNotificationRationale = false
                    )
                }
            }

            is ActiveRunAction.OnSubmitLocationPermission -> {
                _isLocationPermissionGranted.update { action.isGranted }
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
        _isLocationPermissionGranted
            .onEach { isGranted ->
                if (isGranted) {
                    runningTracker.startObservingLocation()
                } else {
                    runningTracker.stopObservingLocation()
                }
            }
            .launchIn(viewModelScope)
    }
}