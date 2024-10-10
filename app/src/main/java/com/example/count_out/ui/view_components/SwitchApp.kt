package com.example.count_out.ui.view_components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.ui.theme.mTypography

@Composable
fun SwitchApp(setting: SettingDB, modifier: Modifier = Modifier, change:(Boolean)->Unit) {
    var checked by remember { mutableStateOf( setting.value == 1) }
    Row( verticalAlignment = Alignment.CenterVertically, modifier = modifier){
        TextApp(
            text = stringResource( setting.parameter),
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier.weight(1f),
            style = mTypography.titleMedium,
            fontWeight = FontWeight.Normal)
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                change(it)
            }
        )
    }
}