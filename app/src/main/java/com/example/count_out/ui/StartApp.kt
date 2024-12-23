package com.example.count_out.ui

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
import com.example.count_out.ui.navigation.NavHostApp
import com.example.count_out.ui.navigation.backScreenDestination
import com.example.count_out.ui.navigation.navigateToScreen
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.view_components.BottomBarApp
import com.example.count_out.ui.view_components.CollapsingToolbar


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
                    onTabSelection = { newScreen -> navController.navigateToScreen(newScreen.route) })
            },
//            floatingActionButton = {
//                if (currentScreen.showFab) {
//                    ExtendedFAB(textId =  currentScreen.textFABId, onClick = currentScreen.onClickFAB) }
//            },
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                NavHostApp(navController = navController, modifier = Modifier.padding(innerPadding))

            }
        )
    }
}


@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}