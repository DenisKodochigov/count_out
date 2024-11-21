package com.example.count_out.ui.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.workout.Activity
import com.example.count_out.ui.dialog.ChangeColorSectionDialog
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp


@SuppressLint("UnrememberedMutableState")
@Composable fun CardActivity(uiState: SettingScreenState, activity: Activity)
{
    Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        ActivityValueSelect(
            activity = mutableStateOf(activity),
            onSelect = {
                uiState.activity.value = activity
                uiState.showBottomSheetAddActivity.value = true },
            onChangeColor = { uiState.onSetColorActivity(activity.idActivity, it ) },
            onDeleteActivity = { uiState.onDeleteActivity( it ) },
        )
    }
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun ActivityValueSelect(
    activity: MutableState<Activity>,
    onSelect: () -> Unit = {},
    onChangeColor: (Int) -> Unit = {},
    onDeleteActivity:(Long)-> Unit = {}
){
    val activityChangeColor: MutableState<Activity?> = remember { mutableStateOf(null) }
    activityChangeColor.value?.let { changeColor->
        ChangeColorSectionDialog(
            colorItem = changeColor.color,
            onDismiss = { activityChangeColor.value = null},
            onConfirm = {
                onChangeColor(it)
                activity.value = ( activity.value as ActivityDB ).copy(color = it)
                activityChangeColor.value = null
            },
        )
    }
    Row( modifier = Modifier
        .padding(start = 12.dp)
        .clickable { onSelect() }
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(painter = painterResource(id = activity.value.icon), contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextApp(
            text = activity.value.name,
            style = mTypography.bodyMedium,
            modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable { activityChangeColor.value = activity.value }
            .background(color = Color(activity.value.color), shape = CircleShape))
        IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
            Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null)
        }
    }
}
@Composable
fun ActivityValueFull(
    activity: MutableState<Activity>,
    onChange: (ActivityDB) -> Unit = {},
    onChangeColor: (Int) -> Unit = {}
){
    val modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp))
    {
        ActivityValueEdit(
            activity = activity,
            typeKeyboard = TypeKeyboard.TEXT,
            onChange = onChange,
            onChangeColor = onChangeColor
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier){
            TextApp(text = stringResource(id = R.string.audio_track), style = mTypography.bodySmall)
            TextFieldApp(
                modifier = Modifier.fillMaxWidth(),
                placeholder = activity.value.audioTrack,
                typeKeyboard = TypeKeyboard.TEXT,
                onLossFocus = false,
                textStyle = mTypography.bodySmall,
                onChangeValue = { onChange( (activity.value as ActivityDB).copy(audioTrack = it)) }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier ){
            TextApp(text = stringResource(id = R.string.video_clip), style = mTypography.bodySmall)
            TextFieldApp(
                modifier = Modifier.fillMaxWidth(),
                placeholder = activity.value.videoClip,
                typeKeyboard = TypeKeyboard.TEXT,
                onLossFocus = false,
                textStyle = mTypography.bodySmall,
                onChangeValue = { onChange( (activity.value as ActivityDB).copy(videoClip = it)) }
            )
        }
        Column( modifier = modifier ){
            TextApp(text = stringResource(id = R.string.description), style = mTypography.bodySmall)
            TextFieldApp(
                modifier = Modifier.fillMaxWidth(),
                placeholder = activity.value.description,
                typeKeyboard = TypeKeyboard.TEXT,
                onLossFocus = false,
                maxLines = 3,
                edit = true,
                textStyle = mTypography.bodySmall,
                onChangeValue = { onChange( (activity.value as ActivityDB).copy(description = it)) }
            )
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun ActivityValueEdit(
    activity: MutableState<Activity>,
    typeKeyboard: TypeKeyboard = TypeKeyboard.TEXT,
    onSelect: () -> Unit = {},
    onChange: (ActivityDB) -> Unit = {},
    onChangeColor: (Int) -> Unit = {},
    onDeleteActivity:(Long)-> Unit = {}
){
    val activityChangeColor: MutableState<Activity?> = remember { mutableStateOf(null) }
    activityChangeColor.value?.let { changeColor->
        ChangeColorSectionDialog(
            colorItem = changeColor.color,
            onDismiss = { activityChangeColor.value = null},
            onConfirm = {
                onChangeColor(it)
                activity.value = ( activity.value as ActivityDB ).copy(color = it)
                activityChangeColor.value = null
            },
        )
    }

    Row( modifier = Modifier
        .padding( start = 12.dp)
        .clickable { onSelect() }
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(painter = painterResource(id = activity.value.icon), contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextFieldApp(
            modifier = Modifier.weight(1f),
            placeholder = "${activity.value.idActivity}:${activity.value.name}",
            typeKeyboard = typeKeyboard,
            textStyle = mTypography.bodySmall,
            onLossFocus = false,
            onChangeValue = {
                activity.value = (activity.value as ActivityDB).copy(name = it)
                onChange(activity.value as ActivityDB) }
        )
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable { activityChangeColor.value = activity.value }
            .background(color = Color(activity.value.color), shape = CircleShape))
        IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
            Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null)
        }
    }
}
