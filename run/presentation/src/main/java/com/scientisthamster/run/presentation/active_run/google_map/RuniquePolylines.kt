package com.scientisthamster.run.presentation.active_run.google_map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import com.scientisthamster.core.domain.location.LocationTimestamp

@Composable
internal fun RuniquePolylines(locations: List<List<LocationTimestamp>>) {
    val polylines = remember(locations) {
        locations.map {
            it.zipWithNext { location1, location2 ->
                PolylineUi(
                    location1 = location1.location.location,
                    location2 = location2.location.location,
                    color = PolylineColorCalculator.calculatePolylineColor(
                        location1 = location1,
                        location2 = location2
                    )
                )
            }
        }
    }

    polylines.onEach { polyline ->
        polyline.onEach { polylineUi ->
            Polyline(
                points = listOf(
                    LatLng(polylineUi.location1.latitude, polylineUi.location1.longitude),
                    LatLng(polylineUi.location2.latitude, polylineUi.location2.longitude)
                ),
                color = polylineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
}