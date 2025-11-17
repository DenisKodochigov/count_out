package com.count_out.presentation.screens.plans

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.enums.TypeBS
import com.count_out.domain.entity.lg
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.plans.model.PlansEvent
import com.count_out.presentation.screens.plans.model.PlansState
import com.count_out.presentation.screens.plans.model.PlansViewModel
import com.count_out.presentation.screens.plans.part.PartContent
import com.count_out.presentation.screens.plans.ring_exercise.getItemByIndex
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun PlansScreen(viewModel: PlansViewModel, navigateEvent: NavigateEvent) {
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenExecuteWorkout = { navigateEvent.goToScreenExecuteWorkout() }
            dataState.launcherBS.execute(dataState)
            PlansScreenLayout(dataState) }
    }
}
@Composable fun PlansScreenLayout(dataState: PlansState) {
    PlansCarcass(
        planList = dataState.plans,
        topBar = { TopBarApp(text = stringResource(R.string.plans_workout), selected = false, onClickText = {})},
        startIcon = { modifier, plan-> IconRun( modifier, dataState, plan )},
        actionItem = { plan-> IconsGroup( dataState, plan)},
        namePlan = { name-> TextApp(text = name, style = MaterialTheme.typography.titleLarge) },
        initSelecting = { plan-> SelectingInit(dataState, plan) },
        getCollaps = { plan-> getCollapsing(dataState,plan)},
        setCollaps = { plan-> setCollapsing(dataState,plan)},
        onChangeSequence = { showChangeOrder(dataState,Plan.EMPTY, dataState.plans)},
        listPart = { plan-> plan.parts.forEach { PartContent(dataState, it) } },
        infoPlan = { amount-> TextApp(style = MaterialTheme.typography.bodyLarge,
            text = pluralStringResource(
                R.plurals.exercise, amount, amount).replaceFirstChar { it.uppercase() },
        )}
    )
}
@Composable fun SelectingInit(dataState: PlansState, plan: Plan){
    LaunchedEffect(dataState.selecting.exercises.isEmpty()) {
        dataState.event(PlansEvent.SetSelecting(dataState.selecting.copy(item = plan)))
    }
}
@Composable fun IconRun(modifier: Modifier = Modifier, dataState: PlansState, plan: Plan) {
    Box(modifier = modifier.padding(start = 4.dp, end = 12.dp)) {
    IconQ.Play(onClick = {
        dataState.event(PlansEvent.RunPlan(plan))
        dataState.goToScreenExecuteWorkout()
    })}
}
@Composable fun IconsGroup(dataState: PlansState, plan: Plan) {
    IconsGroup(
        onCopy = {dataState.event(PlansEvent.CopyPlan(plan))},
        onDelete = { dataState.event(PlansEvent.DelPlan(plan))})
}
fun showSpeech(dataState: PlansState, item: Domain){
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Speech,item)))
}
fun showActivities(dataState: PlansState, owner: Domain, listActivity: List<Domain>){
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Activity, owner, listActivity)))
}
fun showChangeOrder(dataState: PlansState, type: Domain, list: List<Domain> = emptyList(), idOwner: Long = 0){
    lg("PlansScreen  size:${list.size}")
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Order,type,list,idOwner)))
}
fun setCollapsing(dataState: PlansState, item: Domain) {
    dataState.event(PlansEvent.SetCollapsing(dataState.collapsing.copy(item = item))) }
fun getCollapsing(dataState: PlansState, item: Domain): Boolean {
    return when(item) {
        is Plan -> dataState.collapsing.plans.find { it == item.idPlan } != null
        is Part -> dataState.collapsing.parts.find { it == item.idPart } != null
        is Ring -> dataState.collapsing.rings.find { it == item.idRing } != null
        is Exercise -> dataState.collapsing.exercises.find { it == item.idExercise } != null
        else -> false
    }
}
fun setSelecting(dataState: PlansState, ind: Int, list: List<Domain>): Int {
    val listId = list.map { listItem->
        LongDm(
            when (listItem) {
                is Exercise -> listItem.idExercise
                is Set -> listItem.idSet
                else -> 0
            }
        )
    }
    dataState.event(
        PlansEvent.SetSelecting(
            dataState.selecting.copy(item = getItemByIndex(ind,list), listOwner = listId)))
    return ind
}
fun getSelecting(dataState: PlansState, item: Domain): Boolean {
    return when(item) {
        is Set -> dataState.selecting.sets.find { it == item.idSet } != null
        is Part -> dataState.selecting.parts.find { it == item.idPart } != null
        is Ring -> dataState.selecting.rings.find { it == item.idRing } != null
        is Exercise ->dataState.selecting.exercises.find { it == item.idExercise } != null
        else -> false
    }
}
