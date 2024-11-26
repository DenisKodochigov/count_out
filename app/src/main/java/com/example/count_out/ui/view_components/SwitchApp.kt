package com.example.count_out.ui.view_components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.ui.theme.alumBodyMedium
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
@Composable fun UnitSwitch (term: Boolean, unitId1: Int, unitId2: Int, modifier: Modifier = Modifier){
    lg("term $term")
    val annotatedString = buildAnnotatedString {
        append("(")
        withStyle(style = SpanStyle(fontWeight = if (term) FontWeight.Normal else FontWeight.ExtraBold))
        { append(stringResource(unitId1)) }
        append("/")
        withStyle(style = SpanStyle(fontWeight = if (term) FontWeight.ExtraBold else FontWeight.Normal))
        { append(stringResource(unitId2)) }
        append(")")
    }

    Text(annotatedString,
        modifier = modifier,
        style = alumBodyMedium,
        color = MaterialTheme.colorScheme.outline
    )
}