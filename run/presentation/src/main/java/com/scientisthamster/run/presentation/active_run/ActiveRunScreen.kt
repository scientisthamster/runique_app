@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.scientisthamster.run.presentation.active_run

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.StartIcon
import com.scientisthamster.core.presentation.designsystem.StopIcon
import com.scientisthamster.core.presentation.designsystem.components.RuniqueButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueDialog
import com.scientisthamster.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueOutlinedButton
import com.scientisthamster.core.presentation.designsystem.components.RuniqueScaffold
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTopAppBar
import com.scientisthamster.run.presentation.R
import com.scientisthamster.run.presentation.active_run.components.RunBriefInformationCard
import com.scientisthamster.run.presentation.active_run.google_map.RuniqueMap
import com.scientisthamster.run.presentation.active_run.service.ActiveRunService
import com.scientisthamster.run.presentation.util.isLocationPermissionGranted
import com.scientisthamster.run.presentation.util.isNotificationPermissionGranted
import com.scientisthamster.run.presentation.util.requestRuniquePermissions
import com.scientisthamster.run.presentation.util.shouldShowLocationPermissionRationale
import com.scientisthamster.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel
import java.io.ByteArrayOutputStream

@Composable
fun ActiveRunScreenRoute(
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<ActiveRunViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    ActiveRunScreen(
        state = state,
        onServiceToggle = onServiceToggle,
        onBackClick = onBackClick,
        onSnapshotTaken = viewModel::onSnapshotTaken,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onBackClick: () -> Unit,
    onSnapshotTaken: (ByteArray) -> Unit,
    onAction: (ActiveRunAction) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val hasCourseLocationPermission =
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions[Manifest.permission.POST_NOTIFICATIONS] == true
        } else true

        val activity = context as ComponentActivity
        val shouldShowLocationRationale = activity.shouldShowLocationPermissionRationale()
        val shouldShowNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.OnSubmitLocationPermission(
                isGranted = hasCourseLocationPermission && hasFineLocationPermission,
                shouldShowRationale = shouldShowLocationRationale
            )
        )
        onAction(
            ActiveRunAction.OnSubmitNotificationPermission(
                isGranted = hasNotificationPermission,
                shouldShowRationale = shouldShowNotificationRationale
            )
        )
    }

    LaunchedEffect(state.isRunFinished) {
        if (state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    LaunchedEffect(state.shouldTrackRunning) {
        if (context.isLocationPermissionGranted() && state.shouldTrackRunning && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }
    }

    LaunchedEffect(Unit) {
        val activity = context as ComponentActivity
        val shouldShowLocationRationale = activity.shouldShowLocationPermissionRationale()
        val shouldShowNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.OnSubmitLocationPermission(
                isGranted = context.isLocationPermissionGranted(),
                shouldShowRationale = shouldShowLocationRationale
            )
        )
        onAction(
            ActiveRunAction.OnSubmitNotificationPermission(
                isGranted = context.isNotificationPermissionGranted(),
                shouldShowRationale = shouldShowNotificationRationale
            )
        )

        if (!shouldShowLocationRationale && !shouldShowNotificationRationale) {
            permissionLauncher.requestRuniquePermissions(context)
        }
    }

    RuniqueScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundWithGradient = false,
        topBar = {
            RuniqueTopAppBar(
                shouldShowBackButton = true,
                title = stringResource(id = R.string.active_run),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = onBackClick
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
            RuniqueMap(
                isRunFinished = state.isRunFinished,
                currentLocation = state.currentLocation,
                locations = state.runData.locations,
                onSnapshot = { bitmap ->
                    val stream = ByteArrayOutputStream()
                    stream.use {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, it)
                    }
                    onSnapshotTaken(stream.toByteArray())
                },
                modifier = Modifier.fillMaxSize()
            )
            RunBriefInformationCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        }
    }

    if (!state.shouldTrackRunning && state.hasStartedRunning) {
        RuniqueDialog(
            title = stringResource(id = R.string.running_is_paused),
            onDismiss = {
                onAction(ActiveRunAction.OnResumeRunClick)
            },
            description = stringResource(id = R.string.resume_or_finish_run),
            primaryButton = {
                RuniqueButton(
                    text = stringResource(id = R.string.resume),
                    isLoading = false,
                    onClick = { onAction(ActiveRunAction.OnResumeRunClick) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSavingRun
                )
            },
            secondaryButton = {
                RuniqueOutlinedButton(
                    text = stringResource(id = R.string.finish),
                    isLoading = state.isSavingRun,
                    onClick = { onAction(ActiveRunAction.OnFinishRunClick) },
                    modifier = Modifier.weight(1f),
                    enabled = !state.isSavingRun
                )
            }
        )
    }

    if (state.shouldShowLocationRationale || state.shouldShowNotificationRationale) {
        RuniqueDialog(
            title = stringResource(id = R.string.permission_required),
            description = when {
                state.shouldShowLocationRationale && state.shouldShowNotificationRationale -> {
                    stringResource(id = R.string.location_notification_rationale)
                }

                state.shouldShowLocationRationale -> {
                    stringResource(id = R.string.location_rationale)
                }

                else -> {
                    stringResource(id = R.string.notification_rationale)
                }
            },
            primaryButton = {
                RuniqueOutlinedButton(
                    text = stringResource(id = R.string.okay),
                    isLoading = false,
                    onClick = {
                        onAction(ActiveRunAction.OnDismissRationaleDialog)
                        permissionLauncher.requestRuniquePermissions(context)
                    }
                )
            },
            onDismiss = { /* Normal dismissing not allowed for permissions */ })
    }
}

@Preview
@Composable
private fun ActiveRunScreenPreview() {
    RuniqueTheme {
        ActiveRunScreen(
            state = ActiveRunState(),
            onServiceToggle = {},
            onBackClick = {},
            onSnapshotTaken = {},
            onAction = {}
        )
    }
}