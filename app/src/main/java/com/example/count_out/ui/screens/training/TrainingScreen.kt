package com.example.count_out.ui.screens.training

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.Work
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.TextApp

@SuppressLint("UnrememberedMutableState")
@Composable fun TrainingScreen(trainingId: Long, onClickExercise: (Long) -> Unit,
){
    val viewModel: TrainingViewModel = hiltViewModel()
    viewModel.getTraining( trainingId )
    TrainingScreenCreateView( onClickExercise = onClickExercise, viewModel = viewModel)
}
@Composable fun TrainingScreenCreateView(
    onClickExercise: (Long) -> Unit, viewModel: TrainingViewModel
){
    val uiState by viewModel.trainingScreenState.collectAsState()
    uiState.onClickExercise = remember {{id -> onClickExercise(id)}}
    TrainingScreenLayout(uiState = uiState)
}
@Composable fun TrainingScreenLayout( uiState: TrainingScreenState
){
    Column(modifier = Modifier.fillMaxHeight(),
    ){
        Round(uiState = uiState, typeWork = Work.UP)
        Round(uiState = uiState, typeWork = Work.OUT)
        Round(uiState = uiState, typeWork = Work.DOWN)
    }
}

@Composable fun Round(uiState: TrainingScreenState, typeWork: Work){
    Card (
        elevation = elevationTraining(),
        shape = MaterialTheme.shapes.extraSmall
    ){
        Column {
            Row1(uiState = uiState, typeWork)
            Row2(uiState = uiState, typeWork)
            Row3(uiState = uiState, typeWork)
        }
    }

}
@Composable fun Row1(uiState: TrainingScreenState, typeWork: Work){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        TextApp(text = stringResource(id = typeWork.strId), style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { uiState.onSpeechRound(uiState.trainingId) }) {
            Icon(painter = painterResource(id = R.drawable.ic_ecv), contentDescription ="")
        }
        IconButton( onClick = { setCollapsing(uiState, typeWork) }) {
            Icon(painter = painterResource(id = getIconCollapsing(uiState, typeWork)),
                contentDescription ="")
        }
    }
}

fun setCollapsing(uiState: TrainingScreenState, typeWork: Work){
    when(typeWork){
        Work.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
        Work.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
        Work.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
    }
}

fun getIconCollapsing(uiState: TrainingScreenState, typeWork: Work): Int{
    return when(typeWork){
        Work.UP -> getIcon(uiState.workUpCollapsing.value)
        Work.OUT -> getIcon(uiState.workOutCollapsing.value)
        Work.DOWN -> getIcon(uiState.workDownCollapsing.value)
    }
}
fun getIcon(collapsing: Boolean): Int = if (collapsing) R.drawable.ic_wrap1 else R.drawable.ic_wrap

@Composable fun Row2(uiState: TrainingScreenState, typeWork: Work){
    val txtDuration = stringResource(id = R.string.duration) + ": " +
            uiState.durationRound.value.toString()
    TextApp(text = txtDuration, style = interLight12)
}
@Composable fun Row3(uiState: TrainingScreenState, typeWork: Work){
    if (getRoundCollapsing(uiState, typeWork)){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
        {
            val amountExercise = stringResource(id = R.string.activitiyes) +
                    uiState.amountExerciseRounds.value.toString()
            TextApp(text = amountExercise, style = interLight12)
            PoleAddExercise(uiState = uiState, typeWork = typeWork)
        }
    } else {
        Column {
            val amountExercise = stringResource(id = R.string.activitiyes) +
                    uiState.amountExerciseRounds.value.toString()
            TextApp(text = amountExercise, style = interLight12)
            LazyExercise(uiState = uiState, typeWork = typeWork)
            PoleAddExercise(uiState = uiState, typeWork = typeWork)
        }
    }
}
fun getRoundCollapsing(uiState: TrainingScreenState, typeWork: Work): Boolean{
    return when(typeWork){
        Work.UP -> uiState.workUpCollapsing.value
        Work.OUT -> uiState.workOutCollapsing.value
        Work.DOWN -> uiState.workDownCollapsing.value
    }
}
@Composable fun PoleAddExercise(uiState: TrainingScreenState, typeWork: Work){
    Row(modifier = Modifier.clickable { uiState.onAddExercise }) {
        TextApp(text = stringResource(id = R.string.add_activity), style = interThin12)
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
    }
}

@Composable fun LazyExercise(uiState: TrainingScreenState, typeWork: Work){
    Row(modifier = Modifier.clickable { uiState.onAddExercise }) {
//        TextApp(text = stringResource(id = R.string.add_activity), style = interThin12)
//        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrainingLazyColumn(uiState: TrainingScreenState,
){
    Spacer(modifier = Modifier.height(2.dp))
    LazyList(uiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun LazyList(uiState: TrainingScreenState)
{
    val listState = rememberLazyListState()
    val listItems = uiState.training.value
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.testTag("1")
    )
    {
//        items( items = listItems, key = { it.idWorkout })
//        { item ->
//            ItemSwipe(
//                frontView = {
//                    RowLazy(item, uiState, modifier = Modifier.animateItemPlacement()) },
//                actionDragLeft = { uiState.deleteWorkout( item.idWorkout )},
//                actionDragRight = { uiState.editWorkout(item) },
//            )
//        }
    }
}
@Composable fun RowLazy(item: Workout, uiState: TrainingScreenState, modifier: Modifier){
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
        IconStart(item = item, uiState = uiState)
        Spacer(modifier = Modifier.width(Dimen.width8))
        NameWorkout(item = item, uiState = uiState )
        Spacer(modifier = Modifier
            .width(Dimen.width8)
            .weight(1f))
        IconEnd(item = item, uiState = uiState)
    }
}
@Composable fun IconStart(item: Workout, uiState: TrainingScreenState, modifier: Modifier = Modifier){
//    IconButton(onClick = { uiState.onSelect(item)}) {
//        Icon(imageVector = Icons.Default.CheckCircleOutline, contentDescription = "")}
}
@Composable fun IconEnd(item: Workout, uiState: TrainingScreenState, modifier: Modifier = Modifier){
//    IconButton(onClick = { uiState.onOtherAction(item)}) {
//        Icon(imageVector = Icons.Default.BlurOn, contentDescription = "")}
}
@Composable fun NameWorkout(item: Workout, uiState: TrainingScreenState,){
    TextApp(
        text = item.name,
        style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),)
//        modifier = Modifier.clickable { uiState.onSelectItem(item) })
}