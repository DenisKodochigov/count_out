package com.count_out.presentation.screens.plan

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.part.Part
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.icons.IconsGroup

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun PlanScreen(viewModel: PlanViewModel, navigateEvent: NavigateEvent){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            ShowBottomSheetSpeech(dataState, dataState.showBS.plan,
                R.string.training, dataState.plan)
            PlanScreenLayout(dataState)
        }
    }
}
@Composable fun PlanScreenLayout(dataState: PlanState){
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
        NamePlan(dataState = dataState)
        dataState.plan?.parts?.forEach { part ->
            Spacer(modifier = Modifier.height(Dimen.width8))
            Part(dataState = dataState, part = part)
        }
    }
}
@Composable fun NamePlan(dataState: PlanState) {
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
                dataState.plan?.let { pl->
                    dataState.event(
                        PlanEvent.UpdatePlanName(NameId(enteredName.value,pl.idPlan)))
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = {
                dataState.item = dataState.plan
                dataState.event(ShowBS(dataState.showBS.copy(domain = dataState.plan))) },
            onClickDelete = {
                dataState.plan?.let { dataState.event(PlanEvent.DelPlan(dataState.plan))}
                dataState.plan?.let { dataState.event(PlanEvent.BackScreen)}
            }
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}
fun setCollapsing(dataState: PlanState, item: Domain) {
    dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = item)))
}
fun getCollapsing(dataState: PlanState, item: Domain): Boolean {
    return when(item) {
        is Part -> dataState.collapsing.parts.find { it == item.idPart } != null
        is Ring->dataState.collapsing.rings.find { it == item.idRing } != null
        else -> false
    }
}

fun setSelecting(dataState: PlanState, item: Domain, list: List<Domain>) {
    dataState.event(PlanEvent.SetSelecting(
        dataState.selecting.copy(item = item, listOwner = list)))
}
fun getSelecting(dataState: PlanState, item: Domain): Boolean {
    return when(item) {
        is Part -> dataState.selecting.parts.find { it == item.idPart } != null
        is Ring->dataState.selecting.rings.find { it == item.idRing } != null
        is Exercise ->dataState.selecting.exercises.find { it == item.idExercise } != null
        is Set ->dataState.selecting.sets.find { it == item.idSet } != null
        else -> false
    }
}