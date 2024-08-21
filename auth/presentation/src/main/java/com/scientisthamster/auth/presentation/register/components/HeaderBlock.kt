package com.scientisthamster.auth.presentation.register.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.scientisthamster.auth.presentation.R
import com.scientisthamster.core.presentation.designsystem.Poppins
import com.scientisthamster.core.presentation.designsystem.RuniqueGray

@Composable
internal fun ColumnScope.HeaderBlock(onClick: () -> Unit) {
    Text(
        text = stringResource(id = R.string.create_account),
        style = MaterialTheme.typography.headlineMedium
    )

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = RuniqueGray,
                fontFamily = Poppins
            )
        ) {
            append(stringResource(id = R.string.already_have_an_account) + " ")
            pushStringAnnotation(
                tag = "clickable_text",
                annotation = stringResource(id = R.string.login)
            )
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            ) {
                append(stringResource(id = R.string.login))
            }
        }
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "clickable_text",
                start = offset,
                end = offset
            ).firstOrNull()?.also {
                onClick()
            }
        }
    )
}