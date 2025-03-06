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
import com.example.count_out.entity.enums.Units
import com.example.count_out.ui.navigation.NavHostApp
import com.example.count_out.ui.navigation.backScreenDestination
import com.example.count_out.ui.navigation.navigateToScreen
import com.example.count_out.ui.theme.AppTheme
import com.example.count_out.ui.view_components.BottomBarApp
import com.example.count_out.ui.view_components.CollapsingToolbar
import com.example.count_out.R


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation",
    "RestrictedApi"
)
@Composable
fun StartApp() {

    AppTheme {
        val navController = rememberNavController()
        val currentScreen = navController.backScreenDestination()
        initUnits()
        Scaffold(
            modifier = Modifier.Companion.semantics {
                testTagsAsResourceId = true
            },
            topBar = {
                CollapsingToolbar(
                    text = stringResource(currentScreen.nameScreen),
                    moreHoriz = { println(navController.currentBackStack.value.toString()) },
                    backScreen = { navController.popBackStack() })
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
            floatingActionButtonPosition = FabPosition.Companion.End,
            content = { innerPadding ->
                NavHostApp(
                    navController = navController,
                    modifier = Modifier.Companion.padding(innerPadding)
                )

            }
        )
    }
}

fun initUnits(){
    Units.S.id = R.string.sec
    Units.M.id = R.string.min
    Units.H.id = R.string.hour
    Units.KM.id = R.string.km
    Units.M.id = R.string.m
    Units.KG.id = R.string.kg
    Units.GR.id = R.string.gr
}

@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}