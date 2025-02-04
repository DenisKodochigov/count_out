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
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.shapes
import com.example.count_out.ui.view_components.ModalBottomSheetApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectActivity(uiState: TrainingScreenState)
{
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)

    ModalBottomSheetApp(
        onDismissRequest = {uiState.onDismissSelectActivity.invoke()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent(uiState) }
    )
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

@SuppressLint("UnrememberedMutableState")
@Composable fun LazyActivity(uiState: TrainingScreenState
){
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ) {
        items(items = uiState.activities) {item ->
            ActivityInfo(
                activity = mutableStateOf(item),
                onSelect = { uiState.onSelectActivity(uiState.exercise.idExercise, item.idActivity) },
                onChangeColor = {uiState.onSetColorActivity(item.idActivity, it ) }
            )
        }
    }
}
