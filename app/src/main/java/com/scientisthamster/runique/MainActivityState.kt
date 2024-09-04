package com.scientisthamster.runique

data class MainActivityState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val shouldShowAnalyticsDialog: Boolean = false
)