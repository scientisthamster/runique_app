@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.auth.presentation.login.components.HeaderBlock
import com.scientisthamster.auth.presentation.login.components.SignUpBlock
import com.scientisthamster.core.presentation.designsystem.EmailIcon
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.GradientBackground
import com.scientisthamster.core.presentation.designsystem.components.RuniqueButton
import com.scientisthamster.core.presentation.designsystem.components.RuniquePasswordTextField
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTextField
import com.scientisthamster.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoute(
    onLoginSuccess: () -> Unit,
    onSingUpClick: () -> Unit
) {

    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.loginEvent) { event ->
        when (event) {
            is LoginEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }

            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.youre_logged_in,
                    Toast.LENGTH_LONG
                ).show()
                onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = state,
        onSingUpClick = onSingUpClick,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onSingUpClick: () -> Unit,
    onAction: (LoginAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp, bottom = 32.dp)
        ) {
            HeaderBlock()
            Spacer(modifier = Modifier.height(48.dp))
            RuniqueTextField(
                state = state.email,
                hint = stringResource(id = R.string.example_email),
                modifier = Modifier.fillMaxWidth(),
                startIcon = EmailIcon,
                title = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            RuniquePasswordTextField(
                state = state.password,
                hint = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibilityClick = { onAction(LoginAction.OnTogglePasswordVisibilityClick) },
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.password)
            )
            Spacer(modifier = Modifier.height(32.dp))
            RuniqueButton(
                text = stringResource(id = R.string.login),
                isLoading = state.isLoggingIn,
                onClick = { onAction(LoginAction.OnLoginClick) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canLogin
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.BottomCenter
            ) {
                SignUpBlock(onClick = onSingUpClick)
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    RuniqueTheme {
        LoginScreen(
            state = LoginState(),
            onSingUpClick = {},
            onAction = {}
        )
    }
}