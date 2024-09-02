package com.scientisthamster.run.presentation.run_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.core.domain.run.Run
import com.scientisthamster.core.domain.run.RunRepository
import com.scientisthamster.core.domain.run.SyncRunScheduler
import com.scientisthamster.run.presentation.run_overview.mapper.toRunUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

internal class RunOverviewViewModel(
    private val runRepository: RunRepository,
    private val syncRunScheduler: SyncRunScheduler
) : ViewModel() {

    private val _state = MutableStateFlow(RunOverviewState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            syncRunScheduler.scheduleSync(
                type = SyncRunScheduler.SyncType.FetchRuns(30.minutes)
            )
        }
        watchRuns()
        viewModelScope.launch {
            runRepository.syncPendingRuns()
            runRepository.fetchRuns()
        }
    }

    fun onAction(action: RunOverviewAction) {
        when (action) {
            RunOverviewAction.OnAnalyticsClick -> {}
            RunOverviewAction.OnLogoutClick -> {}
            is RunOverviewAction.OnDeleteRun -> {
                viewModelScope.launch {
                    runRepository.deleteRun(action.id)
                }
            }
        }
    }

    private fun watchRuns() {
        runRepository.getRuns()
            .onEach { runs ->
                _state.update {
                    it.copy(
                        runs = runs.map(Run::toRunUi).toImmutableList()
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}