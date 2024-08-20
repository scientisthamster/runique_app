package com.scientisthamster.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.scientisthamster.auth.presentation.intro.components.RuniqueLogoVertical
import com.scientisthamster.auth.presentation.intro.components.WelcomeBlock
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.GradientBackground

@Composable
fun IntroScreenRoute(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSingUpClick -> onSignUpClick()
            }
        }
    )
}

@Composable
private fun IntroScreen(onAction: (IntroAction) -> Unit) {
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            RuniqueLogoVertical()
        }
        WelcomeBlock(
            onSignInClick = {
                onAction(IntroAction.OnSignInClick)
            },
            onSingUpClick = {
                onAction(IntroAction.OnSingUpClick)
            }
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    RuniqueTheme {
        IntroScreen(onAction = {})
    }
}