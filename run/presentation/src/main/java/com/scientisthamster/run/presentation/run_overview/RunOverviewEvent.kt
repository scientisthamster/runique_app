package com.scientisthamster.run.presentation.run_overview

internal sealed interface RunOverviewEvent {

    data object OpenAnalytics : RunOverviewEvent

    data object Logout : RunOverviewEvent
}