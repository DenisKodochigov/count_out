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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.count_out.app.presentation.Const.DEFAULT_SCREEN
import com.count_out.app.presentation.Const.DELAY_SCREEN
import com.count_out.app.presentation.Const.DURATION_SCREEN
import com.count_out.presentation.screens.execute.ExecuteViewModel
import com.count_out.presentation.screens.history.HistoryViewModel
import com.count_out.presentation.screens.plans.model.PlansViewModel
import com.count_out.presentation.screens.settings.SettingViewModel

fun NavGraphBuilder.executeWorkout( navigateEvent: NavigateEventImpl) {
    template(
        routeTo = ExecuteDestination.route,
        content = {navBackStackEntry ->
            ExecuteDestination.Show( navigateEvent,
                hiltViewModel(navBackStackEntry) as ExecuteViewModel)
        }
    )
}
fun NavGraphBuilder.plans( navigateEvent: NavigateEventImpl ) {
    template(
        routeTo = PlansDestination.route,
        content = {navBackStackEntry ->
            PlansDestination.Show(navigateEvent = navigateEvent,
                vm = hiltViewModel(navBackStackEntry) as PlansViewModel)
        }
    )
}
//fun NavGraphBuilder.plan( navigateEvent: NavigateEventImpl) {
//    template(
//        routeTo = PlanDestination.routeWithArgs,
//        argument = PlanDestination.arguments,
//        content = {navBackStackEntry ->
//            PlanDestination.Show(navigateEvent = navigateEvent,
//                vm = hiltViewModel(navBackStackEntry) as PlanViewModel)
//        }
//    )
//}
fun NavGraphBuilder.history( navigateEvent: NavigateEventImpl) {
    template(
        routeTo = HistoryDestination.route,
        content = {navBackStackEntry ->
            HistoryDestination.Show(navigateEvent = navigateEvent,
                vm = hiltViewModel(navBackStackEntry) as HistoryViewModel) }
    )
}
fun NavGraphBuilder.settings( navigateEvent: NavigateEventImpl) {
    template(
        routeTo = SettingDestination.route,
        content = {navBackStackEntry ->
            SettingDestination.Show(navigateEvent = navigateEvent,
                vm = hiltViewModel(navBackStackEntry) as SettingViewModel)}
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
    val targetScreen = targetState.destination.route ?: DEFAULT_SCREEN.route
    val direction: Double = if (targetScreen == DEFAULT_SCREEN.route) -1.0 else 1.0
    slideInHorizontally(animationSpec =tweenM(), initialOffsetX = { (it * direction).toInt() }) +
        fadeIn( animationSpec = tweenM() )
}
val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    val targetScreen = targetState.destination.route ?: DEFAULT_SCREEN.route
    val direction: Double = if (targetScreen == DEFAULT_SCREEN.route) 1.0 else -1.0
    slideOutHorizontally(animationSpec = tweenM(), targetOffsetX = { (it * direction).toInt() }) +
    fadeOut(animationSpec = tweenM())
}
//val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
//    val targetScreen = targetState.destination.route ?: DEFAULT_SCREEN.route
//    val direction: Double = if (targetScreen == DEFAULT_SCREEN.route) (1/3.0) else (1/3.0)
//    slideInHorizontally(initialOffsetX = { (it * direction).toInt() }, animationSpec = tweenM())
//}
//val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
//    val targetScreen = targetState.destination.route ?: DEFAULT_SCREEN.route
//    val direction: Double = if (targetScreen == DEFAULT_SCREEN.route) (1/3.0) else (1/3.0)
//    slideOutHorizontally(targetOffsetX = { (it * direction).toInt() }, animationSpec = tweenM())
//}
fun <T>tweenM(): TweenSpec<T> =
    tween( durationMillis = DURATION_SCREEN, delayMillis = DELAY_SCREEN, easing = LinearOutSlowInEasing)

