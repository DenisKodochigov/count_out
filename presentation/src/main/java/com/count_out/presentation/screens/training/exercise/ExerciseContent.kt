package com.count_out.presentation.screens.training.exercise

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.training.TrainingEvent
import com.count_out.presentation.screens.training.TrainingState
import com.count_out.presentation.screens.training.set.SetContent
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.presentation.R
import com.count_out.presentation.modeles.ActionWithSetImpl
import com.count_out.presentation.modeles.DataForChangeSequenceImpl
import com.count_out.presentation.modeles.Dimen.contourAll1
import com.count_out.presentation.modeles.SetImpl
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.drag_drop_column.column.ColumnDragDrop
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable
fun ListExercises(
    dataState: TrainingState,
    action: Action,
    round: Round,
    modifier: Modifier = Modifier,
    showExercises: Boolean)
{
    val listExercise = remember { round.exercise }
    ColumnDragDrop(
        items = listExercise,
        modifier = modifier,
        showList = showExercises,
        content = { item -> ElementColum( item, dataState = dataState, action = action) },
        onMoveItem = { from, to->
            action.ex(
                TrainingEvent.ChangeSequenceExercise(
                item = DataForChangeSequenceImpl(
                    trainingId = dataState.training.idTraining,
                    roundId = round.idRound,
                    ringId = 0,
                    from = from,
                    to = to )
                ))
        },)
    if (showExercises) Spacer(modifier = Modifier.height(4.dp))
}

@Composable fun <T>ElementColum (item:T, dataState: TrainingState, action: Action,){
    Spacer(modifier = Modifier.padding(top = 1.dp))
    dataState.exercise = item as Exercise
    Frame(contour = contourAll1) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),) {
            SelectActivity(dataState, item as Exercise, action = action)
            BodyExercise(dataState, item as Exercise, action = action)
        }
    }
}
@Composable fun SelectActivity(dataState: TrainingState, exercise: Exercise, action: Action) {
    Row( verticalAlignment = Alignment.CenterVertically){
        val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"
        IconsCollapsing(
            onClick = { exerciseCollapsing(dataState, exercise) },
            wrap = dataState.listCollapsingExercise.value.find { it == exercise.idExercise } != null )
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
            onClickCopy = { action.ex(TrainingEvent.CopyExercise(exercise))},
            onClickDelete = { action.ex(TrainingEvent.DelExercise(exercise)) },
            onClickEdit = {
                dataState.exercise = exercise
                dataState.showBottomSheetSelectActivity.value = true },
            onClickSpeech = {
                dataState.exercise = exercise
                dataState.showSpeechExercise.value = true },
            onClickAddSet = {
                action.ex(
                    TrainingEvent.AddSet(
                        ActionWithSetImpl(
                    id = dataState.training.idTraining,
                    set = SetImpl(name = nameNewSet, exerciseId = exercise.idExercise)
                )
                    ))},
        )
    }
}
@Composable fun BodyExercise(dataState: TrainingState, exercise: Exercise, action: Action){
    val visibleLazy = dataState.listCollapsingExercise.value.find { it == exercise.idExercise } != null
    AnimatedVisibility( visible = visibleLazy){ ListSets(dataState, exercise, action) }
}
@Composable fun ListSets(dataState: TrainingState, exercise: Exercise, action: Action) {
    Column {
        exercise.sets.forEachIndexed { ind, set ->
            Box (modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraSmall
                ).fillMaxWidth(),
                content = { SetContent(dataState, action,
                    (set as SetImpl).copy(positions = Pair(ind, exercise.sets.count()))) }
            )
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}
fun exerciseCollapsing(dataState: TrainingState, exercise: Exercise): Boolean {
    val listCollapsingExercise = dataState.listCollapsingExercise.value.toMutableList()
    val itemList = listCollapsingExercise.find { it == exercise.idExercise }
    return if ( itemList != null) {
        listCollapsingExercise.remove(itemList)
        dataState.listCollapsingExercise.value = listCollapsingExercise
        false
    } else {
        listCollapsingExercise.add( exercise.idExercise )
        dataState.listCollapsingExercise.value = listCollapsingExercise
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