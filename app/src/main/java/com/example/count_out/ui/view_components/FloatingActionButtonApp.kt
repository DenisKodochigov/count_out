package com.example.count_out.ui.view_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.theme.alumniReg14
import com.example.count_out.ui.theme.colorApp
import com.example.count_out.ui.theme.interLight12
import java.math.RoundingMode

@Composable fun ExtendedFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
    onClick: () -> Unit,
    textId: Int
){
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        icon = { IconFab(icon = icon) },
        text = { TextApp(text = stringResource(id = textId), style = alumniReg14) },
    )
}
@Composable fun IconFab(icon: ImageVector){
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = colorApp.onPrimaryContainer,
    )
}
@Composable fun FABStartStopWorkOut(
    modifier: Modifier = Modifier,
    onClickStart: () -> Unit,
    onClickStop: () -> Unit,
    onClickPause: () -> Unit,
    switchStartStop: Boolean
){
    Column (modifier = modifier,){
        Row( horizontalArrangement = Arrangement.Center) {
            if (switchStartStop) {
                FloatingActionButton(
                    onClick = onClickStart,
                    content = { Icon(Icons.Filled.PlayArrow,"") },
                )
            } else {
                FloatingActionButton(
                    onClick = onClickStop,
                    content = { Icon(Icons.Filled.Stop,"") },
                )
                Spacer(modifier = Modifier.width(12.dp))
                FloatingActionButton(
                    onClick = onClickPause,
                    content = { Icon(Icons.Filled.Pause,"") },
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable fun FABCorrectInterval(
    currentValue: Double,
    downInterval: () -> Unit,
    upInterval: () -> Unit,
){
    Box(modifier = Modifier.fillMaxSize().padding(12.dp)){
        Column (modifier = Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier.size(32.dp).clickable { upInterval() },
                imageVector = Icons.Default.AddCircle,
                contentDescription = "")
            TextApp(text = currentValue.toBigDecimal().setScale(1, RoundingMode.UP).toString(),
                style = interLight12)
            Icon(
                modifier = Modifier.size(32.dp).clickable { downInterval() },
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = "")
        }
    }
}
