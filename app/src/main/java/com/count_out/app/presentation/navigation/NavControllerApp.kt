package com.count_out.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.count_out.app.presentation.Const.DEFAULT_SCREEN


fun NavHostController.navigateToScreenExecuteWorkout() {
    this.navigateToScreen(ExecuteDestination.route)
}
fun NavHostController.navigateToScreenPlans() {
    this.navigateToScreen(PlansDestination.route)
}
fun NavHostController.navigateToScreenPlan(planId: Long) {
    this.navigateToScreen("${PlanDestination.route}/$planId")
}

fun NavHostController.navigateToScreen(route: String) = this.navigate(route) { launchSingleTop = true }

@Composable
fun NavHostController.backScreenDestination(): ScreenDestination{
    return listScreens.find{
        it.routeWithArgs == this.currentBackStackEntryAsState().value?.destination?.route } ?: DEFAULT_SCREEN
}


