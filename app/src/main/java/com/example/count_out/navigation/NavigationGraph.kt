package com.example.count_out.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.count_out.ui.screens.exercise.ExerciseScreen
import com.example.count_out.ui.screens.settings.SettingScreen
import com.example.count_out.ui.screens.training.TrainingScreen
import com.example.count_out.ui.screens.trainings.TrainingsScreen
fun NavGraphBuilder.trainings( goToScreen: (Long)->Unit ){
    template(
        routeTo = TrainingsDestination.route,
        content = {
            TrainingsScreen(
                screen = TrainingsDestination,
                onClickTraining = { goToScreen( it )},) }
    )
}
fun NavGraphBuilder.training( goToScreen: (Long, Long)-> Unit, onBaskScreen: ()->Unit ){
    template(
        routeFrom = TrainingsDestination.route,
        routeTo = TrainingDestination.routeWithArgs,
        argument = TrainingDestination.arguments,
        content = {navBackStackEntry ->
            TrainingScreen(
                trainingId = navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0,
                onBaskScreen = onBaskScreen,
                onClickExercise = { id1, id2 -> goToScreen(id1, id2) })
        }
    )
}
fun NavGraphBuilder.exercise(  ){
    template(
        routeFrom = TrainingDestination.route,
        routeTo = ExerciseDestination.routeWithArgs,
        argument = ExerciseDestination.arguments,
        content = {navBackStackEntry ->
            ExerciseScreen(
                roundId = navBackStackEntry.arguments?.getLong(ExerciseDestination.ARG1) ?: 0,
                exerciseId = navBackStackEntry.arguments?.getLong(ExerciseDestination.ARG2) ?: 0,
            )
        }
    )
}

fun NavGraphBuilder.settings(){
    template(
        routeTo = SettingDestination.route,
        content = { SettingScreen( screen = SettingDestination ) }
    )
}
fun NavGraphBuilder.template(
    routeTo: String,
    routeFrom: String = "",
    argument: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry)-> Unit
){
    composable(
        route = routeTo,
        arguments = argument,
        enterTransition = {
            targetState.destination.route?.let { enterTransition(routeFrom, it) } },
        exitTransition = {
            targetState.destination.route?.let { exitTransition(routeFrom, it)  } },
        content = content
    )
}
fun enterTransition(defaultScreen: String, targetScreen: String): EnterTransition {
    val durationMillis = 800
    val delayMillis = 200
    return if (targetScreen == defaultScreen) {
        slideInHorizontally(
            animationSpec = tween( durationMillis = durationMillis, delayMillis = delayMillis)
        ) { it / -1 } + fadeIn( animationSpec =
                tween(durationMillis = durationMillis, delayMillis = delayMillis))
    } else {
        slideInHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
        ) { it / 1 } + fadeIn( animationSpec =
                tween(durationMillis = durationMillis, delayMillis = delayMillis))
    }
}
fun exitTransition(defaultScreen: String, targetScreen: String): ExitTransition {
    val durationMillis = 800
    val delayMillis = 200
    return if (targetScreen == defaultScreen) {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
        ) { it / 1 } +
                fadeOut(animationSpec =
                            tween(durationMillis = durationMillis, delayMillis = delayMillis))
    } else {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
        ) { it / -1 } +
                fadeOut(animationSpec =
                            tween(durationMillis = durationMillis, delayMillis = delayMillis))
    }
}
//fun NavGraphBuilder.workouts(navigateToScreen: (Long)->Unit){
//    template(
//        route = WorkoutsDestination.route,
//        content = {
//            WorkoutsScreen(screen = WorkoutsDestination, onClickWorkout = { navigateToScreen( it )}) }
//    )
//}
//
//fun NavGraphBuilder.workout(navigateToScreen: (Long)->Unit){
//    template(
//        routeTo = WorkoutsDestination.route,
//        route = WorkoutDestination.routeWithArgs,
//        argument = WorkoutDestination.arguments,
//        content = {navBackStackEntry ->
//            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
//            if (workoutId != null) {
//                WorkoutScreen(
//                    workoutId = workoutId,
//                    screen = WorkoutDestination,
//                    onClickWorkout = { navigateToScreen(it) })
//            }
//        }
//    )
//}
//fun NavGraphBuilder.round( navigateToScreen: (Long)->Unit ){
//    template(
//        routeTo = WorkoutDestination.route,
//        route = RoundDestination.routeWithArgs,
//        argument = RoundDestination.arguments,
//        content = {navBackStackEntry ->
//            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
//            if (workoutId != null) {
//                RoundScreen(
//                    workoutId = workoutId,
//                    screen = WorkoutDestination,
//                    onClickWorkout = { navigateToScreen(it) })
//            }
//        }
//    )
//}
//fun NavGraphBuilder.set( navigateToScreen: (Long)->Unit ){
//    template(
//        routeTo = RoundDestination.route,
//        route = SetDestination.routeWithArgs,
//        argument = SetDestination.arguments,
//        content = {navBackStackEntry ->
//            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
//            if (workoutId != null) {
//                SetScreen(
//                    workoutId = workoutId,
//                    screen = WorkoutDestination,
//                    onClickWorkout = { navigateToScreen(it) })
//            }
//        }
//    )
//}