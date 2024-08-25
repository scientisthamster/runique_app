@file:OptIn(ExperimentalMaterial3Api::class)

package com.scientisthamster.run.presentation.run_overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scientisthamster.core.presentation.designsystem.AnalyticsIcon
import com.scientisthamster.core.presentation.designsystem.LogoIcon
import com.scientisthamster.core.presentation.designsystem.LogoutIcon
import com.scientisthamster.core.presentation.designsystem.RunIcon
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueScaffold
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTopAppBar
import com.scientisthamster.core.presentation.designsystem.components.util.DropDownItem
import com.scientisthamster.run.presentation.R
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoute() {

    val viewModel = koinViewModel<RunOverviewViewModel>()

    RunOverviewScreen(onAction = viewModel::onAction)
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(state = rememberTopAppBarState())

    RuniqueScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RuniqueTopAppBar(
                shouldShowBackButton = false,
                title = stringResource(id = R.string.runique),
                modifier = Modifier.fillMaxWidth(),
                menuItems = persistentListOf(
                    DropDownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropDownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) }
            )
        }
    ) {

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RuniqueTheme {
        RunOverviewScreen(onAction = {})
    }
}