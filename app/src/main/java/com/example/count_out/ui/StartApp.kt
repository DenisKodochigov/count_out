package com.example.count_out.ui

//import com.example.count_out.navigation.SettingDestination
//import com.example.count_out.navigation.WorkoutsDestination
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
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
import com.example.count_out.entity.SizeElement
import com.example.count_out.navigation.AppNavHost
import com.example.count_out.navigation.TrainingsDestination
import com.example.count_out.navigation.listScreens
import com.example.count_out.navigation.navBottomScreens
import com.example.count_out.navigation.navigateToScreen
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.view_components.BottomBarApp
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
                .semantics { testTagsAsResourceId = true },
//                .background(color = MaterialTheme.colorScheme.background),
            bottomBar = {
                BottomBarApp(
                    currentScreen = navBottomScreens.find {
                        it.route == currentDestination?.route } ?: TrainingsDestination,
                    modifier = Modifier.height(sizeApp(SizeElement.HEIGHT_BOTTOM_BAR)),
                    onTabSelection = { newScreen -> navController.navigateToScreen(newScreen.route) }
                )
            },
            floatingActionButton = {
                val currentScreen = listScreens.find { it.route ==
                        currentDestination?.route?.substringBefore("/") } ?: TrainingsDestination
                if (currentScreen != TrainingsDestination ) {
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