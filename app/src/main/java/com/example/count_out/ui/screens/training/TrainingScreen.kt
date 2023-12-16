package com.example.count_out.ui.screens.training

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.Work
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.theme.shapeAddExercise
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp

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
@SuppressLint("UnrememberedMutableState")
@Composable fun TrainingScreenLayout(uiState: TrainingScreenState
){
    uiState.enteredName.value = uiState.training.value.name
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(horizontal = Dimen.paddingAppHor),
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(uiState.enteredName)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeWork = Work.UP)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeWork = Work.OUT)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeWork = Work.DOWN)
    }
}

@Composable fun Round(uiState: TrainingScreenState, typeWork: Work){
    Card (elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
        ){
            Column(modifier = Modifier.padding( start = 8.dp),
            ){
                Row1(uiState = uiState, typeWork)
                Row2(uiState = uiState, typeWork)
                Row3(uiState = uiState, typeWork)
            }
        }

    }
}
@Composable fun Row1(uiState: TrainingScreenState, typeWork: Work){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp))
    {
        TextApp(text = stringResource(id = typeWork.strId), style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.width(24.dp).height(24.dp),
            onClick = { uiState.onSpeechRound(uiState.trainingId) }) {
            Icon(painter = painterResource(id = R.drawable.ic_ecv), contentDescription ="",
                modifier = Modifier.padding(4.dp))
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.width(24.dp).height(24.dp),
            onClick = { setCollapsing(uiState, typeWork) }) {
            Icon(painter = painterResource(id = getIconCollapsing(uiState, typeWork)),
                contentDescription ="",
                modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable fun Row2(uiState: TrainingScreenState, typeWork: Work){
    val txtDuration = stringResource(id = R.string.duration) + ": " +
            uiState.durationRound.value.toString()
    TextApp(text = txtDuration, style = interLight12)
}
@Composable fun Row3( uiState: TrainingScreenState, typeWork: Work){
    if (!getRoundCollapsing( uiState, typeWork ) || amountExercise( uiState, typeWork ) == 0){
        Row3Wrap( uiState, typeWork )
    } else {
        Row3UnWrap( uiState, typeWork )
    }
}

@Composable fun Row3Wrap(uiState: TrainingScreenState, typeWork: Work){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        val amountExercise = stringResource(id = R.string.activitiyes) + ": " +
                amountExercise(uiState,typeWork)
        TextApp(text = amountExercise, style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        PoleAddExercise(uiState = uiState, typeWork = typeWork)
    }
}
@Composable fun Row3UnWrap(uiState: TrainingScreenState, typeWork: Work){
    Column {
        val amountExercise = stringResource(id = R.string.activitiyes) + ": "+
                amountExercise(uiState,typeWork)
        TextApp(text = amountExercise, style = interLight12)
        LazyExercise(uiState = uiState, typeWork = typeWork)
        PoleAddExercise(uiState = uiState, typeWork = typeWork)
    }
}

@Composable fun NameTraining(nameTraining: MutableState<String>)
{
    TextFieldApp(
        enterValue = nameTraining,
        textAlign = TextAlign.Start,
        typeKeyboard = TypeKeyboard.TEXT)
}


fun amountExercise(uiState: TrainingScreenState, typeWork: Work): Int
{
    return when(typeWork){
        Work.UP -> uiState.workUp.value?.exercise?.size ?: 0
        Work.OUT -> uiState.workOut.value?.exercise?.size ?: 0
        Work.DOWN -> uiState.workDown.value?.exercise?.size ?: 0
    }
}

fun setCollapsing(uiState: TrainingScreenState, typeWork: Work){
    if (amountExercise(uiState,typeWork) > 0) {
        when(typeWork){
            Work.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            Work.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            Work.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
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
fun getRoundCollapsing(uiState: TrainingScreenState, typeWork: Work): Boolean{
    return when(typeWork){
        Work.UP -> uiState.workUpCollapsing.value
        Work.OUT -> uiState.workOutCollapsing.value
        Work.DOWN -> uiState.workDownCollapsing.value
    }
}
@Composable fun PoleAddExercise(uiState: TrainingScreenState, typeWork: Work){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { uiState.onAddExercise }
            .background(color = MaterialTheme.colorScheme.secondary, shape = shapeAddExercise)
    ) {
        TextApp(
            text = stringResource(id = R.string.add_activity),
            style = interThin12,
            modifier = Modifier.padding(horizontal = 4.dp))
        Icon(painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "",
            modifier = Modifier.padding(4.dp).width(14.dp).height(14.dp))
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

@Preview( showBackground = true)
@Composable fun  TrainingScreenLayoutPreview(){
    TrainingScreenLayout(TrainingScreenState())
}
