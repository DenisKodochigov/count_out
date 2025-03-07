package com.example.count_out.ui.screens.trainings

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.ui.screens.prime.Action
import com.example.count_out.ui.screens.prime.PrimeScreen
import com.example.count_out.entity.workout.Training
import com.example.count_out.R
import com.example.count_out.entity.Const.contourAll2
import com.example.count_out.entity.Const.contourHor2
import com.example.count_out.ui.navigation.NavigateEvent
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.custom_view.IconQ


@Composable
fun TrainingsScreen(navigateEvent: NavigateEvent) {
    val vm: TrainingsViewModel = hiltViewModel()
    vm.initNavigate(navigateEvent)
    LaunchedEffect(Unit) { vm.submitEvent(TrainingsEvent.Gets) }
    TrainingsScreenCreateView(vm)
}

@Composable
fun TrainingsScreenCreateView(viewModel: TrainingsViewModel) {
    val action = Action {viewModel.submitEvent(it) }
    viewModel.dataState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            TrainingsScreenLayout(dataState, action = action)
        }
    }
//    val uiState by viewModel.trainingsScreenState.collectAsState()
//    uiState.onClickTraining = remember {{id -> onClickTraining(id)}}
//    uiState.onSelectItem = remember {{ onClickTraining(it) }}
//    uiState.onStartWorkout = remember {{ onStartWorkout(it) }}
//    TrainingsScreenLayout(uiState = uiState)
}

@Composable
fun TrainingsScreenLayout(dataState: TrainingsState, action: Action) {
    Column(modifier = Modifier.fillMaxSize()) {
        Frame(contour = contourHor2, modifier = Modifier.weight(1f)) {
            TrainingList(dataState, action, modifier = Modifier.weight(1f)) }
        Spacer(modifier = Modifier.height(18.dp))
        DownPlace(dataState, action)
    }
}

@Composable
fun TrainingList(
    dataState: TrainingsState,
    action: Action,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor),
        modifier = modifier.testTag("1").animateContentSize()
    ) {
        items(dataState.trainings) { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = {
                    TrainingCard(
                        item, dataState = dataState, action = action,
                        modifier = Modifier.animateItem()
                    )
                },
                actionDragLeft = { action.ex(TrainingsEvent.Del(item)) },
                actionDragRight = { action.ex(TrainingsEvent.Edit(item.idTraining)) },
            )
        }
    }
}

@Composable
fun TrainingCard(
    item: Training,
    dataState: TrainingsState,
    action: Action,
    modifier: Modifier
) {
    Frame(contour = contourAll2) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            IconSelected(training = item, dataState = dataState)
            Spacer(modifier = Modifier.width(16.dp))
            TrainingInformation(item = item, action = action, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
            IconCopy(item = item, action = action)
            Spacer(modifier = Modifier.width(Dimen.width6))
        }
    }
}

@Composable
fun IconSelected(training: Training, dataState: TrainingsState) {
    dataState.selectedId.value?.let { selectedId ->
        if (training.idTraining == selectedId) IconQ.Mark(onClick = {
            dataState.selectedId.value = null
        })
        else IconQ.HorLine(onClick = { dataState.selectedId.value = training.idTraining })
    } ?: IconQ.HorLine(onClick = { dataState.selectedId.value = training.idTraining })
}

@Composable fun IconCopy(item: Training, action: Action) {
    IconQ.Copy(onClick = { action.ex(TrainingsEvent.Copy(item)) })
}

@Composable
fun TrainingInformation(
    item: Training,
    action: Action,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.clickable { action.ex(TrainingsEvent.Select(item))}) {
        TextApp(text = item.name, style = mTypography.titleLarge)
        Spacer(modifier = Modifier.height(Dimen.height4))
        TextApp(
            text = stringResource(id = R.string.exercise) + ": " + item.amountActivity,
            style = mTypography.bodyLarge
        )
    }
}
@Composable
fun DownPlace(dataState: TrainingsState, action: Action) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        IconQ.Play(
            onClick = {
                if (dataState.selectedId.value == null) action.ex(TrainingsEvent.Run(1))
                else dataState.selectedId.value?.let { id -> action.ex(TrainingsEvent.Run(id))}
            })
        Spacer(modifier = Modifier.width(32.dp))
        IconQ.Add(onClick = { action.ex(TrainingsEvent.Add ) })
    }
}
