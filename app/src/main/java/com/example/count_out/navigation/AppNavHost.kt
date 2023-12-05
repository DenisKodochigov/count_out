package com.example.count_out.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.basket.navigation.navigateToProducts
import com.example.count_out.ui.screens.workouts.WorkoutsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){

    NavHost(
        navController = navController, startDestination = WorkoutsScreen.route, modifier = modifier ) {
        composable(route = WorkoutsScreen.route,
            enterTransition = {
                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
            exitTransition = {
                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }) {
            WorkoutsScreen(
                screen = WorkoutsScreen, onClickWorkout = { navController.navigateToProducts(it) })
        }
        composable(
            route = RoundScreen.routeWithArgs, arguments = RoundScreen.arguments,
            enterTransition = {
                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
            exitTransition = {
                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }
        )
        { navBackStackEntry ->
            val basketId = navBackStackEntry.arguments?.getLong(RoundScreen.workoutIdArg)
            if (basketId != null) {
//                RoundScreen(basketId = basketId, showBottomSheet = showBottomSheet, screen = ProductsBasket)
            }
        }
        composable(
            route = SetScreen.route,
            enterTransition = {
                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
            exitTransition = {
                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }
        ) {
//            SetScreen( showBottomSheet = showBottomSheet, screen = SetScreen )
        }
        composable(
            route = SettingScreen.route,
            enterTransition = {
                targetState.destination.route?.let { enterTransition(WorkoutsScreen.route, it) } },
            exitTransition = {
                targetState.destination.route?.let { exitTransition(WorkoutsScreen.route, it)  } }
        ) {
//            SettingsScreen(refreshScreen = refreshScreen, screen = Setting)
        }
    }
}

fun enterTransition(defaultScreen: String, targetScreen: String): EnterTransition{
    val durationMillis = 800
    val delayMillis = 200
    return if (targetScreen == defaultScreen) {
        slideInHorizontally(
            animationSpec = tween( durationMillis = durationMillis, delayMillis = delayMillis)) { it / -1 } +
                fadeIn( animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
    } else {
        slideInHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)) { it / 1 } +
                fadeIn( animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
    }
}
fun exitTransition(defaultScreen: String, targetScreen: String): ExitTransition {
    val durationMillis = 800
    val delayMillis = 200
    return if (targetScreen == defaultScreen) {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)) { it / 1 } +
                fadeOut(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
    } else {
        slideOutHorizontally(
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)) { it / -1 } +
                fadeOut(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis))
    }
}

