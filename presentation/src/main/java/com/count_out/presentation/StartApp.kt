package com.count_out.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.count_out.presentation.navigation.NavHostApp
import com.count_out.presentation.navigation.backScreenDestination
import com.count_out.presentation.navigation.navigateToScreen
import com.count_out.presentation.view_components.BottomBarApp
import com.count_out.presentation.view_components.CollapsingToolbar


@OptIn(androidx.compose.ui.ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation",
    "RestrictedApi"
)
@androidx.compose.runtime.Composable
fun StartApp() {

    com.count_out.app.ui.theme.AppTheme {
        val navController = androidx.navigation.compose.rememberNavController()
        val currentScreen = navController.backScreenDestination()
        initUnits()
        androidx.compose.material3.Scaffold(
            modifier = androidx.compose.ui.Modifier.Companion.semantics {
                testTagsAsResourceId = true
            },
            topBar = {
                CollapsingToolbar(
                    text = androidx.compose.ui.res.stringResource(currentScreen.nameScreen),
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
            floatingActionButtonPosition = androidx.compose.material3.FabPosition.Companion.End,
            content = { innerPadding ->
                NavHostApp(
                    navController = navController,
                    modifier = androidx.compose.ui.Modifier.Companion.padding(innerPadding)
                )

            }
        )
    }
}

fun initUnits(){
    com.count_out.domain.entity.enums.Units.S.id = com.count_out.app.R.string.sec
    com.count_out.domain.entity.enums.Units.M.id = com.count_out.app.R.string.min
    com.count_out.domain.entity.enums.Units.H.id = com.count_out.app.R.string.hour
    com.count_out.domain.entity.enums.Units.KM.id = com.count_out.app.R.string.km
    com.count_out.domain.entity.enums.Units.M.id = com.count_out.app.R.string.m
    com.count_out.domain.entity.enums.Units.KG.id = com.count_out.app.R.string.kg
    com.count_out.domain.entity.enums.Units.GR.id = com.count_out.app.R.string.gr
}

@androidx.compose.ui.tooling.preview.Preview
@androidx.compose.runtime.Composable
fun StartAppPreview(){
    StartApp()
}