package com.count_out.app.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.count_out.app.presentation.Const.DELAY_SCREEN
import com.count_out.app.presentation.Const.DURATION_SCREEN
import com.count_out.presentation.screens.executor.ExecuteWorkViewModel
import com.count_out.presentation.screens.history.HistoryViewModel
import com.count_out.presentation.screens.settings.SettingViewModel
import com.count_out.presentation.screens.training.TrainingViewModel
import com.count_out.presentation.screens.trainings.TrainingsViewModel

fun NavGraphBuilder.trainings( navigateEvent: NavigateEventImpl,
) {
    template(
        routeTo = TrainingsDestination.route,
        content = {
            val vm: TrainingsViewModel = hiltViewModel()
            vm.initNavigate(navigateEvent)
            TrainingsDestination.Show(vm, emptyList())
        }
    )
}
fun NavGraphBuilder.training( navigateEvent: NavigateEventImpl) {
    template(
        routeTo = TrainingDestination.routeWithArgs,
        argument = TrainingDestination.arguments,
        content = { navBackStackEntry ->
            val vm: TrainingViewModel = hiltViewModel()
            vm.initNavigate(navigateEvent)
            val arg = listOf((navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0).toString())
            TrainingDestination.Show(vm, arg)
//            TrainingScreen(
//                navigateEvent = navigateEvent,
//                trainingId = navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0,
//            )
        }
    )
}
fun NavGraphBuilder.executeWorkout(navigateEvent: NavigateEventImpl) {
    template(
        routeTo = ExecuteWorkDestination.routeWithArgs,
        argument = TrainingDestination.arguments,
        content = { navBackStackEntry ->
            val vm: ExecuteWorkViewModel = hiltViewModel()
//            vm.initNavigate(navigateEvent)
            val arg = listOf((navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0).toString())
            ExecuteWorkDestination.Show(vm, arg)
//            ExecuteWorkoutScreen(
//                navigateEvent = navigateEvent,
//                trainingId = navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0)
        }
    )
}
fun NavGraphBuilder.history(navigateEvent: NavigateEventImpl) {
    template(
        routeTo = HistoryDestination.route,
        content = {
            val vm: HistoryViewModel = hiltViewModel()
//            vm.initNavigate(navigateEvent)
            HistoryDestination.Show(vm, emptyList()) }
    )
}
fun NavGraphBuilder.settings(navigateEvent: NavigateEventImpl) {
    template(
        routeTo = SettingDestination.route,
        content = {
            val vm: SettingViewModel = hiltViewModel()
            vm.initNavigate(navigateEvent)
            SettingDestination.Show(vm, emptyList())}
    )
}

fun NavGraphBuilder.template(
    routeTo: String,
    argument: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = routeTo,
        arguments = argument,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
//        popEnterTransition = popEnterTransition,
//        popExitTransition = popExitTransition,
        content = content
    )
}

val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    val targetScreen = targetState.destination.route ?: TrainingsDestination.route
    val direction: Double = if (targetScreen == TrainingsDestination.route) -1.0 else 1.0
    slideInHorizontally(animationSpec =tweenM(), initialOffsetX = { (it * direction).toInt() }) +
        fadeIn( animationSpec = tweenM() )
}
val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    val targetScreen = targetState.destination.route ?: TrainingsDestination.route
    val direction: Double = if (targetScreen == TrainingsDestination.route) 1.0 else -1.0
    slideOutHorizontally(animationSpec = tweenM(), targetOffsetX = { (it * direction).toInt() }) +
    fadeOut(animationSpec = tweenM())
}
//val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
//    val targetScreen = targetState.destination.route ?: TrainingsDestination.route
//    val direction: Double = if (targetScreen == TrainingsDestination.route) (1/3.0) else (1/3.0)
//    slideInHorizontally(initialOffsetX = { (it * direction).toInt() }, animationSpec = tweenM())
//}
//val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
//    val targetScreen = targetState.destination.route ?: TrainingsDestination.route
//    val direction: Double = if (targetScreen == TrainingsDestination.route) (1/3.0) else (1/3.0)
//    slideOutHorizontally(targetOffsetX = { (it * direction).toInt() }, animationSpec = tweenM())
//}
fun <T>tweenM(): TweenSpec<T> =
    tween( durationMillis = DURATION_SCREEN, delayMillis = DELAY_SCREEN, easing = LinearOutSlowInEasing)

