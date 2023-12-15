package com.example.count_out.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.count_out.R

/*** Contract for information needed on every App navigation destination*/
interface ScreenDestination {
    val route: String
    val nameScreen: Int
    val icon: ImageVector
    val iconText: Int
    val pictureDay: Int
    val pictureNight: Int
    val showFab: Boolean
    var textFABId: Int
    var onClickFAB: () -> Unit
}

/*** App app navigation destinations*/
object TrainingsDestination : ScreenDestination {
    override val route = "trainings"
    override val nameScreen = R.string.plans_workout
    override val icon = Icons.Filled.AccessAlarms
    override val iconText = R.string.trainings_
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = true
    override var textFABId = R.string.training
    override var onClickFAB: () -> Unit = {}
}
object TrainingDestination : ScreenDestination {
    override val route = "training"
    override val nameScreen = R.string.plans_workout
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.trainings_
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.trainings
    override var onClickFAB: () -> Unit = {}

    const val ARG = "training_id"
    val routeWithArgs = "${route}/{$ARG}"
    val arguments = listOf(navArgument(ARG) { type = NavType.LongType })
}
object ActivityDestination : ScreenDestination {
    override val route = "action"
    override val nameScreen = R.string.plans_workout
    override val icon = Icons.Filled.Brightness5
    override val iconText = R.string.activity
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val showFab: Boolean = false
    override var textFABId = R.string.activitiyes1
    override var onClickFAB: () -> Unit = {}

    const val ARG = "training_id"
    val routeWithArgs = "${route}/{$ARG}"
    val arguments = listOf(navArgument(ARG) { type = NavType.LongType })
}
//
//object WorkoutDestination : ScreenDestination {
//    override val route = "training"
//    override val nameScreen = R.string.plans_workout
//    override val icon = Icons.Filled.Brightness5
//    override val iconText: Int = R.string.trainings_
//    override val pictureDay = R.drawable.ic_launcher_background
//    override val pictureNight = R.drawable.ic_launcher_background
//    override var textFABId: String = ""
//    override var onClickFAB: () -> Unit = {}
//
//    const val trainingIdArg = "training_id"
//    val routeWithArgs = "${TrainingsDestination.route}/{$trainingIdArg}"
//    val arguments = listOf(navArgument(trainingIdArg) { type = NavType.LongType })
//}
//
//object RoundDestination : ScreenDestination {
//    override val route = "training"
//    override val nameScreen: Int = R.string.plans_workout
//    override val icon = Icons.Filled.Brightness5
//    override val iconText: Int = R.string.trainings_
//    override val pictureDay = R.drawable.ic_launcher_background
//    override val pictureNight = R.drawable.ic_launcher_background
//    override var textFABId: String = ""
//    override var onClickFAB: () -> Unit = {}
//
//    const val trainingIdArg = "training_id"
//    val routeWithArgs = "${TrainingsDestination.route}/{$trainingIdArg}"
//    val arguments = listOf(navArgument(trainingIdArg) { type = NavType.LongType })
//}
//
//object SetDestination : ScreenDestination {
//    override val icon = Icons.Filled.Dashboard
//    override val iconText: Int = ""
//    override val nameInt: Int = R.string.History
//    override val route = "set"
//    override val pictureDay = R.drawable.ic_launcher_background
//    override val pictureNight = R.drawable.ic_launcher_background
//    override val textHeader = R.string.app_name
//    override var textFABId: String = ""
//    override var onClickFAB: () -> Unit = {}
//    const val setsIdArg = "set_type"
//    val routeWithArgs = "${route}/{$setsIdArg}"
//    val arguments = listOf(navArgument(setsIdArg) { type = NavType.LongType })
//}
//
object SettingDestination : ScreenDestination {
    override val route = "settings"
    override val nameScreen = R.string.setting
    override val icon = Icons.Filled.Settings
    override val iconText = R.string.setting
    override val pictureDay = 0
    override val pictureNight = 0
    override val showFab: Boolean = false
    override var textFABId = 0
    override var onClickFAB: () -> Unit = {}
}

//object SingleAccount : ScreenDestination {
//    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
//    // part of the AppTabRow selection
//    override val icon = Icons.Filled.Money
//    override val iconText: String = ""
//    override val route = "single_account"
//    const val accountTypeArg = "account_type"
//    val routeWithArgs = "$route/{$accountTypeArg}"
//    val arguments = listOf(
//        navArgument(accountTypeArg) { type = NavType.StringType })
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "App://$route/{$accountTypeArg}"})
//}
//object Accounts : ScreenDestination {
//    override val icon = Icons.Filled.Dashboard
//    override val route = "products"
//}
// Screens to be displayed in the top AppTabRow
val navBottomScreens = listOf(TrainingsDestination,SettingDestination)
val listScreens = listOf(TrainingsDestination, TrainingDestination, SettingDestination)
//val navBottomScreens = listOf(WorkoutsDestination, RoundDestination, SetDestination, SettingDestination)
//val listScreens = listOf(WorkoutsDestination, RoundDestination, SetDestination, SettingDestination)
