package com.scientisthamster.analytics_feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.splitcompat.SplitCompat
import com.scientisthamster.analytics.data.di.analyticsDataModule
import com.scientisthamster.analytics.presentation.AnalyticsDashboardScreenRoute
import com.scientisthamster.analytics.presentation.di.analyticsPresentationModule
import com.scientisthamster.core.presentation.designsystem.RuniqueTheme
import org.koin.core.context.loadKoinModules

class AnalyticsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(listOf(analyticsDataModule, analyticsPresentationModule))
        SplitCompat.installActivity(this)

        setContent {
            RuniqueTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = "analytics_dashboard"
                ) {
                    composable(route = "analytics_dashboard") {
                        AnalyticsDashboardScreenRoute(onBackClick = { finish() })
                    }
                }
            }
        }
    }
}