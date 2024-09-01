package com.scientisthamster.run.presentation.run_overview

internal sealed interface RunOverviewAction {

    data object OnAnalyticsClick : RunOverviewAction

    data object OnLogoutClick : RunOverviewAction

    data class OnDeleteRun(val id: String) : RunOverviewAction
}