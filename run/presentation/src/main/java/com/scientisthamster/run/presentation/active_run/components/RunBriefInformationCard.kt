package com.scientisthamster.run.presentation.active_run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.core.presentation.ui.formatted
import com.scientisthamster.core.presentation.ui.toFormattedKm
import com.scientisthamster.core.presentation.ui.toFormattedPace
import com.scientisthamster.run.domain.RunData
import com.scientisthamster.run.presentation.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun RunBriefInformationCard(
    elapsedTime: Duration,
    runData: RunData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RunValueItem(
            title = stringResource(id = R.string.duration),
            value = elapsedTime.formatted(),
            valueFontSize = 32.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RunValueItem(
                title = stringResource(id = R.string.distance),
                value = (runData.distanceMeters / 1000.0).toFormattedKm(),
                modifier = Modifier.defaultMinSize(minWidth = 75.dp)
            )
            RunValueItem(
                title = stringResource(id = R.string.pace),
                value = elapsedTime.toFormattedPace(distanceKm = (runData.distanceMeters / 1000.0)),
                modifier = Modifier.defaultMinSize(minWidth = 75.dp)
            )
        }
    }
}

@Composable
private fun RunValueItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueFontSize: TextUnit = 16.sp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = valueFontSize
        )
    }
}

@Preview
@Composable
private fun RunBriefInformationCardPreview() {
    RuniqueTheme {
        RunBriefInformationCard(
            elapsedTime = 10.minutes,
            runData = RunData(
                distanceMeters = 3425,
                pace = 3.minutes
            )
        )
    }
}