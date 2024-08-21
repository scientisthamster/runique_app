@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.auth.presentation.register.components.HeaderBlock
import com.scientisthamster.auth.presentation.register.components.PasswordRequirements
import com.scientisthamster.core.presentation.designsystem.CheckIcon
import com.scientisthamster.core.presentation.designsystem.EmailIcon
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.GradientBackground
import com.scientisthamster.core.presentation.designsystem.components.RuniqueButton
import com.scientisthamster.core.presentation.designsystem.components.RuniquePasswordTextField
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTextField
import com.scientisthamster.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoute(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit
) {
    val viewModel = koinViewModel<RegisterViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.registerEvent) { event ->
        when (event) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = state,
        onSignInClick = onSignInClick,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onSignInClick: () -> Unit,
    onAction: (RegisterAction) -> Unit,
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp, bottom = 32.dp)
        ) {
            HeaderBlock(onClick = { })
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                hint = stringResource(id = R.string.example_email),
                modifier = Modifier.fillMaxWidth(),
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                title = stringResource(id = R.string.email),
                additionalInfo = stringResource(id = R.string.must_be_a_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibilityClick = { onAction(RegisterAction.OnTogglePasswordVisibilityClick) },
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordRequirements(passwordValidationState = state.passwordValidationState)
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                onClick = { onAction(RegisterAction.OnRegisterClick) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canRegister
            )
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RuniqueTheme {
        RegisterScreen(
            state = RegisterState(),
            onSignInClick = {},
            onAction = {}
        )
    }
}