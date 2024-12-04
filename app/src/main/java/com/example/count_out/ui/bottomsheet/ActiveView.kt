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
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.workout.Activity
import com.example.count_out.ui.dialog.ChangeColorSectionDialog
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.custom_view.Frame


@SuppressLint("UnrememberedMutableState")
@Composable fun CardActivity(uiState: SettingScreenState, activity: Activity) {
    Frame {
        ActivityInfo(
            activity = mutableStateOf(activity),
            onSelect = {
                uiState.activity.value = activity
                uiState.showBottomSheetAddActivity.value = true },
            onChangeColor = { uiState.onSetColorActivity(activity.idActivity, it ) },
            onDeleteActivity = { uiState.onDeleteActivity( it ) },
        )
    }
}

@Composable fun ActivityInfo(
    edit: Boolean = false,
    activity: MutableState<Activity>,
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

    Row( modifier = Modifier.padding( start = 12.dp).clickable { onSelect() }.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Icon(painter = painterResource(id = activity.value.icon), contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextFieldApp(
            edit = edit,
            modifier = Modifier.weight(1f),
            placeholder = activity.value.name,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = mTypography.bodyLarge,
            contentAlignment = Alignment.CenterStart,
            onLossFocus = false,
            onChangeValue = {
                activity.value = (activity.value as ActivityDB).copy(name = it)
                onChange(activity.value as ActivityDB) }
        )
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
            .clickable { activityChangeColor.value = activity.value }
            .background(color = Color(activity.value.color), shape = CircleShape))
        IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
            Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null,
                tint = MaterialTheme.colorScheme.outline)
        }
    }
}
@Composable fun ActivityInfoFull(
    activity: MutableState<Activity>,
    onChange: (ActivityDB) -> Unit = {},
    onChangeColor: (Int) -> Unit = {}
){
    val modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp))
    {
        ActivityInfo(
            activity = activity,
            edit = true,
            onChange = onChange,
            onChangeColor = onChangeColor
        )
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier){
            TextApp(text = stringResource(id = R.string.audio_track) + ":", style = mTypography.bodyLarge)
            FieldF(activity.value.audioTrack) {onChange((activity.value as ActivityDB).copy(audioTrack = it))}
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier ){
            TextApp(text = stringResource(id = R.string.video_clip) + ":", style = mTypography.bodyLarge)
            FieldF(activity.value.videoClip) {onChange((activity.value as ActivityDB).copy(videoClip = it))}
        }
        Column( modifier = modifier ){
            TextApp(text = stringResource(id = R.string.description) + ":", style = mTypography.bodyLarge)
            FieldF(activity.value.description) {onChange((activity.value as ActivityDB).copy(description = it))}
        }
    }
}
@Composable fun FieldF(placeholder: String, onChangeValue: (String)->Unit){
    TextFieldApp(
        edit = true,
        modifier = Modifier.fillMaxWidth().padding(start = 6.dp),
        placeholder = placeholder,
        typeKeyboard = TypeKeyboard.TEXT,
        onLossFocus = false,
        maxLines = 3,
        textStyle = mTypography.bodyLarge,
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