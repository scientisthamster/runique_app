package com.scientisthamster.runique

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.scientisthamster.auth.presentation.intro.IntroScreenRoute
import com.scientisthamster.auth.presentation.login.LoginScreenRoute
import com.scientisthamster.auth.presentation.register.RegisterScreenRoute
import com.scientisthamster.run.presentation.active_run.ActiveRunScreenRoute
import com.scientisthamster.run.presentation.active_run.service.ActiveRunService
import com.scientisthamster.run.presentation.run_overview.RunOverviewScreenRoute

@Composable
internal fun RuniqueNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "run" else "auth"
    ) {
        authGraph(navController)
        runGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        composable(route = "intro") {
            IntroScreenRoute(
                onSignInClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("register") }
            )
        }
        composable(route = "register") {
            RegisterScreenRoute(
                onSignInClick = {
                    navController.navigate("login") {
                        popUpTo("register") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = { navController.navigate("login") }
            )
        }
        composable(route = "login") {
            LoginScreenRoute(
                onLoginSuccess = {
                    navController.navigate("run") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSingUpClick = {
                    navController.navigate("register") {
                        popUpTo("login") {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController) {
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable(route = "run_overview") {
            RunOverviewScreenRoute(
                onStartRunClick = { navController.navigate("active_run") },
                onLogoutClick = {
                    navController.navigate("auth") {
                        popUpTo("run") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "active_run",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "runique://active_run"
                }
            )
        ) {
            val context = LocalContext.current

            ActiveRunScreenRoute(
                onBackClick = { navController.navigateUp() },
                onFinishedRun = { navController.navigateUp() },
                onServiceToggle = { shouldServiceRun ->
                    if (shouldServiceRun) {
                        context.startService(
                            ActiveRunService.createStartIntent(
                                context = context,
                                activityClass = MainActivity::class.java
                            )
                        )
                    } else {
                        context.startService(ActiveRunService.createStopIntent(context = context))
                    }
                }
            )
        }
    }
}