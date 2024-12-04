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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.entity.Const.contourAll2
import com.example.count_out.entity.Const.contourHor2
import com.example.count_out.entity.workout.Training
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.custom_view.IconQ

@Composable fun TrainingsScreen(
    onClickTraining: (Long) -> Unit,
    onStartWorkout: (Long) -> Unit,
    screen: ScreenDestination,
){
    val viewModel: TrainingsViewModel = hiltViewModel()
    TrainingsScreenCreateView(
        onClickTraining = onClickTraining,
        onStartWorkout = onStartWorkout,
        screen = screen,
        viewModel = viewModel,
    )
}
@Composable fun TrainingsScreenCreateView(
    onClickTraining: (Long) -> Unit,
    onStartWorkout: (Long) -> Unit,
    screen: ScreenDestination,
    viewModel: TrainingsViewModel,
){
    val uiState by viewModel.trainingsScreenState.collectAsState()
    uiState.onClickTraining = remember {{id -> onClickTraining(id)}}
    uiState.onSelectItem = remember {{ onClickTraining(it) }}
    uiState.onStartWorkout = remember {{ onStartWorkout(it) }}
    uiState.idImage = getIdImage(screen)
    TrainingsScreenLayout(uiState = uiState)
}
@Composable fun TrainingsScreenLayout( uiState: TrainingsScreenState){
    Column( modifier = Modifier.fillMaxSize()) {
        Frame( contour= contourHor2, modifier = Modifier.weight(1f)){
            TrainingList(uiState, modifier = Modifier.weight(1f)) }
        Spacer(modifier = Modifier.height(18.dp))
        DownPlace(uiState)
    }
}
@Composable fun TrainingList(uiState: TrainingsScreenState, modifier: Modifier = Modifier) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor),
        modifier = modifier.testTag("1").animateContentSize()
    ){
        items( items = uiState.trainings, key = { it.idTraining }) { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = { TrainingCard( item, uiState, modifier = Modifier.animateItem()) },
                actionDragLeft = { uiState.onDeleteTraining( item.idTraining )},
                actionDragRight = { uiState.editTraining(item) },
            )
        }
    }
}
@Composable fun TrainingCard(item: Training, uiState: TrainingsScreenState, modifier: Modifier) {
    Frame( contour = contourAll2){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
        ){
            Spacer(modifier = Modifier.width(12.dp))
            IconSelected(training = item, uiState = uiState)
            Spacer(modifier = Modifier.width(16.dp))
            TrainingInformation(item = item, uiState = uiState, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
            IconCopy(item = item, uiState = uiState)
            Spacer(modifier = Modifier.width(Dimen.width6))
        }
    }
}

@Composable fun IconSelected(training: Training, uiState: TrainingsScreenState){
    uiState.selectedId.value?.let { selectedId->
        if (training.idTraining == selectedId) IconQ.Mark(onClick = { uiState.selectedId.value = null})
        else IconQ.HorLine(onClick = { uiState.selectedId.value = training.idTraining })
    } ?: IconQ.HorLine(onClick = { uiState.selectedId.value = training.idTraining })
}
@Composable fun IconCopy(item: Training, uiState: TrainingsScreenState){
    IconQ.Copy(onClick = { uiState.onCopyTraining(item.idTraining) })
}
@Composable fun TrainingInformation(item: Training, uiState: TrainingsScreenState, modifier: Modifier = Modifier) {
    Column (modifier = modifier.clickable { uiState.onSelectItem(item.idTraining) }){
        TextApp( text = item.name, style = mTypography.titleLarge)
        Spacer(modifier = Modifier.height(Dimen.height4))
        TextApp( text = stringResource(id = R.string.exercise)+ ": " + item.amountActivity,
            style = mTypography.bodyLarge )
    }
}
//##################################################################################################
@Composable fun DownPlace(uiState: TrainingsScreenState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        IconQ.Play(onClick = {
            if (uiState.selectedId.value == null) uiState.onStartWorkout(1)
            else uiState.selectedId.value?.let { id-> uiState.onStartWorkout(id)}
        })
        Spacer(modifier = Modifier.width(32.dp))
        IconQ.Add(onClick = { uiState.onAddTraining() })
    }
}
