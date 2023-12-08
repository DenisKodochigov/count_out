package com.example.count_out.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.count_out.R

/*** Contract for information needed on every App navigation destination*/
interface ScreenDestination {
    val icon: ImageVector
    val nameInt: Int
    val route: String
    val pictureDay: Int
    val pictureNight: Int
    val textHeader: Int
    var textFAB: String
    var onClickFAB: () -> Unit
}

/*** App app navigation destinations*/
object WorkoutsDestination : ScreenDestination {
    override val icon = Icons.Filled.ShoppingBasket
    override val nameInt: Int = R.string.History
    override val route = "workouts"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}
}
object TemplatesDestination : ScreenDestination {
    override val icon = Icons.Filled.ShoppingBasket
    override val nameInt: Int = R.string.templates_workout
    override val route = "workouts"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}
}
object TemplateDestination : ScreenDestination {
    override val icon = Icons.Filled.ShoppingBasket
    override val nameInt: Int = R.string.templat_workout
    override val route = "workouts"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}

    const val templateIdArg = "template_id"
    val routeWithArgs = "${WorkoutDestination.route}/{$templateIdArg}"
    val arguments = listOf(navArgument(templateIdArg) { type = NavType.LongType })
}
object WorkoutDestination : ScreenDestination {
    override val icon = Icons.Filled.ShoppingBasket
    override val nameInt: Int = R.string.Workout
    override val route = "workout"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}

    const val workoutIdArg = "workout_id"
    val routeWithArgs = "${route}/{$workoutIdArg}"
    val arguments = listOf(navArgument(workoutIdArg) { type = NavType.LongType })
}

object RoundDestination : ScreenDestination {
    override val icon = Icons.Filled.Dashboard
    override val nameInt: Int = R.string.History
    override val route = "round"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}

    const val workoutIdArg = "workout_id"
    val routeWithArgs = "${route}/{$workoutIdArg}"
    val arguments = listOf(navArgument(workoutIdArg) { type = NavType.LongType })
}

object SetDestination : ScreenDestination {
    override val icon = Icons.Filled.Dashboard
    override val nameInt: Int = R.string.History
    override val route = "set"
    override val pictureDay = R.drawable.ic_launcher_background
    override val pictureNight = R.drawable.ic_launcher_background
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}
    const val setsIdArg = "workout_type"
    val routeWithArgs = "${route}/{$setsIdArg}"
    val arguments = listOf(navArgument(setsIdArg) { type = NavType.LongType })
}

object SettingDestination : ScreenDestination {
    override val icon = Icons.Filled.Settings
    override val nameInt: Int = R.string.History
    override val route = "settings"
    override val pictureDay = 0
    override val pictureNight = 0
    override val textHeader = R.string.app_name
    override var textFAB: String = ""
    override var onClickFAB: () -> Unit = {}
}

//object SingleAccount : ScreenDestination {
//    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
//    // part of the AppTabRow selection
//    override val icon = Icons.Filled.Money
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
val navBottomScreens = listOf(WorkoutsDestination, RoundDestination, SetDestination, SettingDestination)
val listScreens = listOf(WorkoutsDestination, RoundDestination, SetDestination, SettingDestination)
