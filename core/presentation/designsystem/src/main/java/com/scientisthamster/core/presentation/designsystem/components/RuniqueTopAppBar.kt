@file:OptIn(ExperimentalMaterial3Api::class)

package com.scientisthamster.core.presentation.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scientisthamster.core.presentation.designsystem.AnalyticsIcon
import com.scientisthamster.core.presentation.designsystem.ArrowLeftIcon
import com.scientisthamster.core.presentation.designsystem.LogoIcon
import com.scientisthamster.core.presentation.designsystem.Poppins
import com.scientisthamster.core.presentation.designsystem.R
import com.scientisthamster.core.presentation.designsystem.RuniqueGreen
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.util.DropDownItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RuniqueTopAppBar(
    shouldShowBackButton: Boolean,
    title: String,
    modifier: Modifier = Modifier,
    menuItems: ImmutableList<DropDownItem> = persistentListOf(),
    onMenuItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
    startContent: (@Composable () -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    var isDropDownMenuOpen by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                startContent?.invoke()
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (shouldShowBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = ArrowLeftIcon,
                        contentDescription = stringResource(id = R.string.go_back),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        actions = {
            if (menuItems.isNotEmpty()) {
                Box {
                    IconButton(onClick = { isDropDownMenuOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.open_menu),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    DropdownMenu(
                        expanded = isDropDownMenuOpen,
                        onDismissRequest = { isDropDownMenuOpen = false }
                    ) {
                        menuItems.forEachIndexed { index, dropDownItem ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { onMenuItemClick(index) }
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = dropDownItem.icon,
                                    contentDescription = dropDownItem.title
                                )
                                Text(text = dropDownItem.title)
                            }
                        }
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        scrollBehavior = scrollBehavior
    )
}

@Preview
@Composable
private fun RuniqueTopAppBarPreview() {
    RuniqueTheme {
        RuniqueTopAppBar(
            shouldShowBackButton = false,
            title = "Runique",
            modifier = Modifier.fillMaxWidth(),
            startContent = {
                Icon(
                    imageVector = LogoIcon,
                    contentDescription = null,
                    tint = RuniqueGreen,
                    modifier = Modifier.size(30.dp)
                )
            },
            menuItems = persistentListOf(
                DropDownItem(
                    icon = AnalyticsIcon,
                    title = "Analytics"
                )
            )
        )
    }
}

