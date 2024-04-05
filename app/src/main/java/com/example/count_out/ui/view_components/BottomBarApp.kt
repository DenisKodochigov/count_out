package com.example.count_out.ui.view_components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.count_out.entity.Const.TAB_FADE_IN_ANIMATION_DELAY
import com.example.count_out.entity.Const.TAB_FADE_IN_ANIMATION_DURATION
import com.example.count_out.entity.Const.TAB_FADE_OUT_ANIMATION_DURATION
import com.example.count_out.entity.TagsTesting.BOTTOM_APP_BAR
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.navigation.TrainingsDestination
import com.example.count_out.navigation.navBottomScreens
import com.example.count_out.ui.theme.alumniReg12
import com.example.count_out.ui.theme.bottomBarShape

@Composable
fun BottomBarApp(
    currentScreen: ScreenDestination,
    onTabSelection: (ScreenDestination) -> Unit,
    modifier: Modifier = Modifier
){
    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        tonalElevation = 6.dp,
        modifier = modifier
            .testTag(BOTTOM_APP_BAR)
            .clip(shape = bottomBarShape),
        content = { BottomBarContent( currentScreen = currentScreen, onTabSelection = onTabSelection )}
    )
}

@Composable fun BottomBarContent(
    currentScreen: ScreenDestination,
    onTabSelection: (ScreenDestination) -> Unit,
){
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.weight(1f))
        navBottomScreens.forEach { screen ->
            ItemBottomBar(
                text = stringResource(id = screen.iconText),
                icon = screen.icon,
                onSelected = { onTabSelection(screen) },
                selected = currentScreen == screen
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun ItemBottomBar(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    val animationSpec = remember {
        tween<Color>(
            easing = LinearEasing,
            delayMillis = TAB_FADE_IN_ANIMATION_DELAY,
            durationMillis = if (selected) TAB_FADE_IN_ANIMATION_DURATION
                                else TAB_FADE_OUT_ANIMATION_DURATION
        )
    }
    val colorIcon = MaterialTheme.colorScheme.onSurface
    val colorUnselected = Color(
        colorIcon.red,
        colorIcon.green,
        colorIcon.blue,
        colorIcon.alpha * 0.6f
    )
    val iconColor by animateColorAsState(
        label = "",
        animationSpec = animationSpec,
        targetValue = if (selected) colorIcon else colorUnselected,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        IconButton(
            onClick = onSelected,
            modifier = Modifier.testTag(text)
        ){
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = iconColor,
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
                    .clearAndSetSemantics { contentDescription = text }
            )
        }
        TextApp(text = text, style = alumniReg12)
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BottomBarAppPreview() {
    BottomBarApp(currentScreen = TrainingsDestination, {})
}