@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.core.presentation.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.TextObfuscationMode
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scientisthamster.core.presentation.designsystem.EyeClosedIcon
import com.scientisthamster.core.presentation.designsystem.EyeOpenedIcon
import com.scientisthamster.core.presentation.designsystem.LockIcon
import com.scientisthamster.core.presentation.designsystem.R
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme

@Composable
fun RuniquePasswordTextField(
    state: TextFieldState,
    hint: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (title != null) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
        BasicSecureTextField(
            state = state,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.05f
                        )
                    } else MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textObfuscationMode = if (isPasswordVisible) TextObfuscationMode.Visible else TextObfuscationMode.Hidden,
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
            keyboardType = KeyboardType.Password,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = LockIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerTextField()
                    }
                    IconButton(onClick = onTogglePasswordVisibilityClick) {
                        Icon(
                            imageVector = if (isPasswordVisible) EyeOpenedIcon else EyeClosedIcon,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.show_password)
                            } else {
                                stringResource(id = R.string.hide_password)
                            },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun RuniqueTextFieldPreview() {
    RuniqueTheme {
        RuniquePasswordTextField(
            state = rememberTextFieldState(),
            hint = "example@test.com",
            isPasswordVisible = false,
            onTogglePasswordVisibilityClick = {},
            modifier = Modifier
                .fillMaxWidth(),
            title = "Password"
        )
    }
}