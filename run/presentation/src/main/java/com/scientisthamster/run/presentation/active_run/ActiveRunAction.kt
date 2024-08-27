package com.scientisthamster.run.presentation.active_run

internal sealed interface ActiveRunAction {

    data object OnBackClick : ActiveRunAction

    data object OnToggleRunClick : ActiveRunAction

    data object OnFinishRunClick : ActiveRunAction

    data object OnResumeRunClick : ActiveRunAction

    data class OnSubmitLocationPermission(
        val isGranted: Boolean,
        val shouldShowRationale: Boolean
    ) : ActiveRunAction

    data class OnSubmitNotificationPermission(
        val isGranted: Boolean,
        val shouldShowRationale: Boolean
    ) : ActiveRunAction

    data object OnDismissRationaleDialog : ActiveRunAction
}