package com.count_out.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.presentation.R
import com.count_out.presentation.models.alumBodySmall
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.SwitchApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetAddActivity
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetBle
import com.count_out.presentation.view_element.bottom_sheet.CardActivity
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.AnimateIcon
import com.count_out.presentation.view_element.icons.IconSingle
import com.count_out.presentation.view_element.icons.IconsCollapsing

@Composable fun SettingScreen(viewModel: SettingViewModel){
    LaunchedEffect(Unit) { viewModel.submitEvent(SettingsEvent.Init) }
    SettingScreenCreateView( viewModel = viewModel )
}
@Composable fun SettingScreenCreateView( viewModel: SettingViewModel){
    viewModel.screenState.collectAsStateWithLifecycle().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            if (dataState.showBottomSheetAddActivity.value) BottomSheetAddActivity(dataState)
            if (dataState.showBottomSheetBLE.value) {
                BottomSheetBle(dataState)
                LaunchedEffect(dataState.showBottomSheetBLE.value) { dataState.event.run(SettingsEvent.StartScanBLE) }
            }
            SettingScreenLayout(dataState)
        }
    }

//    SettingScreenLayout( dataState = dataState)
}
@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreenLayout(dataState: SettingsState){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .fillMaxSize(),
        content = {
            ActivitySection(dataState = dataState)
            OtherSettings(dataState = dataState)
        }
    )
}

@Composable fun ActivitySection(dataState: SettingsState) {
    Frame {
        Column( modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 6.dp))
        {
            ActivitySectionTitle(dataState = dataState)
            ActivitySectionBody(dataState = dataState)
        }
    }
}
@Composable fun ActivitySectionTitle(dataState: SettingsState){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        IconsCollapsing(
            onClick = { dataState.collapsingActivity.value = !dataState.collapsingActivity.value },
            wrap = dataState.collapsingActivity.value )
        TextApp(text = stringResource(id = R.string.list_activity), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(
            image = Icons.Default.Add,
            onClick = {
//                dataState.activity.value = ActivityImpl()
                dataState.showBottomSheetAddActivity.value = true } )
        Spacer(modifier = Modifier.width(12.dp))
    }
}
@Composable fun ActivitySectionBody(dataState: SettingsState){
    Column(modifier = Modifier.padding(end = 8.dp))
    {
        ActivitySectionBodyList( dataState = dataState)
        Spacer(modifier = Modifier.height(0.dp))
    }
}
@Composable fun ActivitySectionBodyList(dataState: SettingsState) {
    val showActivity = dataState.collapsingActivity.value && dataState.activities.isNotEmpty()
    dataState.activities.forEach { activity ->
        AnimatedVisibility(
            modifier = Modifier.padding(bottom = 8.dp),
            visible = showActivity,
            content = { CardActivity(dataState, activity) }
        )
    }
}
@Composable fun OtherSettings(dataState: SettingsState) {
    SettingSpeechDescription(dataState)
    SettingsBluetooth(dataState)
}
@Composable fun SettingSpeechDescription(dataState: SettingsState){
    Spacer(modifier = Modifier.height(12.dp))
    Frame{
        dataState.settings?.speechDescription?.let { setting->
            SwitchApp(
                setting = setting,
                description = R.string.speech_description,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                change = { checked->
                    dataState.event.run(SettingsEvent.UpdateSetting(Setting.SpeechDescription(!setting)))
                }
            )
        }
    }
}
@Composable fun SettingsBluetooth(dataState: SettingsState){
    Spacer(modifier = Modifier.height(12.dp))
    Frame{
        Column (modifier = Modifier.padding(start = 4.dp, top = 12.dp, bottom = 12.dp).fillMaxWidth()){
            SettingBluetoothTitle(dataState)
            RowBleDevice(dataState)
        }
    }
}
@Composable fun SettingBluetoothTitle(dataState: SettingsState){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextApp(
            text = stringResource(id = R.string.heart_rate_device),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Rounded.CleaningServices, onClick = {
            dataState.event.run(SettingsEvent.ClearCacheBLE)})
        Spacer(modifier = Modifier.width(12.dp))
        AnimateIcon(
            icon = Icons.AutoMirrored.Rounded.BluetoothSearching,
            animate = dataState.connectingState != ConnectState.CONNECTED,
            onClick = {dataState.showBottomSheetBLE.value = true},
        )
        Spacer(modifier = Modifier.width(12.dp))
    }
}

@Composable fun RowBleDevice(dataState: SettingsState){
    Spacer(modifier = Modifier.height(12.dp))
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (dataState.connectingState == ConnectState.CONNECTED) {
            RowBleDeviceItem(modifier = Modifier.weight(1f), dataState = dataState,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        } else {
            RowBleDeviceItem(modifier = Modifier.weight(1f), dataState = dataState,
                style = MaterialTheme.typography.titleMedium)
        }
    }
}
@Composable fun RowBleDeviceItem(modifier: Modifier, dataState: SettingsState, style: TextStyle){
//    lg("RowBleDeviceItem ${dataState.lastConnectHearthRateDevice?.name}")
    val nameDevice = dataState.lastConnectHearthRateDevice?.name?.ifEmpty { stringResource(id = R.string.no_name)}
        ?: stringResource(id = R.string.not_select_device)
    val heartRate = if (dataState.heartRate > 0) dataState.heartRate.toString() else ""
    Column (modifier = modifier.padding(start = 12.dp, end = 12.dp).fillMaxWidth()) {
        TextApp(text = nameDevice, textAlign = TextAlign.Start, style = style)
        TextApp(text = stringResource(id = EnumsTo(dataState.connectingState).string()), style = alumBodySmall)
    }
    TextApp(text = heartRate, style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(start = 12.dp, end = 12.dp))
}

