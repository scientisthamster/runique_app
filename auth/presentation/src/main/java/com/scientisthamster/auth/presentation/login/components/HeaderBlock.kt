package com.scientisthamster.auth.presentation.login.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.scientisthamster.auth.presentation.R

@Composable
internal fun ColumnScope.HeaderBlock() {
    Text(
        text = stringResource(id = R.string.hi_there),
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        text = stringResource(id = R.string.runique_welcome_text),
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}