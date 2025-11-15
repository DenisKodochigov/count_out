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
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun PlansScreen(viewModel: PlansViewModel, navigateEvent: NavigateEvent) {
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenExecuteWorkout = { navigateEvent.goToScreenExecuteWorkout() }
//            dataState.goToScreenPlan = { navigateEvent.goToScreenPlan(it) }
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
        initSelecting = {plan-> SelectingInit(dataState, plan) },
        getCollaps = { plan-> getCollapsing(dataState, plan)},
        setCollaps = { plan-> setCollapsing(dataState, plan)},
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
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Speech, item)))
}
fun showActivities(dataState: PlansState, owner: Domain, listActivity: List<Domain>){
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Activity, owner, listActivity)))
}
fun showChangeOrder(dataState: PlansState, owner: Domain, list: List<Domain>){
    dataState.event(PlansEvent.Launcher(LauncherBSp().init(TypeBS.Order, owner, list)))
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
fun setSelecting(dataState: PlansState, item: Domain, list: List<Domain>) {
    val listId = list.map { listItem->
        LongDm(
            when (listItem) {
                is Exercise -> listItem.idExercise
                is Set -> listItem.idSet
                else -> 0
            }
        )
    }
    dataState.event(PlansEvent.SetSelecting(dataState.selecting.copy(item = item, listOwner = listId)))
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


//    Column(modifier = Modifier.fillMaxSize()) {
//        TopBar()
//        Frame(contour = contourHor2, modifier = Modifier.weight(1f)) {
//            PlanList(dataState, modifier = Modifier.weight(1f)) }
//    }

//@Composable fun TopBar(){
//    TopBarApp(text = stringResource(R.string.plans_workout), selected = false, onClickText = {})
//}
//@Composable fun PlanInformation(dataState: PlansState, item: Plan, modifier: Modifier = Modifier) {
//    Column(modifier = modifier.clickable {
//        dataState.goToScreenPlan(item.idPlan)}) {
//        TextApp(text = item.name, style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(Dimen.height4))
//        TextApp(
//            text = pluralStringResource(
//                R.plurals.exercise, item.amountActivity, item.amountActivity)
//                .replaceFirstChar { it.uppercase() },
//            style = MaterialTheme.typography.bodyLarge
//        )
//    }
//}
//@Composable fun PlanList(dataState: PlansState, modifier: Modifier = Modifier) {
//    Spacer(modifier = Modifier.fillMaxWidth())
//    LazyColumn(
//        state = rememberLazyListState(),
//        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor),
//        modifier = modifier.testTag("1").animateContentSize()
//    ) {
//        items(dataState.plans) { item ->
//            Spacer(modifier = Modifier.height(Dimen.width4))
//            PlanCard(dataState, item, Modifier.animateItem())
//        }
//    }
//}
//
//@Composable fun PlanCard(dataState: PlansState, item: Plan, modifier: Modifier) {
//    Frame(contour = contourAll2) {
//        Row(
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
//        ) {
//            Spacer(modifier = Modifier.width(12.dp))
//            IconRun(dataState, plan = item )
//            Spacer(modifier = Modifier.width(16.dp))
//            PlanInformation(dataState, item, Modifier.weight(1f))
//            Spacer(modifier = Modifier.width(Dimen.width6))
//            IconsGroup(
//                onCopy = {dataState.event(PlansEvent.CopyPlan(item))},
//                onDelete = { dataState.event(PlansEvent.DelPlan(item))})
//            Spacer(modifier = Modifier.width(Dimen.width6))
//        }
//    }
//}