package com.scientisthamster.analytics.presentation

import com.scientisthamster.core.presentation.ui.UiText
import kotlinx.collections.immutable.ImmutableList

internal data class AnalyticsDashboardState(
    val analyticsMetrics: ImmutableList<AnalyticsMetric>
)

internal data class AnalyticsMetric(
    val title: UiText,
    val value: String
)