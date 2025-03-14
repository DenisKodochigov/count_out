package com.count_out.presentation.view_element.bottom_sheet

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.presentation.R
import com.count_out.presentation.modeles.Dimen.bsHeightWindowsListBle
import com.count_out.presentation.modeles.Dimen.bsSpacerBottomHeight
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.settings.SettingsEvent
import com.count_out.presentation.screens.settings.SettingsState
import com.count_out.presentation.view_element.ModalBottomSheetApp
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.icons.AnimateIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetBle(dataState: SettingsState, action: Action) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismissBLEScan(dataState) },
        modifier = Modifier.padding(horizontal = 12.dp),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetBleContent(dataState, action) }
    )
}
@Composable fun BottomSheetBleContent(dataState: SettingsState, action: Action) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(bsHeightWindowsListBle)
            .padding(4.dp),
        content = { SettingsBluetooth(dataState, action) })
    Spacer(modifier = Modifier.height(bsSpacerBottomHeight))
}
@SuppressLint("MissingPermission")
@Composable fun SettingsBluetooth(dataState: SettingsState, action: Action){
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(30.dp)) {
        TextApp(text = stringResource(id = R.string.section_heart_rate), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        AnimateIcon(animate = dataState.scannedBle)
    }
    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
            .border(width = 1.dp, color = colorScheme.onTertiaryContainer, shape = MaterialTheme.shapes.small)
    ) {
        items(items = dataState.devicesUI) { item ->
            Spacer(modifier = Modifier.height(0.dp))
            Row(modifier = Modifier
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    dataState.showBottomSheetBLE.value = false
                    action.ex(SettingsEvent.SelectDevice(item))
                }) {
                TextApp(text = item.address, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.name.ifEmpty { stringResource(id = R.string.no_name)}, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}