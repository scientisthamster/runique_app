@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.scientisthamster.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginCLick -> TODO()
            RegisterAction.OnRegisterClick -> TODO()
            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }
}