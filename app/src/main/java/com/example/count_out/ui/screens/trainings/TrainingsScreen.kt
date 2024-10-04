package com.example.count_out.ui.screens.trainings

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.count_out.entity.workout.Training
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.Dimen.sizeIconLarge
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.typography
import com.example.count_out.ui.view_components.IconSingleLarge
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.NameScreen
import com.example.count_out.ui.view_components.TextApp

@Composable fun TrainingsScreen(
    onClickTraining: (Long) -> Unit,
    onStartWorkout: (Long) -> Unit,
    screen: ScreenDestination,
){
    val viewModel: TrainingsViewModel = hiltViewModel()
//    lg("TrainingsScreen")
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
    uiState.onDismiss = remember {{ uiState.triggerRunOnClickFAB.value = false }}
    uiState.onClickTraining = remember {{id -> onClickTraining(id)}}
    uiState.onSelectItem = remember {{ onClickTraining(it) }}
    uiState.onStartWorkout = remember {{ onStartWorkout(it) }}
    uiState.idImage = getIdImage(screen)
    screen.onClickFAB = { uiState.triggerRunOnClickFAB.value = true }

    if (uiState.triggerRunOnClickFAB.value) {
        uiState.triggerRunOnClickFAB.value = false
        uiState.onAddTraining()
    }
    TrainingsScreenLayout(uiState = uiState)
}
@Composable fun TrainingsScreenLayout( uiState: TrainingsScreenState
){
    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            NameScreen(id = R.string.list_trainings)
            TrainingsLazyColumn( uiState = uiState)}
    )
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrainingsLazyColumn(uiState: TrainingsScreenState,
){
    LazyList(uiState)
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable fun LazyList(uiState: TrainingsScreenState)
{
//    lg("LazyList")
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .testTag("1")
            .animateContentSize()
            .padding(horizontal = Dimen.paddingAppHor)
    ){
        items( items = uiState.trainings, key = { it.idTraining })
        { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = { RowLazy(item, uiState, modifier = Modifier.animateItem()) },
                actionDragLeft = { uiState.onDeleteTraining( item.idTraining )},
                actionDragRight = { uiState.editTraining(item) },
            )
            Spacer(modifier = Modifier.height(Dimen.width4))
        }
    }
}
@Composable fun RowLazy(item: Training, uiState: TrainingsScreenState, modifier: Modifier)
{
//    lg("RowLazy")
    Card(elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ){
            Spacer(modifier = Modifier.width(12.dp))
            IconStart(item = item, uiState = uiState, modifier = Modifier.size(sizeIconLarge))
            Spacer(modifier = Modifier.width(16.dp))
            TrainingInformation(item = item, uiState = uiState, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
            IconEnd(item = item, uiState = uiState)
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}
@Composable fun IconStart(item: Training, uiState: TrainingsScreenState, modifier: Modifier = Modifier){
    IconButton(onClick = { uiState.onStartWorkout(item.idTraining)}, modifier = modifier) {
        IconSingleLarge(image = Icons.Default.PlayCircleOutline)}
}
@Composable fun IconEnd(item: Training, uiState: TrainingsScreenState, modifier: Modifier = Modifier){
    IconButton(onClick = { uiState.onCopyTraining(item.idTraining)}, modifier = modifier) {
        IconSingleLarge(image = Icons.Default.CopyAll) }
}
@Composable fun TrainingInformation(
    item: Training,
    uiState: TrainingsScreenState,
    modifier: Modifier = Modifier)
{
//    lg("TrainingInformation")
    Column (modifier = modifier.clickable { uiState.onSelectItem(item.idTraining) }){
        TextApp( text = item.name, style = typography.titleLarge)
        Spacer(modifier = Modifier.height(Dimen.height4))
        TextApp(
            text = stringResource(id = R.string.exercise)+ ": " + item.amountActivity,
            style = typography.bodyLarge )
    }
}

