package com.scientisthamster.run.presentation.run_overview

import com.scientisthamster.run.presentation.run_overview.model.RunUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class RunOverviewState(
    val runs: ImmutableList<RunUi> = persistentListOf()
)