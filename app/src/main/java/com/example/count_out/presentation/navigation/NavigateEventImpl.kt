package com.example.count_out.presentation.navigation

import androidx.navigation.NavHostController

class NavigateEventImpl (private val navController: NavHostController): NavigateEvent {
    override fun goToScreenTraining(id: Long) { navController.navigateToScreenTraining(id) }
    override fun goToScreenExecuteWorkout(id: Long) {navController.navigateToScreenExecuteWorkout(id) }
    override fun backStack() { navController.popBackStack() }
}