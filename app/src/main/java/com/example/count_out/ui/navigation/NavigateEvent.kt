package com.example.count_out.ui.navigation

import androidx.navigation.NavHostController


class NavigateEvent (private val navController: NavHostController) {
    fun goToScreenTraining(id: Long) { navController.navigateToScreenTraining(id) }
    fun goToScreenExecuteWorkout(id: Long) {navController.navigateToScreenExecuteWorkout(id) }
    fun backStack() { navController.popBackStack() }
}
