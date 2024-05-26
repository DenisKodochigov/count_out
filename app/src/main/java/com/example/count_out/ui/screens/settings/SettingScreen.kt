package com.example.count_out.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.domain.to01
import com.example.count_out.entity.BluetoothDev
import com.example.count_out.ui.bottomsheet.BottomSheetAddActivity
import com.example.count_out.ui.bottomsheet.CardActivity
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold14
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.IconsCollapsing
import com.example.count_out.ui.view_components.TextApp

@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreen(){
    val viewModel: SettingViewModel = hiltViewModel()
    SettingScreenCreateView( viewModel = viewModel )
}
@Composable fun SettingScreenCreateView( viewModel: SettingViewModel
){
    val uiState = viewModel.settingScreenState.collectAsState()
    uiState.value.onDismissAddActivity = { uiState.value.showBottomSheetAddActivity.value = false }
    uiState.value.onConfirmAddActivity = { activity->
        uiState.value.onAddActivity(activity)
        uiState.value.showBottomSheetAddActivity.value = false }
    SettingScreenLayout( uiState = uiState.value)
    if (uiState.value.showBottomSheetAddActivity.value) BottomSheetAddActivity(uiState.value)
}
@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreenLayout(uiState: SettingScreenState
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .fillMaxSize(),
        content = {
            ActiveSection(uiState = uiState,)
            OtherSettings(uiState = uiState,)
        }
    )
}
@Composable fun OtherSettings(uiState: SettingScreenState)
{
    Spacer(modifier = Modifier.height(12.dp))
    SettingSpeechDescription(uiState)
    SettingsBluetooth(uiState)
}
@Composable fun SettingsBluetooth(uiState: SettingScreenState){
    val listBluetoothDev: List<BluetoothDev>
    try {
        listBluetoothDev = uiState.bluetoothDevices.value
    } catch (e: IllegalStateException) {
        return
    }
//    lg("SettingsBluetooth $listBluetoothDev")
    LazyColumn( state = rememberLazyListState(), modifier = Modifier.height(200.dp).fillMaxWidth()
    ){
        items(items = listBluetoothDev){ item->
            Row {
                TextApp(text = item.name, style = interReg14)
                TextApp(text = item.address, style = interReg14)
            }
        }
    }
}
@Composable fun SettingSpeechDescription(uiState: SettingScreenState){
    var enableValue = true
    try { val test = uiState.settings.value } catch (e: IllegalStateException){ enableValue = false}
    if (enableValue) uiState.settings.value.find{
        it.parameter == R.string.speech_description}?.let { setting->
            TemplateSwitch(
                setting = setting,
                change = { checked-> uiState.onUpdateSetting(setting.copy(value = checked.to01()))
            })
    }
}
@Composable fun TemplateSwitch(setting: SettingDB, change:(Boolean)->Unit)
{
    var checked by remember { mutableStateOf( setting.value == 1) }
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp)
    ){
        TextApp(
            text = stringResource( setting.parameter),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
            style = interReg14)
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                change(it)
            }
        )
    }
}

@Composable fun ActiveSection(uiState: SettingScreenState)
{
    Card ( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box {
            Column( modifier = Modifier.padding(start = 6.dp),)
            {
                SectionTitle(uiState = uiState)
                SectionBody(uiState = uiState)
                SectionBottom(uiState = uiState)
            }
        }
    }
}

@Composable fun SectionTitle(uiState: SettingScreenState){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, end = 8.dp)
    ){
        TextApp(text = stringResource(id = R.string.list_activity), style = interBold14)
        Spacer(modifier = Modifier.weight(1f))
        IconsCollapsing(
            onCollapsing = { uiState.collapsingActivity.value = !uiState.collapsingActivity.value },
            wrap = uiState.collapsingActivity.value
        )
    }
}
@Composable fun SectionBody(uiState: SettingScreenState){
    Column(modifier = Modifier.padding(end = 8.dp))
    {
        ListActivity( uiState = uiState )
        Spacer(modifier = Modifier.height(4.dp))
    }
}
@Composable fun SectionBottom(uiState: SettingScreenState){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        Spacer(modifier = Modifier.weight(1f))
        PoleAddActivity(uiState = uiState)
    }
}
@Composable fun ListActivity(uiState: SettingScreenState)
{
    val showActivity = uiState.collapsingActivity.value && uiState.activities.isNotEmpty()
    uiState.activities.forEach { activity ->
        AnimatedVisibility(
            modifier = Modifier.padding(top = 8.dp),
            visible = showActivity,
            content = { CardActivity(uiState, activity) }
        )
    }
}


@Composable fun PoleAddActivity(uiState: SettingScreenState){
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                uiState.activity.value = ActivityDB()
                uiState.showBottomSheetAddActivity.value = true }
        ) {
            TextApp(
                text = stringResource(id = R.string.add_activity),
                style = interLight12,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .size(Dimen.sizeIcon)
            )
        }
    }
}

