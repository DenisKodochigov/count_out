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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.ui.screens.settings.SettingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.bsHeightWindowsListBle
import com.example.count_out.ui.theme.Dimen.bsSpacerBottomHeight
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.ModalBottomSheetApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.icons.AnimateIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetBle(uiState: SettingScreenState) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheetApp(
        onDismissRequest = { uiState.onDismissBLEScan(uiState) },
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetBleContent(uiState) }
    )
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
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(30.dp)) {
        TextApp(text = stringResource(id = R.string.section_heart_rate), style = mTypography.bodyLarge)
        Spacer(modifier = Modifier.weight(1f))
        AnimateIcon(animate = uiState.scannedBle)
    }
    Spacer(modifier = Modifier.height(12.dp))
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp)
            .border(width = 1.dp, color = colorScheme.onTertiaryContainer, shape = shapes.small)
    ) {
        items(items = uiState.devicesUI) { item ->
            Spacer(modifier = Modifier.height(0.dp))
            Row(modifier = Modifier
                .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                .clickable {
                    uiState.showBottomSheetBLE.value = false
                    uiState.onSelectDevice(item)
                }) {
                TextApp(text = item.address, style = mTypography.bodyMedium)
                Spacer(modifier = Modifier.width(12.dp))
                TextApp(text = item.name.ifEmpty { stringResource(id = R.string.no_name)}, style = mTypography.bodyMedium)
            }
        }
    }
}