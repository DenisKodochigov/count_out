package com.count_out.presentation.screens.plans

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.NavigateEvent
import com.count_out.domain.entity.workout.Plan
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.contourAll2
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.ItemSwipe
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable
fun PlansScreen(vm: PlansViewModel, navigateEvent: NavigateEvent) {
    PlansScreenCreateView(vm, navigateEvent)
}

@Composable fun PlansScreenCreateView(viewModel: PlansViewModel, navigateEvent: NavigateEvent) {
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.goToScreenExecuteWorkout = { navigateEvent.goToScreenExecuteWorkout() }
            dataState.goToScreenPlan = { navigateEvent.goToScreenPlan(it) }
            PlansScreenLayout(dataState) }
    }
}

@Composable fun PlansScreenLayout(dataState: PlansState) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Frame(contour = contourHor2, modifier = Modifier.weight(1f)) {
            PlanList(dataState, modifier = Modifier.weight(1f)) }
    }
}

@Composable fun TopBar(){
    TopBarApp(text = stringResource(R.string.plans_workout), selected = false, onClickText = {})
}

@Composable fun PlanList(dataState: PlansState, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.fillMaxWidth())
//    Log.d("KDS", "list ${dataState.trainings}")
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor),
        modifier = modifier.testTag("1").animateContentSize()
    ) {
        items(dataState.plans) { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = {
                    PlanCard(dataState, item, Modifier.animateItem())},
                actionDragLeft = { dataState.event(PlansEvent.Del(item)) },
                actionDragRight = { dataState.goToScreenPlan(item.idPlan) },
            )
        }
    }
}

@Composable fun PlanCard(dataState: PlansState, item: Plan, modifier: Modifier) {
    Frame(contour = contourAll2) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            IconRun(dataState, training = item )
            Spacer(modifier = Modifier.width(16.dp))
            PlanInformation(dataState, item, Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
//            IconCopy(training = item )
            IconsGroup(
                onClickCopy = {dataState.event(PlansEvent.Copy(item))},
                onClickAddPlan = {dataState.event(PlansEvent.Add)},
                onClickDelete = { dataState.event(PlansEvent.Del(item))}
            )
            Spacer(modifier = Modifier.width(Dimen.width6))
        }
    }
}

@Composable fun IconRun(dataState: PlansState, training: Plan) {
    IconQ.Play(onClick = {
        dataState.event(PlansEvent.Run(training))
        dataState.goToScreenExecuteWorkout()
    })
}

@Composable fun PlanInformation(dataState: PlansState, item: Plan, modifier: Modifier = Modifier) {

    Column(modifier = modifier.clickable { dataState.event(PlansEvent.Edit(item.idPlan))}) {
        TextApp(text = item.name, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(Dimen.height4))
        TextApp(
            text = pluralStringResource(
                R.plurals.exercise, item.amountActivity, item.amountActivity)
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
