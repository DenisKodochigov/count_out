package com.example.count_out.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.count_out.MainActivity
import com.example.count_out.navigation.NavHostApp
import com.example.count_out.navigation.backScreenDestination
import com.example.count_out.navigation.navigateToScreen
import com.example.count_out.permission.RequestPermission1
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.view_components.BottomBarApp
import com.example.count_out.ui.view_components.CollapsingToolbar
import com.example.count_out.ui.view_components.ExtendedFAB


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation",
    "RestrictedApi"
)
@Composable
fun StartApp() {
    AppTheme {
        val navController = rememberNavController()
        val currentScreen = navController.backScreenDestination()
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
                NavHostApp(navController = navController, modifier = Modifier.padding(innerPadding))
//                RequestPermission("POST_NOTIFICATIONS", 31)
//                RequestPermission(Manifest.permission.FOREGROUND_SERVICE, 28)
//                RequestPermission(Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE, 34)
//                RequestPermission(Manifest.permission.BLUETOOTH, 26)
//                RequestPermission(Manifest.permission.BLUETOOTH_SCAN, 31)
//                RequestPermission(Manifest.permission.BLUETOOTH_CONNECT, 30)
//                RequestPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION, 29)
//                RequestPermission(Manifest.permission.INTERNET, 29)
//                RequestPermission(Manifest.permission.ACCESS_NETWORK_STATE, 29)
//                RequestPermission(Manifest.permission.POST_NOTIFICATIONS, 33)
//                RequestPermission(Manifest.permission.READ_PHONE_STATE, 29)
//                RequestPermission(Manifest.permission.VIBRATE, 29)
//                RequestPermission(Manifest.permission.ACTIVITY_RECOGNITION, 29)
                RequestPermission1(LocalContext.current as MainActivity, "BLUETOOTH_CONNECT", 30)
            }
        )
    }
}


@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}