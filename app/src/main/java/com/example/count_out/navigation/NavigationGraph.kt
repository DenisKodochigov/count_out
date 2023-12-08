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
import com.example.count_out.ui.screens.round.RoundScreen
import com.example.count_out.ui.screens.set.SetScreen
import com.example.count_out.ui.screens.settings.SettingScreen
import com.example.count_out.ui.screens.workout.WorkoutScreen
import com.example.count_out.ui.screens.workouts.WorkoutsScreen

fun NavGraphBuilder.workouts(navigateToScreen: (Long)->Unit){
    template(
        route = WorkoutsDestination.route,
        content = {
            WorkoutsScreen(screen = WorkoutsDestination, onClickWorkout = { navigateToScreen( it )}) }
    )
}

fun NavGraphBuilder.workout(navigateToScreen: (Long)->Unit){
    template(
        routeTo = WorkoutsDestination.route,
        route = WorkoutDestination.routeWithArgs,
        argument = WorkoutDestination.arguments,
        content = {navBackStackEntry ->
            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
            if (workoutId != null) {
                WorkoutScreen(
                    workoutId = workoutId,
                    screen = WorkoutDestination,
                    onClickWorkout = { navigateToScreen(it) })
            }
        }
    )
}
fun NavGraphBuilder.round( navigateToScreen: (Long)->Unit ){
    template(
        routeTo = WorkoutDestination.route,
        route = RoundDestination.routeWithArgs,
        argument = RoundDestination.arguments,
        content = {navBackStackEntry ->
            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
            if (workoutId != null) {
                RoundScreen(
                    workoutId = workoutId,
                    screen = WorkoutDestination,
                    onClickWorkout = { navigateToScreen(it) })
            }
        }
    )
}
fun NavGraphBuilder.set( navigateToScreen: (Long)->Unit ){
    template(
        routeTo = RoundDestination.route,
        route = SetDestination.routeWithArgs,
        argument = SetDestination.arguments,
        content = {navBackStackEntry ->
            val workoutId = navBackStackEntry.arguments?.getLong(WorkoutDestination.workoutIdArg)
            if (workoutId != null) {
                SetScreen(
                    workoutId = workoutId,
                    screen = WorkoutDestination,
                    onClickWorkout = { navigateToScreen(it) })
            }
        }
    )
}
fun NavGraphBuilder.settings(){
    template(
        route = SettingDestination.route,
        content = {
            SettingScreen( screen = SettingDestination )
        }
    )
}
fun NavGraphBuilder.template(
    route: String,
    routeTo: String = WorkoutsDestination.route,
    argument: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry)-> Unit
){
    composable(
        route = route,
        arguments = argument,
        enterTransition = {
            targetState.destination.route?.let { enterTransition(routeTo, it) } },
        exitTransition = {
            targetState.destination.route?.let { exitTransition(routeTo, it)  } },
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

//        composable(route = WorkoutsScreen.route,
//            enterTransition = {
//                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
//            exitTransition = {
//                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }) {
//            WorkoutsScreen(
//                screen = WorkoutsScreen, onClickWorkout = { navController.navigateToProducts(it) })
//        }
//        composable(
//            route = RoundScreen.routeWithArgs, arguments = RoundScreen.arguments,
//            enterTransition = {
//                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
//            exitTransition = {
//                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }
//        )
//        { navBackStackEntry ->
//            val basketId = navBackStackEntry.arguments?.getLong(RoundScreen.workoutIdArg)
//            if (basketId != null) {
////                RoundScreen(basketId = basketId, showBottomSheet = showBottomSheet, screen = ProductsBasket)
//            }
//        }
//        composable(
//            route = SetScreen.route,
//            enterTransition = {
//                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
//            exitTransition = {
//                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }
//        ) {
////            SetScreen( showBottomSheet = showBottomSheet, screen = SetScreen )
//        }