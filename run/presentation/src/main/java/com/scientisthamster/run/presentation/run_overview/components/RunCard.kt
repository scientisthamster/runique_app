@file:OptIn(ExperimentalFoundationApi::class)

package com.scientisthamster.run.presentation.run_overview.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scientisthamster.core.domain.location.Location
import com.scientisthamster.core.domain.run.Run
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import com.scientisthamster.run.presentation.R
import com.scientisthamster.run.presentation.run_overview.mapper.toRunUi
import com.scientisthamster.run.presentation.run_overview.model.RunUi
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun RunCard(
    runUi: RunUi,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var shouldShowDropDownMenu by rememberSaveable {
        mutableStateOf(false)
    }

    Box {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.surface)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        shouldShowDropDownMenu = true
                    }
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RunMapImage(imageUrl = runUi.mapPictureUrl)
            RunDurationBlock(
                duration = runUi.duration,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
            )
            RunDateBlock(
                dateTime = runUi.dateTime,
                modifier = Modifier.fillMaxWidth()
            )
            RunMetricsBlock(
                runUi = runUi,
                modifier = Modifier.fillMaxWidth()
            )
        }

        DropdownMenu(
            expanded = shouldShowDropDownMenu,
            onDismissRequest = { shouldShowDropDownMenu = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.delete))
                },
                onClick = {
                    shouldShowDropDownMenu = false
                    onDeleteClick()
                }
            )
        }
    }
}

@Preview
@Composable
private fun RunCardPreview() {
    RuniqueTheme {
        RunCard(
            runUi = Run(
                id = "123",
                duration = 10.minutes + 30.seconds,
                dateTimeUtc = ZonedDateTime.now(),
                distanceMeters = 2543,
                location = Location(0.0, 0.0),
                maxSpeedKmh = 15.6234,
                totalElevationMeters = 123,
                mapPictureUrl = null
            ).toRunUi(),
            onDeleteClick = {}
        )
    }
}