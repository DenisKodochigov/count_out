package com.example.count_out.ui.screens.training.exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.set.SetContent
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.drag_drop_column.column.ColumnDD
import com.example.count_out.ui.view_components.icons.IconsCollapsing
import com.example.count_out.ui.view_components.icons.IconsGroup
import com.example.count_out.ui.view_components.lg

@Composable
fun ListExercises(
    uiState: TrainingScreenState,
    roundType: RoundType,
    modifier: Modifier = Modifier,
    showExercises: Boolean)
{
    val listExercise: MutableState<List<Exercise>> = remember { mutableStateOf(emptyList()) }
    listExercise.value = uiState.training.rounds.find { it.roundType == roundType }?.exercise ?: emptyList()
    lg(" listExercise ${listExercise.value.map { it.idExercise.toString() + "/" + it.idView }}")
    ColumnDD(
        items = listExercise.value,
        modifier = modifier,
        showList = showExercises,
        content = { item -> ElementColum( item, uiState = uiState) },
        onMoveItem = { from, to->
            lg(" listExercise ${listExercise.value.map { it.idExercise.toString() + "/" + it.idView }}  ${ from to to}")
            uiState.changeSequenceExercise(
                uiState.training.idTraining,
                if (listExercise.value.isNotEmpty()) listExercise.value[0].roundId else 0,
                from, to )
        },
    )
}

@Composable
fun <T>ElementColum (item:T, uiState: TrainingScreenState){
    Card(
        elevation = elevationTraining(),
        shape = MaterialTheme.shapes.extraSmall
    ){
        Column {
            SelectActivity(uiState, item as Exercise)
            BodyExercise(uiState, item as Exercise)
        }
    }
}
@Composable
fun SelectActivity(uiState: TrainingScreenState, exercise: Exercise)
{
    uiState.exercise = exercise
    Row( verticalAlignment = Alignment.CenterVertically){
        val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"
        IconsCollapsing(
            onClick = {exerciseCollapsing(uiState, exercise) },
            wrap = uiState.listCollapsingExercise.value.find { it == exercise.idExercise } != null )
        Spacer(modifier = Modifier.width(2.dp))
        TextApp(
            text = exercise.activity.name,
            textAlign = TextAlign.Start,
            style = interReg14,
            modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { uiState.onCopyExercise(uiState.training.idTraining, exercise.idExercise)},
            onClickDelete = { uiState.onDeleteExercise(uiState.training.idTraining, exercise.idExercise) },
            onClickEdit = {
                uiState.exercise = exercise
                uiState.showBottomSheetSelectActivity.value = true },
            onClickSpeech = {
                uiState.exercise = exercise
                uiState.showSpeechExercise.value = true },
            onClickAddSet = {
                uiState.onAddUpdateSet( exercise.idExercise,
                    SetDB( name = nameNewSet, exerciseId = exercise.idExercise))},
        )
    }
}

@Composable
fun BodyExercise(uiState: TrainingScreenState, exercise: Exercise){
    val visibleLazy =
        uiState.listCollapsingExercise.value.find { it == exercise.idExercise } != null
    Column{
        AnimatedVisibility( visible = visibleLazy){
            Column (modifier = Modifier.fillMaxWidth(),
                content = {
                    uiState.exercise = exercise
                    ListSets(uiState)
                }
            )
        }
    }
}
@Composable
fun ListSets(uiState: TrainingScreenState)
{
    uiState.exercise.sets.forEach { set ->
        Box (
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraSmall
                )
                .fillMaxWidth(),
            content = { SetContent(uiState,set, uiState.exercise.sets.size) }
        )
        Spacer(modifier = Modifier.height(1.dp))
    }
}
fun exerciseCollapsing(uiState: TrainingScreenState,  exercise: Exercise): Boolean
{
    val listCollapsingExercise = uiState.listCollapsingExercise.value.toMutableList()
    val itemList = listCollapsingExercise.find { it == exercise.idExercise }
    return if ( itemList != null) {
        listCollapsingExercise.remove(itemList)
        uiState.listCollapsingExercise.value = listCollapsingExercise
        false
    } else {
        listCollapsingExercise.add( exercise.idExercise )
        uiState.listCollapsingExercise.value = listCollapsingExercise
        true
    }
}
//@Composable
//fun RowAddSet(uiState: TrainingScreenState, exercise: Exercise)
//{
//    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
//        val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"
//        IconAddItem(
//            textId = R.string.add_set,
//            onAdd = {
//                uiState.onAddUpdateSet( exercise.idExercise,
//                SetDB(name = nameNewSet, exerciseId = exercise.idExercise))
//            }
//        )
//    }
//}