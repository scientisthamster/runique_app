@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class
)

package com.scientisthamster.analytics.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.scientisthamster.analytics.presentation.components.AnalyticsCard
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.designsystem.components.RuniqueScaffold
import com.scientisthamster.core.presentation.designsystem.components.RuniqueTopAppBar
import com.scientisthamster.core.presentation.ui.UiText
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsDashboardScreenRoute(
    onBackClick: () -> Unit,
) {
    val viewModel = koinViewModel<AnalyticsDashboardViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    AnalyticsDashboardScreen(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
private fun AnalyticsDashboardScreen(
    state: AnalyticsDashboardState?,
    onBackClick: () -> Unit
) {
    RuniqueScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            RuniqueTopAppBar(
                shouldShowBackButton = true,
                title = stringResource(id = R.string.analytics),
                onBackClick = onBackClick,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { paddingValues ->
        if (state == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            FlowRow(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                maxItemsInEachRow = 2,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.analyticsMetrics.forEach {
                    AnalyticsCard(
                        title = it.title.asString(),
                        value = it.value,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    RuniqueTheme {
        AnalyticsDashboardScreen(
            state = AnalyticsDashboardState(
                persistentListOf(
                    AnalyticsMetric(
                        title = UiText.StringResource(R.string.total_distance_run),
                        value = "0.2 km"
                    ),
                    AnalyticsMetric(
                        title = UiText.StringResource(R.string.total_time_run),
                        value = "0d 0h 0m"
                    ),
                    AnalyticsMetric(
                        title = UiText.StringResource(R.string.fastest_ever_run),
                        value = "143.9 km/h"
                    ),
                    AnalyticsMetric(
                        title = UiText.StringResource(R.string.avg_distance_per_run),
                        value = "0.1 km"
                    ),
                    AnalyticsMetric(
                        title = UiText.StringResource(R.string.avg_pace_per_run),
                        value = "07:10"
                    ),
                )
            ),
            onBackClick = {}
        )
    }
}