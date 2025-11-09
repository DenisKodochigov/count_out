package com.count_out.presentation.screens.plan

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.enums.TypeBS
import com.count_out.domain.entity.supportive.NameId
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plan.part.PartContent
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.icons.IconsGroup

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun PlanScreen(viewModel: PlanViewModel, navigateEvent: NavigateEvent){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenPlans = { navigateEvent.goToScreenPlans() }
            dataState.launcherBS.execute(dataState)
            PlanScreenLayout(dataState)
        }
    }
}
@Composable fun PlanScreenLayout(dataState: PlanState) {
    CarcassPlanScreen(
        namePlanField = { NamePlanField(dataState) },
        listPlans = { ListPlan(dataState) },
        iconsActionPlan = { IconsActionPlan(dataState) }
    )
}
@Composable fun IconsActionPlan(dataState: PlanState){
    IconsGroup(
        onSpeech = { dataState.plan?.let {showSpeech(dataState,it)} },
        onDelete = {
            dataState.plan?.let { dataState.event(PlanEvent.DelPlan(it))}
            dataState.goToScreenPlans()
        }
    )
}
@Composable fun NamePlanField(dataState: PlanState){
    val enteredName: MutableState<String> = remember { mutableStateOf(dataState.plan?.name ?: "") }
    if (dataState.plan?.idPlan == 0L) return
    TextFieldApp(
        modifier = Modifier.padding(start = 14.dp),
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
}
@Composable fun ListPlan(dataState: PlanState){
    dataState.plan?.parts?.forEach { part ->
        Spacer(modifier = Modifier.height(8.dp))
        PartContent(dataState = dataState, part = part)
    }
}
fun setCollapsing(dataState: PlanState, item: Domain) {
    dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = item))) }
fun getCollapsing(dataState: PlanState, item: Domain): Boolean {
    return when(item) {
        is Part -> dataState.collapsing.parts.find { it == item.idPart } != null
        is Ring->dataState.collapsing.rings.find { it == item.idRing } != null
        else -> false
    }
}
fun setSelecting(dataState: PlanState, item: Domain, list: List<Domain>) {
    val listId = list.map { listItem->
        LongDm(when(listItem){
                is Exercise -> listItem.idExercise
                is Set -> listItem.idSet
                else-> 0
            }
        )
    }
    dataState.event(PlanEvent.SetSelecting(
        dataState.selecting.copy(item = item, listOwner = listId)))
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
fun showSpeech(dataState: PlanState, item: Domain){
    dataState.event(PlanEvent.Launcher(LauncherBSp().type(TypeBS.Speech).element(listOf(item))))
}
fun showActivities(dataState: PlanState, item: Domain){
    dataState.event(PlanEvent.Launcher(LauncherBSp().type(TypeBS.Activity).element(listOf(item))))
}
fun showChangeOrder(dataState: PlanState, item: Domain){
    dataState.event(PlanEvent.Launcher(LauncherBSp().type(TypeBS.Order).owner(item).element(listOf(item))))
}
