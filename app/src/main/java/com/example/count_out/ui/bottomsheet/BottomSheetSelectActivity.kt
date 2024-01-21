package com.example.count_out.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.count_out.entity.Activity
import com.example.count_out.ui.dialog.ChangeColorSectionDialog
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.colorApp
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.view_components.TextApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectActivity(uiState: TrainingScreenState)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)
    ModalBottomSheet(
        onDismissRequest = {uiState.onDismissSelectActivity.invoke()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapesApp.small,
        containerColor = BottomSheetDefaults.ContainerColor,
        contentColor = contentColorFor(BottomAppBarDefaults.containerColor),
        tonalElevation = BottomSheetDefaults.Elevation,
        scrimColor = BottomSheetDefaults.ScrimColor,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        windowInsets = BottomSheetDefaults.windowInsets,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent(uiState) })
}

@Composable
fun BottomSheetSelectActivityContent(uiState: TrainingScreenState)
{
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.bsItemPaddingHor)
    ) {
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        LazyActivity(uiState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@Composable fun LazyActivity(uiState: TrainingScreenState
){
    val listState = rememberLazyListState()
    val activityChangeColor: MutableState<Activity?> = remember { mutableStateOf(null) }

    if (activityChangeColor.value != null) {
        ChangeColorSectionDialog(
            colorItem = activityChangeColor.value!!.color,
            onDismiss = { activityChangeColor.value = null},
            onConfirm = {
                uiState.onSetColorActivity(activityChangeColor.value!!.idActivity, it )
                activityChangeColor.value = null },)
    }
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ) {
        items(items = uiState.activities) {item ->
            LazyActivityContent(
                item = item,
                activityChangeColor = activityChangeColor,
                onSelect = { uiState.onSelectActivity(uiState.exercise.idExercise, item.idActivity) })
        }
    }
}
@Composable fun LazyActivityContent(
    item: Activity,
    activityChangeColor: MutableState<Activity?>,
    onSelect: () -> Unit
){
    Row( modifier = Modifier
        .padding(horizontal = 12.dp)
        .clickable { onSelect() }
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painter = painterResource(id = item.icon), contentDescription = null)
        Spacer(modifier = Modifier.padding(end= 12.dp))
        TextApp(text = item.name, style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = CircleShape)
            .border(
                width = 1.dp,
                color = colorApp.outline,
                shape = CircleShape
            )
            .clickable { activityChangeColor.value = item }
            .background(color = Color(item.color), shape = CircleShape))
    }
}