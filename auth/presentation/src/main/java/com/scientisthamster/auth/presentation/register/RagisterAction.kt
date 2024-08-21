package com.scientisthamster.auth.presentation.register

internal sealed interface RegisterAction {

    data object OnTogglePasswordVisibilityClick : RegisterAction

    data object OnRegisterClick : RegisterAction
}