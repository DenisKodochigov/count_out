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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Parameter
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.SpeechKit
import com.count_out.presentation.R
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.view_element.ModalBottomSheetApp

@Composable fun ShowBottomSheetSelectActivity(dataState: PlanState, item: Exercise){
    if (dataState.showBS.activity && dataState.item == item){
        dataState.nameSection = stringResource(id = R.string.list_activity)
        dataState.onDismiss =
            { dataState.event(ShowBS(dataState.showBS.copy(element = item.activity))) }
        dataState.onConfirmation = { exercise, activity ->
            dataState.event(
                PlanEvent.UpdateExercise(
                    object: Exercise{
                        override val idExercise: Long = (exercise as Exercise).idExercise
                        override val ringId: Long = (exercise as Exercise).ringId
                        override val idView: Int = (exercise as Exercise).idView
                        override val activity: Activity? = (activity as Activity)
                        override val activityId: Long= (activity as Activity).idActivity
                        override val speechKit: SpeechKit = (exercise as Exercise).speechKit
                        override val sets: List<Set> = (exercise as Exercise).sets
                        override val amountSet: Int = (exercise as Exercise).amountSet
                        override val duration: Parameter = (exercise as Exercise).duration
                    }))
            dataState.event(ShowBS(dataState.showBS.copy(element = item.activity)))
        }
        BottomSheetSelectActivity(dataState)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectActivity(dataState: PlanState)
{
    Log.d("KDS", "BottomSheetSelectActivity ${dataState.item}")
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, confirmValueChange = { true },)

    ModalBottomSheetApp(
        onDismissRequest = { dataState.onDismiss()},
        modifier = Modifier.padding(horizontal = Dimen.bsPaddingHor1),
        shape = MaterialTheme.shapes.small,
        sheetState = sheetState,
        content = { BottomSheetSelectActivityContent(dataState) }
    )
}

@Composable fun BottomSheetSelectActivityContent(dataState: PlanState) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(Dimen.bsItemPaddingHor)
    ){
        Spacer(Modifier.height(Dimen.bsSpacerHeight))
        LazyActivity(dataState)
        Spacer(Modifier.height(Dimen.bsSpacerBottomHeight))
    }
}

@Composable fun LazyActivity(dataState: PlanState){
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 0.dp, max = 250.dp)
    ){
        items(items = dataState.activities) {item ->
            ActivityTitle(
                activity = remember{ mutableStateOf(ActivityImplP(item))},
                onSelect = { dataState.item?.let { dataState.onConfirmation(it, item)}},
            )
        }
    }
}
