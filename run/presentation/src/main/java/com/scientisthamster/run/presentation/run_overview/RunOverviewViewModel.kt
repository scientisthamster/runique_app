package com.scientisthamster.run.presentation.run_overview

import androidx.lifecycle.ViewModel

internal class RunOverviewViewModel : ViewModel() {

    fun onAction(action: RunOverviewAction) {
        when (action) {
            RunOverviewAction.OnAnalyticsClick -> {}
            RunOverviewAction.OnLogoutClick -> {}
            RunOverviewAction.OnStartClick -> {}
        }
    }
}