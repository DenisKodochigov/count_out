package com.count_out.app.ui.view_components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.count_out.app.entity.TagsTesting.BOTTOM_APP_BAR
import com.count_out.app.presentation.navigation.ScreenDestination
import com.count_out.app.presentation.navigation.TrainingsDestination
import com.count_out.app.presentation.navigation.navBottomScreens
import com.count_out.app.ui.theme.bottomBarShape
import com.count_out.app.ui.view_components.icons.IconSubscribe

@Composable
fun BottomBarApp(
    currentScreen: ScreenDestination,
    onTabSelection: (ScreenDestination) -> Unit,
    modifier: Modifier = Modifier
){
    Box( modifier = Modifier.fillMaxWidth()){
        BottomAppBar(
            contentPadding = PaddingValues(0.dp),
            tonalElevation = 6.dp,
            modifier = modifier.padding(top = 16.dp)
                .testTag(BOTTOM_APP_BAR)
                .clip(shape = bottomBarShape),
            content = { BottomBarContent( currentScreen = currentScreen, onTabSelection = onTabSelection )}
        )
    }
}

@Composable fun BottomBarContent(
    currentScreen: ScreenDestination,
    onTabSelection: (ScreenDestination) -> Unit,
){
    Row(
        modifier = Modifier.padding(horizontal = 12.dp).fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.weight(1f))
        navBottomScreens.forEach { screen ->
            IconSubscribe(
                text = stringResource(id = screen.iconText),
                icon = screen.icon,
                onSelected = { onTabSelection(screen) },
                selected = currentScreen == screen
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BottomBarAppPreview() {
    BottomBarApp(currentScreen = TrainingsDestination, {})
}