package com.example.count_out.ui.screens.exercise

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Set
import com.example.count_out.ui.bottomsheet.BottomSheetSelectActivity
import com.example.count_out.ui.bottomsheet.BottomSheetSpeech
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.theme.shapeAddExercise
import com.example.count_out.ui.view_components.GroupIcons4
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextStringAndField

@SuppressLint("UnrememberedMutableState")
@Composable fun ExerciseScreen(roundId: Long, exerciseId:Long,
){
    val viewModel: ExerciseViewModel = hiltViewModel()
    viewModel.getExercise(roundId, exerciseId)
    ExerciseScreenCreateView( viewModel = viewModel)
}
@Composable fun ExerciseScreenCreateView( viewModel: ExerciseViewModel
){
    val uiState = viewModel.exerciseScreenState.collectAsState()
    uiState.value.onDismissSelectActivity = { uiState.value.showBottomSheetSelectActivity.value = false }
    uiState.value.onDismissSpeech = { uiState.value.showBottomSheetSpeech.value = false}
    uiState.value.nameSection = stringResource(id = R.string.exercise)

    ShowBottomSheet(uiState.value)
    ExerciseScreenLayout(uiState = uiState.value)
}
@Composable fun ShowBottomSheet(uiState: ExerciseScreenState) {
    if (uiState.showBottomSheetSelectActivity.value) BottomSheetSelectActivity(uiState)
    if (uiState.showBottomSheetSpeech.value) {
        uiState.item = uiState.exercise
        BottomSheetSpeech(uiState)
    }
}
@Composable
fun ExerciseScreenLayout(uiState: ExerciseScreenState
) {
    Column(
        Modifier
            .padding(Dimen.paddingAppHor)
            .fillMaxHeight(),
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTrainingRound(uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
        ExerciseContent(uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
    }
}
@Composable fun NameTrainingRound(uiState: ExerciseScreenState
){
    Row(verticalAlignment = Alignment.CenterVertically){
        TextApp(
            text = uiState.nameTraining,
            textAlign = TextAlign.Start,
            style = interBold16,
            modifier = Modifier.padding(start = 12.dp)
        )
        TextApp(
            text = "${uiState.nameRound}:${uiState.roundId}  ; Exercise: ${uiState.exercise.idExercise}",
            textAlign = TextAlign.Start,
            style = interReg14,
            modifier = Modifier.padding(start = 24.dp)
        )
    }

}
@Composable fun ExerciseContent(uiState: ExerciseScreenState
){
    Card (
        elevation = elevationTraining(),
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary,
        )
    ){
        Column (modifier = Modifier.fillMaxWidth().padding(6.dp))
        {
            SelectActivity(uiState)
            LazySets(uiState)
        }
        RowAddSet(uiState)
    }
}
@Composable fun SelectActivity(uiState: ExerciseScreenState
){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
    ){
        IconSelectActivity(uiState)
        Spacer(modifier = Modifier.width(12.dp))
        TextActivity(uiState)
        Spacer(modifier = Modifier.weight(1f))
        IconSpeech(uiState)
    }
}
@Composable fun IconSelectActivity(uiState: ExerciseScreenState
){
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),
        onClick = { uiState.showBottomSheetSelectActivity.value = true })
    { Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "") }
}
@Composable fun TextActivity(uiState: ExerciseScreenState
){
    TextApp(text = uiState.exercise.activity.name, style = interReg14)
}
@Composable fun IconSpeech(uiState: ExerciseScreenState
){
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),
        onClick = { uiState.showBottomSheetSpeech.value = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ecv), contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}
