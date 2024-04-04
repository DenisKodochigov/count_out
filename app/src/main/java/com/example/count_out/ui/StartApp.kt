package com.example.count_out.ui

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.count_out.navigation.NavHostApp
import com.example.count_out.navigation.backStack
import com.example.count_out.navigation.navigateToScreen
import com.example.count_out.permission.RequestPermission
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.view_components.BottomBarApp
import com.example.count_out.ui.view_components.CollapsingToolbar
import com.example.count_out.ui.view_components.ExtendedFAB
import com.example.count_out.ui.view_components.lg

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation",
    "RestrictedApi"
)
@Composable
fun StartApp() {
    AppTheme {
        val navController = rememberNavController()
//        val currentBackStack by navController.currentBackStackEntryAsState()
//        val currentDestination = currentBackStack?.destination
//        val currentScreen = listScreens.find {
//            it.routeWithArgs == currentDestination?.route } ?: TrainingsDestination
        val currentScreen = navController.backStack()
        Scaffold(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            topBar = {
                CollapsingToolbar(
                    text = stringResource(currentScreen.nameScreen),
                    moreHoriz = { println(navController.currentBackStack.value.toString())}  ,
                    backScreen = { navController.popBackStack()})
            },
            bottomBar = {
                BottomBarApp(
                    currentScreen = currentScreen,
                    onTabSelection = { newScreen -> navController.navigateToScreen(newScreen.route) }
                )
            },
            floatingActionButton = {
                if (currentScreen.showFab) {
                    ExtendedFAB(textId =  currentScreen.textFABId, onClick = currentScreen.onClickFAB) }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                lg("StartApp")
                NavHostApp(navController = navController, modifier = Modifier.padding(innerPadding))
                RequestPermission(Manifest.permission.POST_NOTIFICATIONS, 31)
                RequestPermission(Manifest.permission.FOREGROUND_SERVICE, 28)
                RequestPermission(Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE, 34)
            }
        )
    }
}


@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}