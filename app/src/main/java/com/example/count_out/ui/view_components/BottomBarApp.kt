package com.example.count_out.ui.view_components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.count_out.entity.TagsTesting.BOTTOM_APP_BAR
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.navigation.WorkoutsDestination
import com.example.count_out.navigation.navBottomScreens
import com.example.count_out.ui.theme.TabFadeInAnimationDelay
import com.example.count_out.ui.theme.TabFadeInAnimationDuration
import com.example.count_out.ui.theme.TabFadeOutAnimationDuration

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
            .background(color = Color.Transparent)  //MaterialTheme.colorScheme.surface)
            .testTag(BOTTOM_APP_BAR)
            .clip(shape = MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            navBottomScreens.forEachIndexed { index, screen ->
                BottomTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelection(screen) },
                    selected = currentScreen == screen
                )
                if (index != navBottomScreens.size-1) Spacer(modifier = Modifier.weight(1f).width(12.dp))
            }
        }
    }
}

@Composable
private fun BottomTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    val animationSpec = remember {
        tween<Color>(
            easing = LinearEasing, delayMillis = TabFadeInAnimationDelay,
            durationMillis = if (selected) TabFadeInAnimationDuration
            else TabFadeOutAnimationDuration
        )
    }

    val colorUnselected = Color(
        MaterialTheme.colorScheme.primary.red,
        MaterialTheme.colorScheme.primary.green,
        MaterialTheme.colorScheme.primary.blue,
        MaterialTheme.colorScheme.primary.alpha * 0.6f
    )

    val iconColor by animateColorAsState(
        label = "", animationSpec = animationSpec,
        targetValue = if (selected) MaterialTheme.colorScheme.primary else colorUnselected,
    )
    IconButton(
        onClick = onSelected,
        modifier = Modifier.fillMaxHeight().testTag(text)
    ) {

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
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BottomBarAppPreview() {
    BottomBarApp(currentScreen = WorkoutsDestination, {})
}