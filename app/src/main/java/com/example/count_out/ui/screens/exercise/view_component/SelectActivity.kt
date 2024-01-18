package com.example.count_out.ui.screens.exercise.view_component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.screens.exercise.ExerciseScreenState
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.TextApp

@Composable
fun SelectActivity(uiState: ExerciseScreenState)
{
    Row(verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
    ){
        IconSelectActivity(uiState)
        Spacer(modifier = Modifier.width(12.dp))
        TextActivity(uiState)
        Spacer(modifier = Modifier.weight(1f))
        IconSpeech(uiState)
    }
}
@Composable
fun IconSelectActivity(uiState: ExerciseScreenState)
{
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),
        onClick = { uiState.showBottomSheetSelectActivity.value = true })
    { Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "") }
}
@Composable
fun TextActivity(uiState: ExerciseScreenState)
{
    TextApp(text = uiState.exercise.activity.name, style = interReg14)
}
@Composable
fun IconSpeech(uiState: ExerciseScreenState)
{
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),
        onClick = { uiState.showBottomSheetSpeech.value = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ecv), contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}