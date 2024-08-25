package com.scientisthamster.run.presentation.run_overview

internal sealed interface RunOverviewAction {

    data object OnStartClick : RunOverviewAction

    data object OnAnalyticsClick : RunOverviewAction

    data object OnLogoutClick : RunOverviewAction
}