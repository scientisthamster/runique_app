package com.scientisthamster.auth.presentation.register

import com.scientisthamster.core.presentation.ui.UiText

internal sealed interface RegisterEvent {

    data object RegistrationSuccess : RegisterEvent

    data class Error(val error: UiText) : RegisterEvent
}