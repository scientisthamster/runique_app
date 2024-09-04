package com.scientisthamster.analytics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scientisthamster.analytics.domain.AnalyticsRepository
import com.scientisthamster.core.presentation.ui.UiText
import com.scientisthamster.core.presentation.ui.formatted
import com.scientisthamster.core.presentation.ui.toFormattedKm
import com.scientisthamster.core.presentation.ui.toFormattedKmh
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

internal class AnalyticsDashboardViewModel(
    private val analyticsRepository: AnalyticsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AnalyticsDashboardState?>(null)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val analyticsMetrics = analyticsRepository.getAnalyticsMetrics()
            _state.update {
                it?.copy(
                    analyticsMetrics = persistentListOf(
                        AnalyticsMetric(
                            title = UiText.StringResource(R.string.total_distance_run),
                            value = (analyticsMetrics.totalDistanceRun / 1000.0).toFormattedKm()
                        ),
                        AnalyticsMetric(
                            title = UiText.StringResource(R.string.total_time_run),
                            value = analyticsMetrics.totalTimeRun.toFormattedTotalTime()
                        ),
                        AnalyticsMetric(
                            title = UiText.StringResource(R.string.fastest_ever_run),
                            value = analyticsMetrics.fastestEverRun.toFormattedKmh()
                        ),
                        AnalyticsMetric(
                            title = UiText.StringResource(R.string.avg_distance_per_run),
                            value = (analyticsMetrics.avgDistancePerRun / 1000.0).toFormattedKm()
                        ),
                        AnalyticsMetric(
                            title = UiText.StringResource(R.string.avg_pace_per_run),
                            value = analyticsMetrics.avgPacePerRun.seconds.formatted()
                        ),
                    )
                )
            }
        }
    }

    private fun Duration.toFormattedTotalTime(): String {
        val days = toLong(DurationUnit.DAYS)
        val hours = toLong(DurationUnit.HOURS) % 24
        val minutes = toLong(DurationUnit.MINUTES) % 60

        return "${days}d ${hours}h ${minutes}m"
    }
}