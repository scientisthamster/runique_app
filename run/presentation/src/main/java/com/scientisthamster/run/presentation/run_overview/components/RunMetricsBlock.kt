@file:OptIn(ExperimentalLayoutApi::class)

package com.scientisthamster.run.presentation.run_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scientisthamster.run.presentation.R
import com.scientisthamster.run.presentation.run_overview.model.RunMetric
import com.scientisthamster.run.presentation.run_overview.model.RunUi
import kotlin.math.max

@Composable
internal fun RunMetricsBlock(
    runUi: RunUi,
    modifier: Modifier = Modifier
) {
    val runMetrics = listOf(
        RunMetric(
            title = stringResource(id = R.string.distance),
            value = runUi.distance
        ),
        RunMetric(
            title = stringResource(id = R.string.pace),
            value = runUi.pace
        ),
        RunMetric(
            title = stringResource(id = R.string.avg_speed),
            value = runUi.avgSpeed
        ),
        RunMetric(
            title = stringResource(id = R.string.max_speed),
            value = runUi.maxSpeed
        ),
        RunMetric(
            title = stringResource(id = R.string.total_elevation),
            value = runUi.totalElevation
        ),
    )

    var maxWidth by remember {
        mutableIntStateOf(0)
    }
    val maxWidthDp = with(LocalDensity.current) { maxWidth.toDp() }
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        runMetrics.forEach { runMetric ->
            RunMetricItem(
                runMetric = runMetric,
                modifier = Modifier
                    .defaultMinSize(minWidth = maxWidthDp)
                    .onSizeChanged {
                        maxWidth = max(maxWidth, it.width)
                    }
            )
        }
    }
}

@Composable
private fun RunMetricItem(
    runMetric: RunMetric,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = runMetric.title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
        )
        Text(
            text = runMetric.value,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}