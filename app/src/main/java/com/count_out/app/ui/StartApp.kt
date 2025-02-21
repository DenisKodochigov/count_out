package com.count_out.app.ui

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
import com.count_out.app.R
import com.count_out.app.presentation.navigation.NavHostApp
import com.count_out.app.presentation.navigation.backScreenDestination
import com.count_out.app.presentation.navigation.navigateToScreen
import com.count_out.app.ui.theme.AppTheme
import com.count_out.app.ui.view_components.BottomBarApp
import com.count_out.app.ui.view_components.CollapsingToolbar
import com.count_out.domain.entity.enums.Units


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