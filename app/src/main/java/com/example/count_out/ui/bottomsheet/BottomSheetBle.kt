package com.example.count_out.ui.bottomsheet

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.permission.checkPermission
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interReg18
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.ButtonConfirm
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.lg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBle(uiState: SettingScreenState) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = { uiState.onDismissAddActivity.invoke() },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapesApp.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetBluetoothContent(uiState) })
}
@Composable
fun BottomSheetBluetoothContent(uiState: SettingScreenState)
{
    lg("BottomSheetBluetoothContent")
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ActivityValueFull(
            activity = uiState.activity,
            onChange = { uiState.onUpdateActivity(it) },
            onChangeColor = { uiState.onSetColorActivity(uiState.activity.value.idActivity, it ) }
        )
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        ButtonConfirm( onConfirm = {
            uiState.onConfirmAddActivity(uiState.activity.value)
        } )
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}
@Composable fun SettingsBluetooth(uiState: SettingScreenState){
    val listBluetoothDev: List<BluetoothDevice>
    try {
        listBluetoothDev = uiState.bluetoothDevices.value
    } catch (e: IllegalStateException) {
        return
    }
//    lg("SettingsBluetooth $listBluetoothDev")
    Column (
        modifier = Modifier
            .border(width = 1.dp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                shape = MaterialTheme.shapes.small)
            .height(200.dp)
            .padding(8.dp)
            .fillMaxWidth()
    ){
        TextApp(text = stringResource(id = R.string.section_head_rate), style = interReg18)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn( state = rememberLazyListState(), modifier = Modifier.fillMaxSize()
        ){
            items(items = listBluetoothDev){ item->
                Row (modifier = Modifier.padding(vertical = 2.dp).clickable { uiState.onSelectDevice(item) }) {
                    TextApp(text = item.address, style = interReg14)
                    Spacer(modifier = Modifier.width(8.dp))
                    item.name?.let {  TextApp(text = checkPermission ( Manifest.permission.BLUETOOTH_SCAN, 31){ it }, style = interReg14) }
                }
            }
        }
    }
}