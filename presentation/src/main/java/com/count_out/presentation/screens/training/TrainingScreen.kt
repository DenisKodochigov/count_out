package com.count_out.presentation.screens.training

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.screens.training.PlanEvent.ShowBS
import com.count_out.presentation.screens.training.round.Part
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun TrainingScreen(viewModel: PlanViewModel, trainingId: Long){
    LaunchedEffect(Unit) { viewModel.submitEvent(PlanEvent.Init(trainingId)) }
    TrainingScreenCreateView( viewModel = viewModel )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun TrainingScreenCreateView( viewModel: PlanViewModel){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            ShowBottomSheetSpeech(dataState, dataState.showBS.plan,
                R.string.training, dataState.plan)
            TrainingScreenLayout(dataState)
        }
    }
}
@Composable fun TrainingScreenLayout(dataState: PlanState){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.paddingAppHor)
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            },
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(dataState = dataState)
        dataState.plan?.parts?.forEach { part ->
            Spacer(modifier = Modifier.height(Dimen.width8))
            Part(dataState = dataState, part = part)
        }


    }
}
@Composable fun NameTraining(dataState: PlanState) {
    val enteredName: MutableState<String> = remember { mutableStateOf(dataState.plan?.name ?: "") }
    if (dataState.plan?.idPlan == 0L) return
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp, end = 4.dp))
    {
        TextFieldApp(
            modifier = Modifier.padding(start = 4.dp),
            edit = true,
            typeKeyboard = TypeKeyboard.TEXT,
            contentAlignment = Alignment.CenterStart,
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
            colorLine = MaterialTheme.colorScheme.outline,
            placeholder = enteredName.value,
            onChangeFocus = {
                enteredName.value = it
                dataState.plan?.let { tr->
                    dataState.event( PlanEvent.UpdatePlan(tr.apply { this.name = enteredName.value}))
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = {
                dataState.item = dataState.plan
                dataState.event(ShowBS(dataState.showBS.copy(element = dataState.plan))) },
            onClickDelete = {
                dataState.plan?.let { dataState.event(PlanEvent.DelPlan(dataState.plan))}
                dataState.plan?.let { dataState.event(PlanEvent.BackScreen)}
            }
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}
