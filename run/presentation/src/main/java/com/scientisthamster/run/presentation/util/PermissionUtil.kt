package com.scientisthamster.run.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun ComponentActivity.shouldShowNotificationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

private fun Context.isGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isLocationPermissionGranted(): Boolean {
    return isGranted(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun Context.isNotificationPermissionGranted(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        isGranted(Manifest.permission.POST_NOTIFICATIONS)
    } else true
}

fun ActivityResultLauncher<Array<String>>.requestRuniquePermissions(context: Context) {
    val isLocationPermissionGranted = context.isLocationPermissionGranted()
    val isNotificationPermissionGranted = context.isNotificationPermissionGranted()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        isLocationPermissionGranted && isNotificationPermissionGranted -> return
        isLocationPermissionGranted -> launch(notificationPermission)
        isNotificationPermissionGranted -> launch(locationPermissions)
        else -> launch(locationPermissions + notificationPermission)
    }
}