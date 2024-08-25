package com.scientisthamster.run.presentation.active_run

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

internal class ActiveRunViewModel : ViewModel() {

    private val _state = MutableStateFlow(ActiveRunState())
    val state = _state.asStateFlow()

    private val _activeRunEventChannel = Channel<ActiveRunEvent>()
    val activeRunEvent = _activeRunEventChannel.receiveAsFlow()

    fun onAction(action: ActiveRunAction) {
        when (action) {
            ActiveRunAction.OnBackClick -> TODO()
            ActiveRunAction.OnFinishRunClick -> TODO()
            ActiveRunAction.OnResumeRunClick -> TODO()
            ActiveRunAction.OnToggleRunClick -> TODO()
        }
    }
}