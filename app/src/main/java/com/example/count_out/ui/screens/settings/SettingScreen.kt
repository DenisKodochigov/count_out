package com.example.count_out.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.BluetoothSearching
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.count_out.R
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.domain.to01
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.ui.bottomsheet.BottomSheetAddActivity
import com.example.count_out.ui.bottomsheet.BottomSheetBle
import com.example.count_out.ui.bottomsheet.CardActivity
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.typography
import com.example.count_out.ui.view_components.AnimateIcon
import com.example.count_out.ui.view_components.IconSingle
import com.example.count_out.ui.view_components.IconsCollapsing
import com.example.count_out.ui.view_components.NameScreen
import com.example.count_out.ui.view_components.SwitchApp
import com.example.count_out.ui.view_components.TextApp

@Composable fun SettingScreen(){
    val viewModel: SettingViewModel = hiltViewModel()
    SettingScreenCreateView( viewModel = viewModel )
}
@Composable fun SettingScreenCreateView( viewModel: SettingViewModel){
    val uiState by viewModel.settingScreenState.collectAsStateWithLifecycle()
    uiState.onConfirmAddActivity = { activity->
        uiState.onAddActivity(activity)
        uiState.showBottomSheetAddActivity.value = false }
    uiState.onDismissAddActivity = { uiState.showBottomSheetAddActivity.value = false }

    uiState.onConfirmBLEScan = { address->
        uiState.onSelectDevice(address)
        uiState.showBottomSheetBLE.value = false }
    uiState.onDismissBLEScan = {
        uiState.onStopScanBLE()
        uiState.showBottomSheetBLE.value = false }

    if (uiState.showBottomSheetAddActivity.value) BottomSheetAddActivity(uiState)
    if (uiState.showBottomSheetBLE.value) {
        BottomSheetBle(uiState)
        LaunchedEffect(uiState.showBottomSheetBLE.value) { uiState.onStartScanBLE() }
    }
    SettingScreenLayout( uiState = uiState)
}
@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreenLayout(uiState: SettingScreenState){
    var animated by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .fillMaxSize(),
        content = {
            NameScreen(id = R.string.title_setting_screen)
            ActivitySection(uiState = uiState,)
            OtherSettings(uiState = uiState,)
        }
    )
}

@Composable fun ActivitySection(uiState: SettingScreenState) {
    Card ( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box {
            Column( modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 6.dp),)
            {
                ActivitySectionTitle(uiState = uiState)
                ActivitySectionBody(uiState = uiState)
            }
        }
    }
}
@Composable fun ActivitySectionTitle(uiState: SettingScreenState){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        IconsCollapsing(
            onClick = { uiState.collapsingActivity.value = !uiState.collapsingActivity.value },
            wrap = uiState.collapsingActivity.value )
        TextApp(text = stringResource(id = R.string.list_activity), style = typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(
            image = Icons.Default.Add,
            onClick = {
                uiState.activity.value = ActivityDB()
                uiState.showBottomSheetAddActivity.value = true } )
        Spacer(modifier = Modifier.width(12.dp))
    }
}
@Composable fun ActivitySectionBody(uiState: SettingScreenState){
    Column(modifier = Modifier.padding(end = 8.dp))
    {
        ActivitySectionBodyList( uiState = uiState )
        Spacer(modifier = Modifier.height(0.dp))
    }
}
@Composable fun ActivitySectionBodyList(uiState: SettingScreenState) {
    val showActivity = uiState.collapsingActivity.value && uiState.activities.isNotEmpty()
    uiState.activities.forEach { activity ->
        AnimatedVisibility(
            modifier = Modifier.padding(bottom = 8.dp),
            visible = showActivity,
            content = { CardActivity(uiState, activity) }
        )
    }
}
@Composable fun OtherSettings(uiState: SettingScreenState) {
    SettingSpeechDescription(uiState)
    SettingsBluetooth(uiState)
}
@Composable fun SettingSpeechDescription(uiState: SettingScreenState){
    Spacer(modifier = Modifier.height(12.dp))
    Card ( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall ){
        uiState.settings.find{
            it.parameter == R.string.speech_description}?.let { setting->
            SwitchApp(
                setting = setting,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                change = { checked-> uiState.onUpdateSetting(setting.copy(value = checked.to01()))
                }
            )
        }
    }
}
@Composable fun SettingsBluetooth(uiState: SettingScreenState){
    Spacer(modifier = Modifier.height(12.dp))
    Card ( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall){
        Column (modifier = Modifier
            .padding(start = 4.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth()
        ){
            SettingBluetoothTitle(uiState)
            RowBleDevice(uiState)
        }
    }
}
@Composable fun SettingBluetoothTitle(uiState: SettingScreenState){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextApp(
            text = stringResource(id = R.string.heart_rate_device),
            style = typography.titleMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        AnimateIcon(
            icon = Icons.AutoMirrored.Rounded.BluetoothSearching,
            animate = uiState.connectingDevice,
            onClick = {uiState.showBottomSheetBLE.value = true},
        )
        Spacer(modifier = Modifier.width(12.dp))
        IconSingle(
            image = Icons.Rounded.CleaningServices,
            onClick = { uiState.onClearCacheBLE }
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable fun RowBleDevice(uiState: SettingScreenState){
    val lastHeartDevice: DeviceUI? = uiState.lastConnectHearthRateDevice
    val heartRate = uiState.heartRate

    Spacer(modifier = Modifier.height(12.dp))
    Row (
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        TextApp(text = lastHeartDevice?.name ?: stringResource(id = R.string.not_select_device),
            style = if (heartRate > 0) typography.titleMedium
            else typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.weight(1f))
        if (!uiState.showBottomSheetBLE.value) TextApp(
            text = (if (heartRate > 0) heartRate else "").toString(),
            modifier = Modifier.padding(end = 24.dp),
            style = typography.bodyLarge)
    }
}