@Composable fun LazySets(uiState: ExerciseScreenState
){
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .testTag("1")
            .padding(horizontal = 3.dp)
    ){
        items( items = uiState.exercise.sets, key = { it.idSet })
        { set ->
            Card (
                elevation = elevationTraining(),
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                ),
                content = {
                    Spacer(modifier = Modifier.height(Dimen.width4))
                    NameSet(uiState,set)
                    AdditionalInformation(uiState,set)
                    Spacer(modifier = Modifier.height(Dimen.width4))
                }
            )
        }
    }
}
@Composable fun NameSet(uiState: ExerciseScreenState, set: Set)
{
    Row{
        TextApp(
            text = "${set.idSet}: ${set.name}",
            style = interReg14,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
        GroupIcons4(
            onCopy = { /*TODO*/ },
            onSpeech = { /*TODO*/ },
            onDel = { /*TODO*/ },
            onCollapsing = { setCollapsing(uiState, set) },
            wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null
        )
    }
}
fun setCollapsing(uiState: ExerciseScreenState,  set: Set): Boolean
{
    val listCollapsingSet = uiState.listCollapsingSet.value.toMutableList()
    val itemList = listCollapsingSet.find { it == set.idSet }
    return if ( itemList != null) {
        listCollapsingSet.remove(itemList)
        uiState.listCollapsingSet.value = listCollapsingSet
        false
    } else {
        listCollapsingSet.add(set.idSet)
        uiState.listCollapsingSet.value = listCollapsingSet
        true
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun AdditionalInformation(uiState: ExerciseScreenState, set: Set)
{
    val enteredDuration: MutableState<String> = remember { mutableStateOf("${set.duration.toInt()}") }
    val enteredWeight: MutableState<String> = remember { mutableStateOf("${set.weight}") }
    val enteredDistance: MutableState<String> = remember { mutableStateOf("${set.distance}") }
    val enteredIntensity: MutableState<String> = remember { mutableStateOf(set.intensity) }
    val enteredReps: MutableState<String> = remember { mutableStateOf("${set.reps}") }
    val enteredTimeRest: MutableState<String> = remember { mutableStateOf("${set.timeRest}") }
    val enteredIntervalDown: MutableState<String> = remember { mutableStateOf("${set.intervalDown}") }
    val enteredIntervalReps: MutableState<String> = remember { mutableStateOf("${set.intervalReps}") }
    val enteredName: MutableState<String> = remember { mutableStateOf(set.name) }
    val enteredGroupCount: MutableState<String> = remember { mutableStateOf(set.groupCount) }
    val visibleLazy = uiState.listCollapsingSet.value.find { it == set.idSet } != null
    val focusRequester = remember { FocusRequester() }

    Column(modifier = Modifier
        .testTag("1")
        .focusRequester(focusRequester)
        .onFocusChanged {
            if (visibleLazy) uiState.onChangeSet(
                SetDB(
                    idSet = set.idSet,
                    name = enteredName.value,
                    exerciseId = set.exerciseId,
                    speechId = set.speechId,
                    speech = set.speech,
                    weight = if (enteredWeight.value.isNotEmpty()) enteredWeight.value.toInt() else 0,
                    intensity = enteredIntensity.value,
                    distance = if (enteredWeight.value.isNotEmpty()) enteredDistance.value.toDouble() else 0.0,
                    duration = if (enteredWeight.value.isNotEmpty()) enteredDuration.value.toInt() else 0,
                    reps = if (enteredWeight.value.isNotEmpty()) enteredReps.value.toInt() else 0,
                    intervalReps = if (enteredWeight.value.isNotEmpty()) enteredIntervalReps.value.toDouble() else 0.0,
                    intervalDown = if (enteredWeight.value.isNotEmpty()) enteredIntervalDown.value.toDouble() else 0.0,
                    groupCount = enteredGroupCount.value,
                    timeRest = if (enteredWeight.value.isNotEmpty()) enteredTimeRest.value.toInt() else 0,
                )
            )
        }
        .focusTarget()
        .pointerInput(Unit) { detectTapGestures { focusRequester.requestFocus() } }
        .onFocusedBoundsChanged {

        }
        .padding(horizontal = 8.dp)){
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextStringAndField(enterValue = enteredDuration, editing = visibleLazy,
                text = stringResource(id = R.string.duration) + " (" + stringResource(id = R.string.min) + "): ")
            Spacer(modifier = Modifier.weight(1f))
            TextStringAndField(enterValue = enteredDistance, editing = visibleLazy,
                text = stringResource(id = R.string.distance) + " (" + stringResource(id = R.string.km) + "): ")
            Spacer(modifier = Modifier.weight(1f))
            TextStringAndField(enterValue = enteredWeight, editing = visibleLazy,
                text = stringResource(id = R.string.weight) + " (" + stringResource(id = R.string.kg) + "): ")
        }

        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visibleLazy
        ){
            Column{
                TextApp(text = stringResource(id = R.string.duration_set), style = interReg14)
                SwitchDuration(uiState, set)
                Spacer(modifier = Modifier.height(8.dp))
                TextStringAndField( enterValue = enteredTimeRest, editing = true,
                    text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
            }
        }
    }
}
@Composable fun SwitchDuration( uiState: ExerciseScreenState, set: Set){

}
@Composable fun RowAddSet(uiState: ExerciseScreenState
){
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.tertiary, shape = shapeAddExercise)
                .clickable {
                    uiState.onAddUpdateSet(
                        uiState.exercise.idExercise,
                        SetDB(exerciseId = uiState.exercise.idExercise)
                    )
                }
        ) {
            TextApp(
                text = stringResource(id = R.string.add_set),
                style = interThin12,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .width(14.dp)
                    .height(14.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable fun ExerciseScreenLayoutPreview() {
    ExerciseScreenLayout( ExerciseScreenState())
}

//@Composable fun LazyContent(uiState: ExerciseScreenState, set: Set){
//    Column {
//        TextApp(text = "${set.idSet}: ${set.name}", style = interThin12)
//        TextApp(text = "Reps: ${set.reps}", style = interThin12)
//        TextApp(text = "intervalDown: ${set.intervalDown}", style = interThin12)
//        TextApp(text = "intervalReps: ${set.intervalReps}", style = interThin12)
//        TextApp(text = "Intensity: ${set.intensity}", style = interThin12)
//        TextApp(text = "distance: ${set.distance}", style = interThin12)
//        TextApp(text = "duration: ${set.duration}", style = interThin12)
//        TextApp(text = "exerciseId: ${set.exerciseId}", style = interThin12)
//        TextApp(text = "groupCount: ${set.groupCount}", style = interThin12)
//        TextApp(text = "timeRest: ${set.timeRest}", style = interThin12)
//        TextApp(text = "weight: ${set.weight}", style = interThin12)
//        TextApp(text = "speech: ${set.speech}", style = interThin12)
//    }
//}