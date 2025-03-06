package com.count_out.app.presentation.screens.settings

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.count_out.app.R
import com.count_out.app.presentation.bottom_sheet.BottomSheetAddActivity
import com.count_out.app.presentation.bottom_sheet.BottomSheetBle
import com.count_out.app.presentation.bottom_sheet.CardActivity
import com.count_out.app.presentation.navigation.NavigateEventImpl
import com.count_out.app.presentation.prime.Action
import com.count_out.app.presentation.prime.PrimeScreen
import com.count_out.app.presentation.theme.alumBodySmall
import com.count_out.app.presentation.theme.mTypography
import com.count_out.app.presentation.view_components.SwitchApp
import com.count_out.app.presentation.view_components.TextApp
import com.count_out.app.presentation.view_components.custom_view.Frame
import com.count_out.app.presentation.view_components.icons.AnimateIcon
import com.count_out.app.presentation.view_components.icons.IconSingle
import com.count_out.app.presentation.view_components.icons.IconsCollapsing
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.to01

@Composable fun SettingScreen(navigateEvent: NavigateEventImpl){
    val viewModel: SettingViewModel = hiltViewModel()
    viewModel.initNavigate(navigateEvent)
    LaunchedEffect(Unit) { viewModel.submitEvent(SettingsEvent.Init) }
    SettingScreenCreateView( viewModel = viewModel )
}
@Composable fun SettingScreenCreateView( viewModel: SettingViewModel){
    val action = Action {viewModel.submitEvent(it) }
    viewModel.dataState.collectAsStateWithLifecycle().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->

            if (dataState.showBottomSheetAddActivity.value) BottomSheetAddActivity(dataState, action)
            if (dataState.showBottomSheetBLE.value) {
                BottomSheetBle(dataState, action)
                LaunchedEffect(dataState.showBottomSheetBLE.value) { action.ex(SettingsEvent.StartScanBLE) }
            }
            SettingScreenLayout(dataState, action = action)
        }
    }

//    SettingScreenLayout( dataState = dataState)
}
@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreenLayout(dataState: SettingsState, action: Action){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .fillMaxSize(),
        content = {
            ActivitySection(dataState = dataState, action)
            OtherSettings(dataState = dataState, action)
        }
    )
}

@Composable fun ActivitySection(dataState: SettingsState, action: Action) {
        Frame {
            Column( modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 6.dp))
            {
                ActivitySectionTitle(dataState = dataState)
                ActivitySectionBody(dataState = dataState, action)
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
        TextApp(text = stringResource(id = R.string.list_activity), style = mTypography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(
            image = Icons.Default.Add,
            onClick = {
//                dataState.activity.value = ActivityImpl()
                dataState.showBottomSheetAddActivity.value = true } )
        Spacer(modifier = Modifier.width(12.dp))
    }
}
@Composable fun ActivitySectionBody(dataState: SettingsState, action: Action){
    Column(modifier = Modifier.padding(end = 8.dp))
    {
        ActivitySectionBodyList( dataState = dataState, action)
        Spacer(modifier = Modifier.height(0.dp))
    }
}
@Composable fun ActivitySectionBodyList(dataState: SettingsState, action: Action) {
    val showActivity = dataState.collapsingActivity.value && dataState.activities.isNotEmpty()
    dataState.activities.forEach { activity ->
        AnimatedVisibility(
            modifier = Modifier.padding(bottom = 8.dp),
            visible = showActivity,
            content = { CardActivity(dataState, activity, action) }
        )
    }
}
@Composable fun OtherSettings(dataState: SettingsState, action: Action) {
    SettingSpeechDescription(dataState, action)
    SettingsBluetooth(dataState, action)
}
@Composable fun SettingSpeechDescription(dataState: SettingsState, action: Action){
    Spacer(modifier = Modifier.height(12.dp))
    Frame{
        dataState.settings?.speechDescription?.let { setting->
            SwitchApp(
                setting = setting,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                change = { checked->
                    setting.value = setting.value.to01()
                    action.ex(SettingsEvent.UpdateSetting( setting ))
                }
            )
        }
    }
}
@Composable fun SettingsBluetooth(dataState: SettingsState, action: Action){
    Spacer(modifier = Modifier.height(12.dp))
    Frame{
        Column (modifier = Modifier.padding(start = 4.dp, top = 12.dp, bottom = 12.dp).fillMaxWidth()){
            SettingBluetoothTitle(dataState, action)
            RowBleDevice(dataState)
        }
    }
}
@Composable fun SettingBluetoothTitle(dataState: SettingsState, action: Action){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextApp(
            text = stringResource(id = R.string.heart_rate_device),
            style = mTypography.titleMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Rounded.CleaningServices, onClick = {
            action.ex(SettingsEvent.ClearCacheBLE)})
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
                style = mTypography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        } else {
            RowBleDeviceItem(modifier = Modifier.weight(1f), dataState = dataState,
                style = mTypography.titleMedium)
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
        TextApp(text = stringResource(id = dataState.connectingState.strId), style = alumBodySmall)
    }
    TextApp(text = heartRate, style = mTypography.displayMedium, modifier = Modifier.padding(start = 12.dp, end = 12.dp))
}




