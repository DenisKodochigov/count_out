package com.count_out.presentation.view_element.bottom_sheet

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Exercise.Companion.copy
import com.count_out.presentation.R
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.BottomSheetInterface
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.view_element.ModalBottomSheetApp

@Composable fun ActivityBS(dataState: BottomSheetInterface, elements: List<Domain> = emptyList()){
    dataState.item = elements.firstNotNullOf{it}
    val item = dataState.item
    if (dataState.item != null && item is Exercise){
        dataState.nameSection = stringResource(id = R.string.list_activity)
        dataState.onDismiss = { dataState.event(PlanEvent.Launcher(LauncherBSp())) }
        dataState.onConfirmation = { activity ->
            if (activity is Activity) dataState.event(PlanEvent.UpdateExercise(
                item.copy( activity = activity, activityId = activity.idActivity )))
            dataState.event(PlanEvent.Launcher(LauncherBSp()))
        }
        BottomSheetSelectActivity1(dataState)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BottomSheetSelectActivity1(dataState: BottomSheetInterface) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)

    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismiss()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = MaterialTheme.shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent1(dataState) }
    )
}

@Composable fun BottomSheetSelectActivityContent1(dataState: BottomSheetInterface) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ){
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        LazyActivity1(dataState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}
@Composable fun LazyActivity1(dataState: BottomSheetInterface){
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ){
        items(items = dataState.activities) { item ->
            ActivityTitle(
                activity = remember{ mutableStateOf(ActivityImplP(item))},
                onSelect = { dataState.onConfirmation( item )},
            )
        }
    }
}
