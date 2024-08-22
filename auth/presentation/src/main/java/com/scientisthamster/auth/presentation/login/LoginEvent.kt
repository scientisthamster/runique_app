package com.scientisthamster.auth.presentation.login

import com.scientisthamster.core.presentation.ui.UiText

internal sealed interface LoginEvent {

    data class Error(val error: UiText) : LoginEvent

    data object LoginSuccess : LoginEvent
}