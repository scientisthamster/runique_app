@file:OptIn(MapsComposeExperimentalApi::class)

package com.scientisthamster.run.presentation.active_run.google_map

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.ktx.awaitSnapshot
import com.scientisthamster.core.domain.location.Location
import com.scientisthamster.core.domain.location.LocationTimestamp
import com.scientisthamster.core.presentation.designsystem.RunIcon
import com.scientisthamster.run.presentation.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun RuniqueMap(
    isRunFinished: Boolean,
    currentLocation: Location?,
    locations: List<List<LocationTimestamp>>,
    onSnapshot: (Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapStyle = remember { MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style) }
    val cameraPositionState = rememberCameraPositionState()

    val markerState = rememberMarkerState()
    val markerPositionLatitude by animateFloatAsState(
        targetValue = currentLocation?.latitude?.toFloat() ?: .0f,
        animationSpec = tween(delayMillis = 500),
        label = "markerPositionLatitude"
    )
    val markerPositionLongitude by animateFloatAsState(
        targetValue = currentLocation?.longitude?.toFloat() ?: .0f,
        animationSpec = tween(delayMillis = 500),
        label = "markerPositionLongitude"
    )
    val markerPosition = remember(markerPositionLatitude, markerPositionLongitude) {
        LatLng(markerPositionLatitude.toDouble(), markerPositionLongitude.toDouble())
    }

    LaunchedEffect(markerPosition, isRunFinished) {
        if (!isRunFinished) {
            markerState.position = markerPosition
        }
    }

    LaunchedEffect(currentLocation, isRunFinished) {
        if (!isRunFinished && currentLocation != null) {
            val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
    }

    var shouldTakeSnapshot by remember { mutableStateOf(false) }
    var takeSnapshotJob: Job? = remember { null }
    val googleMapModifier = if (isRunFinished) {
        modifier
            .width(300.dp)
            .aspectRatio(16 / 9f)
            .alpha(0f)
            .onSizeChanged {
                if (it.width >= 300) {
                    shouldTakeSnapshot = true
                }
            }
    } else modifier

    GoogleMap(
        modifier = googleMapModifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(mapStyleOptions = mapStyle),
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        RuniquePolylines(locations)

        MapEffect(isRunFinished, shouldTakeSnapshot) { map ->
            if (isRunFinished && shouldTakeSnapshot && takeSnapshotJob == null) {
                shouldTakeSnapshot = false

                val boundsBuilder = LatLngBounds.builder()
                locations.flatten().forEach { locationTimestamp ->
                    boundsBuilder.include(
                        LatLng(
                            locationTimestamp.location.location.latitude,
                            locationTimestamp.location.location.longitude
                        )
                    )
                }
                map.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100)
                )
                map.setOnCameraIdleListener {
                    takeSnapshotJob?.cancel()
                    takeSnapshotJob = GlobalScope.launch {
                        // Make sure the map is sharp and focused before taking
                        // the screenshot
                        delay(500L)
                        map.awaitSnapshot()?.also(onSnapshot)
                    }
                }
            }
        }

        if (!isRunFinished && currentLocation != null) {
            MarkerComposable(
                currentLocation,
                state = markerState
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = RunIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}