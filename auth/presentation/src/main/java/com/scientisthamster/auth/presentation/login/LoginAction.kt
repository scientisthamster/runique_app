package com.scientisthamster.auth.presentation.login

internal sealed interface LoginAction {

    data object OnTogglePasswordVisibilityClick : LoginAction

    data object OnLoginClick : LoginAction
}