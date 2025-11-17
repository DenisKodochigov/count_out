package com.count_out.presentation.view_element.bottom_sheet

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CleaningServices
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.R
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen.bsHeightWindowsListBle
import com.count_out.presentation.models.Dimen.bsSpacerBottomHeight
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.icons.IconSingle

@Composable fun DeviceBleBS(dataState: BottomSheetInterface, owner: Domain){
    dataState.item = owner
    if (dataState.item != null && dataState.item is DeviceBle) {
        LaunchedEffect(owner){ dataState.event(SettingsEvent.StartScanBLE) }
        BottomSheetBle(dataState)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetBle(dataState: BottomSheetInterface) {
    dataState.onDismiss = {
        dataState.event(SettingsEvent.StopScanBLE)
        dataState.event(SettingsEvent.Launcher(LauncherBSp()))
    }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = dataState.onDismiss,
        modifier = Modifier.padding(horizontal = 12.dp),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetBleContent(dataState) }
    )
}
@Composable fun BottomSheetBleContent(dataState: BottomSheetInterface) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(bsHeightWindowsListBle)
            .padding(4.dp),
        content = { SettingsBluetooth(dataState) }
    )
    Spacer(modifier = Modifier.height(bsSpacerBottomHeight))
}
@SuppressLint("MissingPermission")
@Composable fun SettingsBluetooth(dataState: BottomSheetInterface) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(30.dp)
    ) {
        TextApp(
            text = stringResource(id = R.string.section_heart_rate),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Rounded.CleaningServices,
            onClick = { dataState.event(SettingsEvent.ClearCacheBLE)})
        Spacer(modifier = Modifier.width(12.dp))
    }
    Spacer(modifier = Modifier.height(12.dp))
    dataState.devicesUI.forEach { dev ->
        Spacer(modifier = Modifier.height(0.dp))
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    dataState.event(SettingsEvent.SelectDevice(dev))
                    dataState.event(SettingsEvent.Launcher(LauncherBSp()))
                }
        ) {
            TextApp(text = dev.address, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(12.dp))
            TextApp(
                text = dev.name.ifEmpty { stringResource(id = R.string.no_name) },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
//    dataState.list.forEach { dev->
//        if (dev is DeviceBle){
//            Spacer(modifier = Modifier.height(0.dp))
//            Row(horizontalArrangement = Arrangement.Start,
//                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 12.dp, end = 12.dp)
//                    .clickable {
//                        dataState.event(SettingsEvent.StopScanBLE)
//                        dataState.event(SettingsEvent.SelectDevice(dev))
//                        dataState.event(SettingsEvent.Launcher(LauncherBSp()))
//                    }
//            ) {
//                TextApp(text = dev.address, style = MaterialTheme.typography.bodyLarge)
//                Spacer(modifier = Modifier.width(12.dp))
//                TextApp(
//                    text = dev.name.ifEmpty { stringResource(id = R.string.no_name) },
//                    style = MaterialTheme.typography.bodyLarge
//                )
//            }
//        }
//    }
}