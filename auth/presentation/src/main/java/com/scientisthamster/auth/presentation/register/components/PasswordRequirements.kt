package com.scientisthamster.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scientisthamster.auth.domain.PasswordValidationState
import com.scientisthamster.auth.domain.UserDataValidator
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.core.presentation.designsystem.CheckIcon
import com.scientisthamster.core.presentation.designsystem.CrossIcon
import com.scientisthamster.core.presentation.designsystem.RuniqueDarkRed
import com.scientisthamster.core.presentation.designsystem.RuniqueGreen

@Composable
internal fun PasswordRequirements(passwordValidationState: PasswordValidationState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_characters,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = passwordValidationState.hasMinLength
        )
        PasswordRequirement(
            text = stringResource(id = R.string.at_least_one_number),
            isValid = passwordValidationState.hasNumber
        )
        PasswordRequirement(
            text = stringResource(id = R.string.contains_lowercase_char),
            isValid = passwordValidationState.hasLowerCaseCharacter
        )
        PasswordRequirement(
            text = stringResource(id = R.string.contains_uppercase_char),
            isValid = passwordValidationState.hasUpperCaseCharacter
        )
    }
}

@Composable
private fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if (isValid) RuniqueGreen else RuniqueDarkRed
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}