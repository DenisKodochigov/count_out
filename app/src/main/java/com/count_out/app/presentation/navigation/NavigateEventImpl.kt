package com.count_out.app.presentation.navigation

import androidx.navigation.NavHostController
import com.count_out.domain.entity.NavigateEvent

class NavigateEventImpl (private val navController: NavHostController): NavigateEvent {
    override fun goToScreenPlan(id: Long) { navController.navigateToScreenTraining(id) }
    override fun goToScreenPlans() { navController.navigateToScreenPlans() }
    override fun goToScreenExecuteWorkout() {navController.navigateToScreenExecuteWorkout() }
    override fun backStack() { navController.popBackStack() }
    override fun backStackEntry(route: String) = navController.getBackStackEntry(route)
    override fun getNavController() = navController
}