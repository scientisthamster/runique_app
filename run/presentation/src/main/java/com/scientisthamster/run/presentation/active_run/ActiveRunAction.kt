package com.scientisthamster.run.presentation.active_run

internal sealed interface ActiveRunAction {

    data object OnToggleRunClick : ActiveRunAction

    data object OnFinishRunClick : ActiveRunAction

    data object OnResumeRunClick : ActiveRunAction

    data object OnBackClick : ActiveRunAction
}