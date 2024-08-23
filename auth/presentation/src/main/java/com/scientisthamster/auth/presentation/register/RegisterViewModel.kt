@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.auth.domain.AuthRepository
import com.scientisthamster.auth.domain.UserDataValidator
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.core.domain.util.DataError
import com.scientisthamster.core.domain.util.Result
import com.scientisthamster.core.presentation.ui.UiText
import com.scientisthamster.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _registerEventChannel = Channel<RegisterEvent>()
    val registerEvent = _registerEventChannel.receiveAsFlow()

    init {
        watchEmailInput()
        watchPasswordInput()
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.update {
                    it.copy(isPasswordVisible = !it.isPasswordVisible)
                }
            }
        }
    }

    private fun watchEmailInput() {
        _state.value.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                _state.update {
                    it.copy(
                        isEmailValid = isValidEmail,
                        canRegister = isValidEmail && it.passwordValidationState.isValidPassword && !it.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun watchPasswordInput() {
        _state.value.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState =
                    userDataValidator.validatePassword(password.toString())
                _state.update {
                    it.copy(
                        passwordValidationState = passwordValidationState,
                        canRegister = it.isEmailValid && passwordValidationState.isValidPassword && !it.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun register() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isRegistering = true,
                    canRegister = false
                )
            }
            val result = authRepository.register(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            _state.update {
                it.copy(
                    isRegistering = false,
                    canRegister = true
                )
            }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        _registerEventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_email_exists)))
                    } else {
                        _registerEventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _registerEventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}