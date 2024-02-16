package com.example.count_out.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToScreen(route: String) = this.navigate(route) { launchSingleTop = true }
fun NavHostController.navigateToTraining(trainingId: Long) {
    this.navigateToScreen("${TrainingDestination.route}/$trainingId")
}
fun NavHostController.navigateToPlayWorkout( trainingId: Long) {
    this.navigateToScreen("${PlayWorkoutDestination.route}/$trainingId")
}



