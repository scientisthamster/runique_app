@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    init {
        watchEmailInput()
        watchPasswordInput()
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnLoginCLick -> TODO()
            RegisterAction.OnRegisterClick -> TODO()
            RegisterAction.OnTogglePasswordVisibilityClick -> TODO()
        }
    }

    private fun watchEmailInput() {
        _state.value.email.textAsFlow()
            .onEach { email ->
                _state.update {
                    it.copy(isEmailValid = userDataValidator.isValidEmail(email.toString()))
                }
            }
            .launchIn(viewModelScope)
    }

    private fun watchPasswordInput() {
        _state.value.password.textAsFlow()
            .onEach { password ->
                _state.update {
                    it.copy(passwordValidationState = userDataValidator.validatePassword(password.toString()))
                }
            }
            .launchIn(viewModelScope)
    }
}