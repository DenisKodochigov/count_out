package com.example.count_out.ui.bottom_sheet

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.count_out.ui.models.ActionWithActivityImpl
import com.example.count_out.ui.models.ActivityImpl
import com.example.count_out.ui.screens.prime.Action
import com.example.count_out.ui.screens.training.TrainingEvent
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.TrainingState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectActivity(dataState: TrainingState, action: Action)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)

    ModalBottomSheetApp(
        onDismissRequest = {dataState.onDismissSelectActivity.invoke()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent(dataState, action) }
    )
}

@Composable fun BottomSheetSelectActivityContent(dataState: TrainingState, action: Action) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ){
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        LazyActivity(dataState, action)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable fun LazyActivity(dataState: TrainingState, action: Action){
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ){
        items(items = dataState.activities) {item ->
            ActivityInfo(
                activity = mutableStateOf(item as ActivityImpl),
                onSelect = { action.ex( TrainingEvent.SelectActivity( ActionWithActivityImpl(
                    exerciseId = dataState.exercise.idExercise, activity = item)
                )) },
//                onChangeColor = {
//                    action.ex(TrainingEvent.SetColorActivity( item.copy(color = it)))}
            )
        }
    }
}
