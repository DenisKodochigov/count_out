package com.example.count_out.navigation

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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.count_out.entity.Const.defaultScreen
import com.example.count_out.entity.Const.delayScreen
import com.example.count_out.entity.Const.durationScreen
import com.example.count_out.ui.screens.play_workout.PlayWorkoutScreen
import com.example.count_out.ui.screens.settings.SettingScreen
import com.example.count_out.ui.screens.training.TrainingScreen
import com.example.count_out.ui.screens.trainings.TrainingsScreen

fun NavGraphBuilder.trainings(
    goToScreenTraining: (Long) -> Unit,
    goToScreenPlayWorkOut: (Long) -> Unit
) {
    template(
        routeTo = TrainingsDestination.route,
        content = {
            TrainingsScreen(
                screen = TrainingsDestination,
                onClickTraining = { goToScreenTraining(it) },
                onStartWorkout = { goToScreenPlayWorkOut(it) },
            )
        }
    )
}

fun NavGraphBuilder.training(onBaskScreen: () -> Unit) {
    template(
        routeTo = TrainingDestination.routeWithArgs,
        argument = TrainingDestination.arguments,
        content = { navBackStackEntry ->
            TrainingScreen(
                trainingId = navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0,
                onBaskScreen = onBaskScreen
            )
        }
    )
}

fun NavGraphBuilder.playWorkout(onBaskScreen: () -> Unit) {
    template(
        routeTo = PlayWorkoutDestination.routeWithArgs,
        argument = TrainingDestination.arguments,
        content = { navBackStackEntry ->
            PlayWorkoutScreen(
                trainingId = navBackStackEntry.arguments?.getLong(TrainingDestination.ARG) ?: 0,
                onBaskScreen = onBaskScreen
            )
        }
    )
}

fun NavGraphBuilder.settings(onBaskScreen: () -> Unit) {
    template(
        routeTo = SettingDestination.route,
        content = { SettingScreen(onBaskScreen = onBaskScreen) }
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
        content = content
    )
}

val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    val targetScreen = targetState.destination.route ?: defaultScreen
    val direction = if (targetScreen == defaultScreen) -1 else 1
        slideInHorizontally(animationSpec =tweenM(), initialOffsetX = { it * direction}) +
            fadeIn( animationSpec = tweenM() )
}
val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    val targetScreen = targetState.destination.route ?: defaultScreen
    val direction = if (targetScreen == defaultScreen) 1 else -1
        slideOutHorizontally(animationSpec = tweenM(), targetOffsetX = { it * direction }) +
        fadeOut(animationSpec = tweenM())
}

fun <T>tweenM(): TweenSpec<T> =
    tween( durationMillis = durationScreen, delayMillis = delayScreen, easing = LinearOutSlowInEasing)

