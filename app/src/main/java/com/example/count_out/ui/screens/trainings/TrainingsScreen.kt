package com.example.count_out.ui.screens.trainings

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.entity.Training
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.IconSingle
import com.example.count_out.ui.view_components.ItemSwipe
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
//    lg("TrainingsScreenLayout")
    Column(
        modifier = Modifier.fillMaxSize(),
        content = { TrainingsLazyColumn( uiState = uiState) }
    )
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrainingsLazyColumn(uiState: TrainingsScreenState,
){
//    lg("TrainingsLazyColumn")
    LazyList(uiState)
    Spacer(modifier = Modifier.height(8.dp))
}
@OptIn(ExperimentalFoundationApi::class)
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
                frontView = { RowLazy(item, uiState, modifier = Modifier.animateItemPlacement()) },
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
            modifier = modifier.fillMaxWidth()
        ){
            IconStart(item = item, uiState = uiState)
            Spacer(modifier = Modifier.width(Dimen.width4))
            TrainingInformation(item = item, uiState = uiState, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width4))
            IconEnd(item = item, uiState = uiState)
        }
    }
}
@Composable fun IconStart(item: Training, uiState: TrainingsScreenState){
//    lg("IconStart")
    IconButton(onClick = { uiState.onStartWorkout(item.idTraining)}) {
        IconSingle(image = Icons.Default.PlayCircleOutline)}
}
@Composable fun IconEnd(item: Training, uiState: TrainingsScreenState){
    IconButton(onClick = { uiState.onCopyTraining(item.idTraining)}) {
        IconSingle(image = painterResource(R.drawable.ic_copy)) }
}
@Composable fun TrainingInformation(
    item: Training,
    uiState: TrainingsScreenState,
    modifier: Modifier = Modifier)
{
//    lg("TrainingInformation")
    Column (modifier = modifier.clickable { uiState.onSelectItem(item.idTraining) }){
        TextApp( text = item.name, style = interReg14)
        Spacer(modifier = Modifier.height(Dimen.height4))
        TextApp(
            text = stringResource(id = R.string.exercise)+ ": " + item.amountActivity,
            style = interLight12 )
    }
}

