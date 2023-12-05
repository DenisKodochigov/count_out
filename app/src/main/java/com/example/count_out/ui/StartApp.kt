package com.example.count_out.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.basket.navigation.navigateToScreen
import com.example.count_out.entity.SizeElement
import com.example.count_out.navigation.AppNavHost
import com.example.count_out.navigation.SettingScreen
import com.example.count_out.navigation.WorkoutsScreen
import com.example.count_out.navigation.listScreens
import com.example.count_out.navigation.navBottomScreens
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.view_components.AppBottomBar
import com.example.count_out.ui.view_components.ExtendedFAB

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun StartApp() {
    AppTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        Scaffold(
            modifier = Modifier
                .padding(horizontal = Dimen.paddingAppHor, vertical = Dimen.paddingAppVer)
                .semantics { testTagsAsResourceId = true }
                .background(color = MaterialTheme.colorScheme.background),
            bottomBar = {
                AppBottomBar(
                    currentScreen = navBottomScreens.find {
                        it.route == currentDestination?.route } ?: WorkoutsScreen,
                    modifier = Modifier.height(sizeApp(SizeElement.HEIGHT_BOTTOM_BAR)),
                    onTabSelection = { newScreen -> navController.navigateToScreen(newScreen.route) }
                )
            },
            floatingActionButton = {
                val currentScreen = listScreens.find { it.route ==
                        currentDestination?.route?.substringBefore("/") } ?: WorkoutsScreen
                if (currentScreen != SettingScreen ) {
                    ExtendedFAB(
                        text =  currentScreen.textFAB,
                        onClick = currentScreen.onClickFAB,
                        modifier = Modifier
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center,
        ) { innerPadding ->
            AppNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}