package com.scientisthamster.auth.presentation.intro.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.core.presentation.designsystem.components.RuniqueButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueOutlinedButton

@Composable
internal fun WelcomeBlock(
    onSignInClick: () -> Unit,
    onSingUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 48.dp)
    ) {
        Text(
            text = stringResource(id = R.string.welcome_to_runique),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.runique_description),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        RuniqueOutlinedButton(
            text = stringResource(id = R.string.sign_in),
            isLoading = false,
            onClick = onSignInClick,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        RuniqueButton(
            text = stringResource(id = R.string.sign_up),
            isLoading = false,
            onClick = onSingUpClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}