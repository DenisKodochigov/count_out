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
import com.count_out.presentation.screens.executor.ExecuteWorkViewModel
import com.count_out.presentation.screens.history.HistoryScreen
import com.count_out.presentation.screens.executor.ExecuteWorkoutScreen
import com.count_out.presentation.screens.trainings.TrainingsScreen
import com.count_out.presentation.screens.training.TrainingScreen
import com.count_out.presentation.screens.history.HistoryViewModel
import com.count_out.presentation.screens.settings.SettingViewModel
import com.count_out.presentation.screens.settings.SettingScreen
import com.count_out.presentation.screens.training.TrainingViewModel
import com.count_out.presentation.screens.trainings.TrainingsViewModel

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
    @Composable fun Show (vm: ViewModel, arg: List<String>)

}
/*** App app navigation destinations*/
object TrainingsDestination : ScreenDestination {
    override val route = "trainings"
    override val routeWithArgs = route
    override val nameScreen = R.string.plans_workout
    override val icon = Icons.Filled.AccessAlarms
    override val iconText = R.string.trainings_
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = R.string.training
    override var onClickFAB: () -> Unit = {}
    @Composable
    override fun Show (vm: ViewModel, arg: List<String>) { TrainingsScreen(vm as TrainingsViewModel)}
}
object TrainingDestination : ScreenDestination {
    override val route = "training"
    override val nameScreen = R.string.plan_workout
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.trainings_
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.trainings
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(vm: ViewModel, arg: List<String>) {
        TrainingScreen(vm as TrainingViewModel, arg[0].toLong())
    }

    const val ARG = "arg_training"
    override val routeWithArgs = "${route}/{$ARG}"
    val arguments = listOf(navArgument(ARG) { type = NavType.LongType })
}
object ExecuteWorkDestination : ScreenDestination {
    override val route = "executeWorkout"
    override val nameScreen = R.string.screen_execute_work
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.trainings_
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.trainings
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(vm: ViewModel, arg: List<String>) {
        ExecuteWorkoutScreen(vm as ExecuteWorkViewModel, arg[0].toLong()) }

    const val ARG = "arg_training"
    override val routeWithArgs = "${route}/{$ARG}"
    val arguments = listOf(navArgument(ARG) { type = NavType.LongType })
}
object HistoryDestination : ScreenDestination {
    override val route = "history"
    override val routeWithArgs = route
    override val nameScreen = R.string.history
    override val icon = Icons.Filled.CalendarMonth
    override val iconText = R.string.history_
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = R.string.history_
    override var onClickFAB: () -> Unit = {}

    @Composable override fun Show(vm: ViewModel, arg: List<String>) {
        HistoryScreen(vm as HistoryViewModel) }
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
    override fun Show(vm: ViewModel, arg: List<String>) { SettingScreen(vm as SettingViewModel) }
}
val navBottomScreens = listOf(TrainingsDestination, HistoryDestination, SettingDestination)
val listScreens = listOf(
        TrainingsDestination,
        TrainingDestination,
        ExecuteWorkDestination,
        HistoryDestination,
        SettingDestination,
    )



