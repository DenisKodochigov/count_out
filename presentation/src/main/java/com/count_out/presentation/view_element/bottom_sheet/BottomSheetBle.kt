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
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.bsHeightWindowsListBle
import com.count_out.presentation.models.Dimen.bsSpacerBottomHeight
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.screens.settings.SettingsState
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.icons.IconSingle

@Composable fun ShowBottomSheetBle(dataState: SettingsState, showBS: Boolean
){
    if (showBS) {
        LaunchedEffect(dataState.showBS.selectBleDevice){ dataState.event(SettingsEvent.StartScanBLE) }
        BottomSheetBle(dataState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetBle(dataState: SettingsState) {
    dataState.onDismiss = {
        dataState.event(SettingsEvent.StopScanBLE)
        dataState.event(SettingsEvent.ShowBS(dataState.showBS.copy(element =
            object: DeviceBle{ override val name: String = ""; override val address: String = "" })))
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = dataState.onDismiss,
        modifier = Modifier.padding(horizontal = 12.dp),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetBleContent(dataState) }
    )
}
@Composable fun BottomSheetBleContent(dataState: SettingsState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().height(bsHeightWindowsListBle).padding(4.dp),
        content = { SettingsBluetooth(dataState) }
    )
    Spacer(modifier = Modifier.height(bsSpacerBottomHeight))
}
@SuppressLint("MissingPermission")
@Composable fun SettingsBluetooth(dataState: SettingsState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp).height(30.dp)
    ) {
        TextApp(
            text = stringResource(id = R.string.section_heart_rate),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Rounded.CleaningServices,
            onClick = { dataState.event(SettingsEvent.ClearCacheBLE)})
        Spacer(modifier = Modifier.width(12.dp))
//        AnimateIcon(animate = dataState.scannedBle)
    }
    Spacer(modifier = Modifier.height(12.dp))

    dataState.devicesUI.values.forEach { dev->
        Spacer(modifier = Modifier.height(0.dp))
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    dataState.event(SettingsEvent.ShowBS(dataState.showBS.copy(element =
                        object: DeviceBle{ override val name: String = ""; override val address: String = "" })))
                    dataState.event(SettingsEvent.SelectDevice(dev))
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
}

//    LazyColumn(
//        state = rememberLazyListState(),
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(6.dp)
//            .border(width = 1.dp, color = colorScheme.onTertiaryContainer, shape = shapes.small)
//    ) {
//        items(items = dataState.devicesUI) { item ->
//
//        }
//    }
//}