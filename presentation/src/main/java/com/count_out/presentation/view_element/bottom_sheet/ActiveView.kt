package com.count_out.presentation.view_element.bottom_sheet

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
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
import androidx.compose.ui.unit.dp
import com.count_out.presentation.R
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.view_element.dialog.ChangeColorSectionDialog
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.screens.settings.SettingsState
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.domain.entity.workout.Activity
import com.count_out.presentation.models.ActivityImpl

@SuppressLint("UnrememberedMutableState")
@Composable fun CardActivity(dataState: SettingsState, activity: Activity, action: Action) {
    Frame {
        ActivityInfo(
            activity = mutableStateOf(activity as ActivityImpl),
            onSelect = {
                dataState.activity.value = activity
                dataState.showBottomSheetAddActivity.value = true },
            onChange = { action.ex(SettingsEvent.SetColorActivity(activity)) },
            onDeleteActivity = { action.ex(SettingsEvent.DeleteActivity(activity)) },
        )
    }
}

@Composable fun ActivityInfo(
    edit: Boolean = false,
    activity: MutableState<ActivityImpl>,
    onSelect: () -> Unit = {},
    onChange: (ActivityImpl) -> Unit = {},
    onDeleteActivity:(Long)-> Unit = {}
){
    val activityChangeColor: MutableState<Activity?> = remember { mutableStateOf(null) }
    activityChangeColor.value?.let { changeColor->
        ChangeColorSectionDialog(
            colorItem = changeColor.color,
            onDismiss = { activityChangeColor.value = null },
            onConfirm = {
                activity.value = activity.value.copy(color = it)
                onChange(activity.value)
                activityChangeColor.value = null
            },
        )
    }

    Row( modifier = Modifier.padding( start = 12.dp).clickable { onSelect() }.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(painter = painterResource(id = activity.value.icon), contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextFieldApp(
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            edit = edit,
            placeholder = activity.value.name,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = MaterialTheme.typography.bodyLarge,
            contentAlignment = Alignment.CenterStart,
            onLossFocus = false,
            onChangeValue = {
                activity.value = activity.value.copy(name = it)
                onChange(activity.value) }
        )
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = colorScheme.outline, shape = CircleShape)
            .clickable { activityChangeColor.value = activity.value }
            .background(color = Color(activity.value.color), shape = CircleShape))
        IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
            Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null,
                tint = colorScheme.outline)
        }
    }
}
@Composable fun ActivityInfoFull(
    activity: MutableState<ActivityImpl>,
    onChange: (Activity) -> Unit = {},
){
    val modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
    val modifier1 = Modifier.padding(horizontal = 6.dp, vertical = 0.dp)
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        ActivityInfo(
            activity = activity,
            edit = true,
            onChange = onChange,
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
            .background(color = colorScheme.onSecondary, shape = shapes.small),){
            TextApp(text = stringResource(id = R.string.audio_track) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            FieldF(activity.value.audioTrack, modifier.weight(1f)) {
                onChange((activity.value ).copy(audioTrack = it))}
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
            .background(color = colorScheme.onSecondary, shape = shapes.small), ){
            TextApp(text = stringResource(id = R.string.video_clip) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            FieldF(activity.value.videoClip, modifier.weight(1f)) {
                onChange(activity.value.copy(videoClip = it))}
        }
        Column( modifier = modifier.background(color = colorScheme.onSecondary, shape = shapes.small)){
            TextApp(text = stringResource(id = R.string.description) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            Row(Modifier.fillMaxWidth()) {
                FieldF(activity.value.description, Modifier.weight(1f)) {
                    onChange(activity.value.copy(description = it))}
            }
        }
    }
}
@Composable fun FieldF(placeholder: String, modifier:Modifier = Modifier, onChangeValue: (String)->Unit){
    TextFieldApp(
        modifier = modifier,
        edit = true,
        placeholder = placeholder,
        typeKeyboard = TypeKeyboard.TEXT,
        contentAlignment = Alignment.CenterStart,
        onLossFocus = false,
        maxLines = 3,
        textStyle = MaterialTheme.typography.bodyLarge,
        onChangeValue = { onChangeValue(it) }
    )
}
//@SuppressLint("UnrememberedMutableState")
//@Composable
//fun ActivityValueSelect1(
//    activity: MutableState<Activity>,
//    onSelect: () -> Unit = {},
//    onChangeColor: (Int) -> Unit = {},
//    onDeleteActivity:(Long)-> Unit = {}
//){
//    val activityChangeColor: MutableState<Activity?> = remember { mutableStateOf(null) }
//    activityChangeColor.value?.let { changeColor->
//        ChangeColorSectionDialog(
//            colorItem = changeColor.color,
//            onDismiss = { activityChangeColor.value = null},
//            onConfirm = {
//                onChangeColor(it)
//                activity.value = ( activity.value as ActivityDB ).copy(color = it)
//                activityChangeColor.value = null
//            },
//        )
//    }
//    Row( modifier = Modifier.padding(start = 12.dp).clickable { onSelect() }.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start
//    ){
//        Icon(painter = painterResource(id = activity.value.icon), contentDescription = null)
//        Spacer(modifier = Modifier.padding(end= 12.dp))
//        TextApp(
//            text = activity.value.name,
//            textAlign = TextAlign.Start,
//            style = mTypography.bodyMedium,
//            modifier = Modifier.weight(1f),)
//        Spacer(modifier = Modifier
//            .size(size = 32.dp)
//            .clip(shape = CircleShape)
//            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
//            .clickable { activityChangeColor.value = activity.value }
//            .background(color = Color(activity.value.color), shape = CircleShape))
//        IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
//            Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null,
//                tint = MaterialTheme.colorScheme.outline)
//        }
//    }
//}