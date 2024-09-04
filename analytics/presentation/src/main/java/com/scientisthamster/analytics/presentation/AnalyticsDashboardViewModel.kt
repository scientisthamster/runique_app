package com.scientisthamster.analytics.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AnalyticsDashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow<AnalyticsDashboardState?>(null)
    val state = _state.asStateFlow()
}