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
import com.count_out.domain.entity.workout.Activity
import com.count_out.presentation.R
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.screens.settings.SettingsState
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.dialog.ChangeColorSectionDialog

@SuppressLint("UnrememberedMutableState")
@Composable fun CardActivity(dataState: SettingsState, activity: ActivityImplP) {
    Frame {
        ActivityTitle(
            activity = mutableStateOf(ActivityImplP(activity)),
            onSelect = {
                dataState.item = activity
                dataState.event(SettingsEvent.ShowBS(dataState.showBS.copy(domain = activity)))},             onChange = { dataState.event(SettingsEvent.SetColorActivity(activity)) }, //
            onDeleteActivity = { dataState.event(SettingsEvent.DeleteActivity(activity)) },
        )
    }
}

@Composable fun ActivityTitle(
    edit: Boolean = false,
    activity: MutableState<ActivityImplP>,
    onSelect: () -> Unit = {},
    onChange: (ActivityImplP) -> Unit = {},
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
        Icon(painter = painterResource(
            id = activity.value.icon.let { if (it > 0) it else R.drawable.ic_setka }),
            contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextFieldApp(
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            edit = edit,
            placeholder = activity.value.name.let { it.ifEmpty { stringResource(R.string.new_activity) } },
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = MaterialTheme.typography.bodyLarge,
            contentAlignment = Alignment.CenterStart,
            onChangeValue = { activity.value = activity.value.copy(name = it) }
        )
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(width = 1.dp, color = colorScheme.outline, shape = CircleShape)
            .clickable { activityChangeColor.value = activity.value }
            .background(color = Color(activity.value.color), shape = CircleShape))
        if (activity.value.idActivity > 0){
            IconButton( onClick = { onDeleteActivity(activity.value.idActivity) }) {
                Icon(imageVector = Icons.Filled.DeleteSweep, contentDescription = null,
                    tint = colorScheme.outline)
            }
        }
    }
}
@Composable fun ActivityEdit(
    activity: MutableState<ActivityImplP>,
    onChange: (ActivityImplP) -> Unit = {},
){
    val modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
    val modifier1 = Modifier.padding(horizontal = 6.dp, vertical = 0.dp)
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        ActivityTitle(edit = true, activity = activity, onChange = onChange,)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier,){
            TextApp(text = stringResource(id = R.string.audio_track) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            FieldEdit(activity.value.audioTrack, modifier.weight(1f)) {
                onChange((activity.value ).copy(audioTrack = it))}
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier, ){
            TextApp(text = stringResource(id = R.string.video_clip) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            FieldEdit(activity.value.videoClip, modifier.weight(1f)) {
                onChange(activity.value.copy(videoClip = it))}
        }
        Column( modifier = modifier){
            TextApp(text = stringResource(id = R.string.description) + ":", style = MaterialTheme.typography.bodyLarge, modifier = modifier1)
            Row(Modifier.fillMaxWidth()) {
                FieldEdit(activity.value.description, Modifier.weight(1f)) {
                    onChange(activity.value.copy(description = it))}
            }
        }
    }
}
@Composable fun FieldEdit(placeholder: String, modifier:Modifier = Modifier, onChangeValue: (String)->Unit){
    TextFieldApp(
        modifier = modifier,
        edit = true,
        placeholder = placeholder,
        typeKeyboard = TypeKeyboard.TEXT,
        contentAlignment = Alignment.CenterStart,
        maxLines = 3,
        textStyle = MaterialTheme.typography.bodyLarge,
        onChangeValue = { onChangeValue(it) }
    )
}
