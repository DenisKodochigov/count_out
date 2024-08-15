package com.example.count_out.ui.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.bsHeightWindowsListBle
import com.example.count_out.ui.theme.Dimen.bsSpacerBottomHeight
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interReg16
import com.example.count_out.ui.theme.interReg18
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.AnimateIcon
import com.example.count_out.ui.view_components.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBle(uiState: SettingScreenState) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = { uiState.onDismissBLEScan.invoke() },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetBleContent(uiState) })
}
@Composable
fun BottomSheetBleContent(uiState: SettingScreenState)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(bsHeightWindowsListBle)
            .padding(Dimen.bsItemPaddingHor),
        content = { SettingsBluetooth(uiState) })
    Spacer(modifier = Modifier.height(bsSpacerBottomHeight))
}
@SuppressLint("MissingPermission")
@Composable fun SettingsBluetooth(uiState: SettingScreenState){
    val listBluetoothDev: List<DeviceUI> = remember {uiState.devicesUI}
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp).height(30.dp)) {
        TextApp(text = stringResource(id = R.string.section_heart_rate), style = interReg18)
        Spacer(modifier = Modifier.weight(1f))
        AnimateIcon(animate = uiState.scannedBle)
    }
    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .border(width = 1.dp, color = colorScheme.onTertiaryContainer, shape = shapes.small)
    ) {
        items(items = listBluetoothDev) { item ->
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .clickable { uiState.onSelectDevice(item.address) }) {
                TextApp(text = item.address, style = interReg16)
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.name.ifEmpty { "not name" }, style = interReg14)
            }
        }
    }
}