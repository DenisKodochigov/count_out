package com.count_out.presentation.view_element.bottom_sheet

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.count_out.domain.entity.workout.Activity
//import com.count_out.presentation.models.ActivityImplP
//import com.count_out.presentation.screens.settings.SettingsEvent
//import com.count_out.presentation.screens.settings.SettingsState
//import com.count_out.presentation.view_element.ButtonConfirm
//import com.count_out.presentation.view_element.ModalBottomSheetApp
//
//@Composable fun ShowBottomSheetAddActivity(dataState: SettingsState, showBS: Boolean
//){
//    if (showBS) { BottomSheetAddActivity(dataState) }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable fun BottomSheetAddActivity(dataState: SettingsState) {
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true, confirmValueChange = { true },)
//    ModalBottomSheetApp(
//        onDismissRequest = { dataState.event(
//            SettingsEvent.ShowBS(dataState.showBS.copy(domain = ActivityImplP(0L)))) },
//        modifier = Modifier.padding(horizontal = 12.dp),
//        shape = MaterialTheme.shapes.small,
//        sheetState = sheetState,
//        content = { BottomSheetAddActivityContent(dataState) }
//    )
//}
//@Composable fun BottomSheetAddActivityContent(dataState: SettingsState) {
//    val activityNew = remember { mutableStateOf(
//        (dataState.item)?.let{ ActivityImplP(it as Activity)} ?: ActivityImplP(0L)) }
//    Column( horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp))
//    {
//        Spacer(Modifier.height(12.dp))
//        ActivityEdit(
//            activity = activityNew,
//            onChange = { activityNew.value = it },
//        )
//        Spacer(Modifier.height(12.dp))
//        ButtonConfirm( onConfirm = {
//            if (activityNew.value.idActivity > 0)
//                dataState.event(SettingsEvent.UpdateActivity(activityNew.value))
//            else dataState.event(SettingsEvent.AddActivity(activityNew.value))
//            dataState.event(SettingsEvent.ShowBS(dataState.showBS.copy(domain = activityNew.value)))} )
//        Spacer(Modifier.height(12.dp))
//    }
//}