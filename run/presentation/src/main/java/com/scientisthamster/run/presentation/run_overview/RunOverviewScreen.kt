@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.scientisthamster.run.presentation.run_overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.core.presentation.designsystem.AnalyticsIcon
import com.scientisthamster.core.presentation.designsystem.LogoIcon
import com.scientisthamster.core.presentation.designsystem.LogoutIcon
import com.scientisthamster.core.presentation.designsystem.RunIcon
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueScaffold
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTopAppBar
import com.scientisthamster.core.presentation.designsystem.components.util.DropDownItem
import com.scientisthamster.core.presentation.ui.ObserveAsEvents
import com.scientisthamster.run.presentation.R
import com.scientisthamster.run.presentation.run_overview.components.RunCard
import com.scientisthamster.run.presentation.run_overview.model.RunUi
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRoute(
    onStartRunClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val viewModel = koinViewModel<RunOverviewViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.runOverviewEventChannel) {
        when (it) {
            RunOverviewEvent.Logout -> onLogoutClick()
        }
    }

    RunOverviewScreen(
        state = state,
        onStartRunClick = onStartRunClick,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RunOverviewScreen(
    state: RunOverviewState,
    onStartRunClick: () -> Unit,
    onAction: (RunOverviewAction) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
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
                onClick = onStartRunClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = state.runs, key = RunUi::id) { runUi ->
                RunCard(
                    runUi = runUi,
                    onDeleteClick = { onAction(RunOverviewAction.OnDeleteRun(runUi.id)) },
                    modifier = Modifier.animateItemPlacement()
                )
            }
        }
    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RuniqueTheme {
        RunOverviewScreen(
            state = RunOverviewState(),
            onStartRunClick = {},
            onAction = {}
        )
    }
}