package com.count_out.app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.count_out.app.R
import com.count_out.app.presentation.navigation.NavHostApp
import com.count_out.app.presentation.navigation.backScreenDestination
import com.count_out.app.presentation.navigation.navigateToScreen
import com.count_out.app.presentation.theme.AppTheme
import com.count_out.app.presentation.view_components.BottomBarApp
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.enums.Units

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType", "UnrememberedMutableState", "SuspiciousIndentation",
    "RestrictedApi"
)
@Composable
fun StartApp() {
    AppTheme {
        val navController = rememberNavController()
        val currentScreen = navController.backScreenDestination()
        initUnits()
        initPartName()
        Scaffold(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            topBar = {
                TopAppBar(
                    expandedHeight = 0.dp,
                    title = {},
                    modifier = Modifier.height(0.dp),
                    navigationIcon = {},
                    actions = {},
                )
            },
            bottomBar = {
                BottomBarApp(
                    currentScreen = currentScreen,
                    onTabSelection = { newScreen ->
                        navController.navigateToScreen(newScreen.route) })
            },
//            floatingActionButton = {
//                if (currentScreen.showFab) {
//                    ExtendedFAB(textId =  currentScreen.textFABId, onClick = currentScreen.onClickFAB) }
//            },
//            floatingActionButtonPosition = FabPosition.Companion.End,
            content = {  innerPadding ->
                Box(modifier = Modifier.statusBarsPadding().padding(innerPadding)) {
                    NavHostApp(navController = navController)
                }
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
fun initPartName(){
    PartName.WorkUp.idName = R.string.work_up
    PartName.WorkOut.idName = R.string.work_out
    PartName.WorkDown.idName = R.string.work_down
}
@Preview
@Composable
fun StartAppPreview(){
    StartApp()
}