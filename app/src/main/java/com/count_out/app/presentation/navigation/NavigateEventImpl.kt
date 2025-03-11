package com.count_out.app.presentation.navigation

import androidx.navigation.NavHostController
import com.count_out.domain.entity.NavigateEvent

class NavigateEventImpl (private val navController: NavHostController): NavigateEvent {
    override fun goToScreenTraining(id: Long) { navController.navigateToScreenTraining(id) }
    override fun goToScreenExecuteWorkout(id: Long) {navController.navigateToScreenExecuteWorkout(id) }
    override fun backStack() { navController.popBackStack() }
    override fun backStackEntry(route: String) = navController.getBackStackEntry(route)
    override fun getNavController() = navController
}