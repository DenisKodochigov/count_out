package com.example.count_out.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.count_out.entity.Const.DEFAULT_SCREEN

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
        it.routeWithArgs == this.currentBackStackEntryAsState().value?.destination?.route } ?: DEFAULT_SCREEN
}


