package com.scientisthamster.run.presentation.active_run

import com.scientisthamster.core.presentation.ui.UiText

internal sealed interface ActiveRunEvent {

    data class Error(val error: UiText) : ActiveRunEvent

    data object RunSaved : ActiveRunEvent
}