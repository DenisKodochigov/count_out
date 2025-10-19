package com.count_out.app.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.count_out.app.R
import com.count_out.domain.entity.NavigateEvent
import com.count_out.presentation.screens.execute.ExecuteViewModel
import com.count_out.presentation.screens.execute.ExecuteWorkoutScreen
import com.count_out.presentation.screens.history.HistoryScreen
import com.count_out.presentation.screens.history.HistoryViewModel
import com.count_out.presentation.screens.plan.PlanScreen
import com.count_out.presentation.screens.plan.PlanViewModel
import com.count_out.presentation.screens.plans.PlansScreen
import com.count_out.presentation.screens.plans.PlansViewModel
import com.count_out.presentation.screens.settings.SettingScreen
import com.count_out.presentation.screens.settings.SettingViewModel

/*** Contract for information needed on every App navigation destination*/
interface ScreenDestination {
    val route: String
    val routeWithArgs: String
    val nameScreen: Int
    val icon: ImageVector
    val iconText: Int
    val pictureDay: Int
    val pictureNight: Int
    val showFab: Boolean
    var textFABId: Int
    var onClickFAB: () -> Unit
    @Composable fun Show( navigateEvent: NavigateEvent, vm: ViewModel)
}
/*** App app navigation destinations*/
object ExecuteDestination : ScreenDestination {
    override val route = "executeWorkout"
    override val routeWithArgs = route
    override val nameScreen = R.string.screen_execute
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.screen_execute
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.screen_execute
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(navigateEvent: NavigateEvent, vm: ViewModel) {
        ExecuteWorkoutScreen(vm as ExecuteViewModel, navigateEvent)
    }
}
object PlansDestination : ScreenDestination {
    override val route = "plans"
    override val routeWithArgs = route
    override val nameScreen = R.string.plans_workout
    override val icon = Icons.Filled.AccessAlarms
    override val iconText = R.string.plans
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = R.string.plans
    override var onClickFAB: () -> Unit = {}
    @Composable
    override fun Show (navigateEvent: NavigateEvent, vm: ViewModel) {
        PlansScreen(vm as PlansViewModel, navigateEvent)
    }
}
object PlanDestination : ScreenDestination {
    override val route = "training"
    override val nameScreen = R.string.plan_workout
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.training
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.training
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(navigateEvent: NavigateEvent, vm: ViewModel) {
        PlanScreen(vm as PlanViewModel, navigateEvent)
    }

    const val ARG = "arg1"
    override val routeWithArgs = "${route}/{$ARG}"
    val arguments = listOf(navArgument(ARG) { type = NavType.LongType })
}
object HistoryDestination : ScreenDestination {
    override val route = "history"
    override val routeWithArgs = route
    override val nameScreen = R.string.history
    override val icon = Icons.Filled.CalendarMonth
    override val iconText = R.string.history
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = R.string.history
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(navigateEvent: NavigateEvent, vm: ViewModel) {
        HistoryScreen(vm as HistoryViewModel, navigateEvent) }
}
object SettingDestination : ScreenDestination {
    override val route = "settings"
    override val routeWithArgs = route
    override val nameScreen = R.string.setting
    override val icon = Icons.Filled.Settings
    override val iconText = R.string.setting
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = 0
    override var onClickFAB: () -> Unit = {}

    @Composable
    override fun Show(navigateEvent: NavigateEvent, vm: ViewModel) {
        SettingScreen(vm as SettingViewModel)
    }
}
val navBottomScreens =
    listOf(ExecuteDestination, PlansDestination, HistoryDestination, SettingDestination)
val listScreens = listOf(
        PlansDestination,
        PlanDestination,
        ExecuteDestination,
        HistoryDestination,
        SettingDestination,
    )



