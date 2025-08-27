package com.count_out.presentation.screens.training.exercise

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.presentation.R
import com.count_out.presentation.models.DataForChangeSequenceImpl
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.models.SetImplP
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingEvent.ShowBS
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.screens.training.set.SetContent
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSelectActivity
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable
fun ListExercises(dataState: TrainingState, round: Round, modifier: Modifier = Modifier,)
{
    val listExercise = round.exercise
    ColumnDragDrop(
        items = listExercise,
        modifier = modifier,
        content = { item -> ElementColum( item, dataState = dataState) },
        onMoveItem = { from, to->
            dataState.event(
                TrainingEvent.ChangeSequenceExercise(
                    item = DataForChangeSequenceImpl(
                        trainingId = dataState.training?.idTraining ?: 0,
                        roundId = round.idRound,
                        ringId = 0,
                        from = from,
                        to = to )
                ))
        },)
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable fun <T>ElementColum (item:T, dataState: TrainingState,){
    Spacer(modifier = Modifier.padding(top = 1.dp))
    Frame(contour = contourAll1) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),) {
            Title(dataState, item as Exercise)
            BodyExercise(dataState, item as Exercise)
        }
    }
}
@Composable fun Title(dataState: TrainingState, exercise: Exercise) {
    ShowBottomSheetSpeech(dataState,dataState.showBS.exercise,R.string.exercise2,exercise)
    ShowBottomSheetSelectActivity(dataState, exercise)

    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 4.dp)){
        val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"
        IconsCollapsing(
            onClick = {
                dataState.event(TrainingEvent.SetCollapsing(dataState.collapsing.copy(item = exercise))) },
            wrap = dataState.collapsing.exercises.find { it == exercise.idExercise } != null)
        Spacer(modifier = Modifier.width(2.dp))
        Column {
            TextApp(
                text = exercise.activity?.name ?: "",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier)
            TextApp(
                style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${stringResource(id = R.string.sets)}: ${exercise.amountSet}/" +
                        "${exercise.duration} ${stringResource(id = R.string.min)}",
            ) }
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { dataState.event(TrainingEvent.CopyExercise(exercise))},
            onClickDelete = { dataState.event(TrainingEvent.DelExercise(exercise)) },
            onClickEdit = {
                dataState.item = exercise
                dataState.event(ShowBS(dataState.showBS.copy(element = exercise.activity)))},
            onClickSpeech = {
                dataState.item = exercise
                dataState.event(ShowBS(dataState.showBS.copy(element = exercise))) },
            onClickAddSet = { dataState.event( TrainingEvent.CopySet(
                    SetImplP(name = nameNewSet, exerciseId = exercise.idExercise)))},
        )
    }
}
@Composable fun BodyExercise(dataState: TrainingState, exercise: Exercise){
    val visibleLazy = dataState.collapsing.exercises.find { it ==exercise.idExercise } != null
    AnimatedVisibility( visible = visibleLazy){ ListSets(dataState, exercise) }
}
@Composable fun ListSets(dataState: TrainingState, exercise: Exercise) {
    Column {
        exercise.sets.forEachIndexed { ind, set ->
            Box (modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraSmall
                ),
                content = { SetContent(dataState,
                    SetImplP(set, Pair(ind, exercise.sets.count())) )}
            )
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}
//fun exerciseCollapsing(dataState: TrainingState, exercise: Exercise): Boolean {
//    val listCollapsingExercise = dataState.listCollapsingExercise.value.toMutableList()
//    val itemList = listCollapsingExercise.find { it == exercise.idExercise }
//    return if ( itemList != null) {
//        listCollapsingExercise.remove(itemList)
//        dataState.listCollapsingExercise.value = listCollapsingExercise
//        false
//    } else {
//        listCollapsingExercise.add( exercise.idExercise )
//        dataState.listCollapsingExercise.value = listCollapsingExercise
//        true
//    }
//}
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