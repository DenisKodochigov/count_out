package com.count_out.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

fun NavHostController.navigateToScreen(route: String) = this.navigate(route) { launchSingleTop = true }
fun NavHostController.navigateToScreenTraining(trainingId: Long) {
    this.navigateToScreen("${TrainingDestination.route}/$trainingId")
}
fun NavHostController.navigateToScreenExecuteWorkout(trainingId: Long) {
    this.navigateToScreen("${ExecuteWorkDestination.route}/$trainingId")
}
@Composable
fun NavHostController.backScreenDestination(): ScreenDestination{
    return listScreens.find{
        it.routeWithArgs == this.currentBackStackEntryAsState().value?.destination?.route } ?: TrainingsDestination
}


