package com.scientisthamster.run.presentation.active_run.google_map

import androidx.compose.ui.graphics.Color
import com.scientisthamster.core.domain.location.Location

internal data class PolylineUi(
    val location1: Location,
    val location2: Location,
    val color: Color
)