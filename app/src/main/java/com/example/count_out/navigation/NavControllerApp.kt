package com.example.count_out.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToScreen(route: String) = this.navigate(route) { launchSingleTop = true }
fun NavHostController.navigateToTraining(trainingId: Long) {
    this.navigateToScreen("${TrainingDestination.route}/$trainingId")
}
fun NavHostController.navigateToPlayWorkout( trainingId: Long) {
    this.navigateToScreen("${PlayWorkoutDestination.route}/$trainingId")
}
//fun NavHostController.navigateToExercise(roundId: Long, exerciseId: Long) {
//    this.navigateToScreen("${ExerciseDestination.route}/$roundId/$exerciseId")
//}


//fun NavHostController.navigateToRound(workoutId: Long) {
//    this.navigateToScreen("${RoundDestination.route}/$workoutId")
//}
//fun NavHostController.navigateToSet(workoutId: Long) {
//    this.navigateToScreen("${SetDestination.route}/$workoutId")
//}


