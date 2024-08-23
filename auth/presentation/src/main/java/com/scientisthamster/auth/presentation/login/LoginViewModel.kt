@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.auth.presentation.login

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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _loginEventChannel = Channel<LoginEvent>()
    val loginEvent = _loginEventChannel.receiveAsFlow()

    init {
        combine(
            _state.value.email.textAsFlow(),
            _state.value.password.textAsFlow()
        ) { email, password ->
            _state.update {
                val isValidEmail = userDataValidator.isValidEmail(email.toString().trim())
                it.copy(canLogin = isValidEmail && password.isNotEmpty() && !it.isLoggingIn)
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibilityClick -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoggingIn = true,
                    canLogin = false
                )
            }
            val result = authRepository.login(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            _state.update {
                it.copy(
                    isLoggingIn = false,
                    canLogin = true
                )
            }

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        _loginEventChannel.send(
                            LoginEvent.Error(
                                UiText.StringResource(R.string.error_email_password_incorrect)
                            )
                        )
                    } else {
                        _loginEventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success -> {
                    _loginEventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}