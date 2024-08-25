@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.scientisthamster.run.presentation.active_run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.StartIcon
import com.scientisthamster.core.presentation.designsystem.StopIcon
import com.scientisthamster.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueScaffold
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTopAppBar
import com.scientisthamster.run.presentation.R
import com.scientisthamster.run.presentation.active_run.components.RunBriefInformationCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveRunScreenRoute() {

    val viewModel = koinViewModel<ActiveRunViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    ActiveRunScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onAction: (ActiveRunAction) -> Unit
) {
    RuniqueScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundWithGradient = false,
        topBar = {
            RuniqueTopAppBar(
                shouldShowBackButton = true,
                title = stringResource(id = R.string.active_run),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { onAction(ActiveRunAction.OnBackClick) }
            )
        },
        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = if (state.shouldTrackRunning) StopIcon else StartIcon,
                onClick = { onAction(ActiveRunAction.OnToggleRunClick) },
                contentDescription = if (state.shouldTrackRunning) {
                    stringResource(id = R.string.pause_run)
                } else {
                    stringResource(id = R.string.start_run)
                },
                iconSize = 20.dp
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            RunBriefInformationCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RuniqueTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onAction = {}
        )
    }
}