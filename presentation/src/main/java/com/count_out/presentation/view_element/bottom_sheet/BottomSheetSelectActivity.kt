package com.count_out.presentation.view_element.bottom_sheet

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.view_element.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectActivity(dataState: TrainingState)
{
    Log.d("KDS", "BottomSheetSelectActivity ${dataState.item}")
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)

    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismissSpeech()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = MaterialTheme.shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent(dataState) }
    )
}

@Composable fun BottomSheetSelectActivityContent(dataState: TrainingState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ){
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        LazyActivity(dataState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@Composable fun LazyActivity(dataState: TrainingState){
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ){
        items(items = dataState.activities) {item ->
            ActivityInfo(
                activity = remember{ mutableStateOf(ActivityImplP(item))},
                onSelect = { dataState.exercise?.let { dataState.onConfirmation(it, item)}},
            )
        }
    }
}
