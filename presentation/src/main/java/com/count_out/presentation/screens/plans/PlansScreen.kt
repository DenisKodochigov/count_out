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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Training
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.Dimen.contourAll2
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.view_element.ItemSwipe
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.custom_view.IconQ
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable
fun PlansScreen(vm: PlansViewModel) {
    LaunchedEffect(Unit) { vm.submitEvent(PlansEvent.Gets) }
    PlansScreenCreateView(vm)
}

@Composable fun PlansScreenCreateView(viewModel: PlansViewModel) {
    val action = Action {viewModel.submitEvent(it) }
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            PlansScreenLayout(dataState, action = action) }
    }
}

@Composable fun PlansScreenLayout(dataState: PlansState, action: Action) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Frame(contour = contourHor2, modifier = Modifier.weight(1f)) {
            PlanList(dataState, action, modifier = Modifier.weight(1f)) }
    }
}

@Composable fun TopBar(){
    TopBarApp(text = stringResource(R.string.plans_workout), selected = false, onClickText = {})
}

@Composable fun PlanList(dataState: PlansState, action: Action, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.fillMaxWidth())
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor),
        modifier = modifier.testTag("1").animateContentSize()
    ) {
        items(dataState.trainings) { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = {
                    PlanCard(modifier = Modifier.animateItem(), item = item, action = action)},
                actionDragLeft = { action.ex(PlansEvent.Del(item)) },
                actionDragRight = { action.ex(PlansEvent.Edit(item.idTraining)) },
            )
        }
    }
}

@Composable fun PlanCard(item: Training, action: Action, modifier: Modifier) {
    Frame(contour = contourAll2) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            IconRun(training = item, action = action)
            Spacer(modifier = Modifier.width(16.dp))
            PlanInformation(item = item, action = action, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
//            IconCopy(training = item, action = action)
            IconsGroup(
                onClickCopy = {action.ex(PlansEvent.Copy(item))},
                onClickAddPlan = {action.ex(PlansEvent.Copy(TrainingImplP()))},
                onClickDelete = { action.ex(PlansEvent.Del(item))}
            )
            Spacer(modifier = Modifier.width(Dimen.width6))
        }
    }
}

@Composable fun IconRun(training: Training, action: Action) {
    IconQ.Play(onClick = { action.ex(PlansEvent.Run(training)) })
}

@Composable fun PlanInformation(item: Training, action: Action, modifier: Modifier = Modifier) {

    Column(modifier = modifier.clickable { action.ex(PlansEvent.Edit(item.idTraining))}) {
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
