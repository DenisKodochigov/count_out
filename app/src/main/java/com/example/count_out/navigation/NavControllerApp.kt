package com.example.count_out.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateToScreen(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(this@navigateToScreen.graph.findStartDestination().id) { saveState = true }
    }
fun NavHostController.navigateToTraining(trainingId: Long) {
    this.navigateToScreen("${TrainingDestination.route}/$trainingId")
}
fun NavHostController.navigateToActivity(trainingId: Long) {
    this.navigateToScreen("${ActivityDestination.route}/$trainingId")
}

//fun NavHostController.navigateToWorkout(workoutId: Long) {
//    this.navigateToScreen("${WorkoutDestination.route}/$workoutId")
//}
//fun NavHostController.navigateToRound(workoutId: Long) {
//    this.navigateToScreen("${RoundDestination.route}/$workoutId")
//}
//fun NavHostController.navigateToSet(workoutId: Long) {
//    this.navigateToScreen("${SetDestination.route}/$workoutId")
//}


