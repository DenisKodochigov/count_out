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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
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
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.icons.IconRun
import com.example.count_out.ui.view_components.icons.IconSingle
import com.example.count_out.ui.view_components.icons.IconSubscribe

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
@Composable fun TrainingsScreenLayout( uiState: TrainingsScreenState
){
    Column(
        modifier = Modifier.fillMaxSize()) {
        TrainingList(uiState, modifier = Modifier.weight(1f))
        DownPlace(uiState)
    }
}



@Composable fun TrainingList(uiState: TrainingsScreenState, modifier: Modifier = Modifier) {

    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(horizontal = Dimen.paddingAppHor, vertical = 4.dp),
        modifier = modifier.testTag("1").animateContentSize()
    ){
        items( items = uiState.trainings, key = { it.idTraining })
        { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            ItemSwipe(
                frontView = { TrainingCard( item, uiState, modifier = Modifier.animateItem()) },
                actionDragLeft = { uiState.onDeleteTraining( item.idTraining )},
                actionDragRight = { uiState.editTraining(item) },
            )
            Spacer(modifier = Modifier.height(Dimen.width4))
        }
        item { ItemLast( uiState = uiState ) }
    }
}
@Composable fun TrainingCard(item: Training, uiState: TrainingsScreenState, modifier: Modifier) {
    Card(elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
        ){
            Spacer(modifier = Modifier.width(12.dp))
            IconRunTraining(idTraining = item.idTraining, uiState = uiState)
            Spacer(modifier = Modifier.width(16.dp))
            TrainingInformation(item = item, uiState = uiState, modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(Dimen.width6))
            IconEnd(item = item, uiState = uiState)
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}
@Composable fun ItemLast( uiState: TrainingsScreenState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        IconAddTraining( uiState = uiState)
    }
}
@Composable fun IconRunTraining(idTraining: Long, uiState: TrainingsScreenState){
    IconSubscribe(text = "Write activity", icon = Icons.Default.PlayCircleOutline,
        onSelected = { uiState.onStartWorkout(idTraining)})
}
@Composable fun IconAddTraining(uiState: TrainingsScreenState){
    IconSubscribe(text = "Add plan", icon = Icons.Default.AddCircleOutline,
        onSelected = { uiState.onAddTraining() })
}
@Composable fun IconEnd(item: Training, uiState: TrainingsScreenState, modifier: Modifier = Modifier){
    IconButton(
        onClick = {
            uiState.onCopyTraining(item.idTraining)
                  }, modifier = modifier) {
        IconSingle(image = Icons.Default.CopyAll) }
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
    Row(modifier = Modifier.fillMaxWidth(),) {
        IconRun(onClick = {})
        Spacer(modifier = Modifier.width(16.dp))
        IconAddTraining( uiState = uiState )
    }
}
