package com.example.count_out.ui.screens.training.round

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RoundType
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.exercise.ListExercises
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold14
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.view_components.IconsCollapsing
import com.example.count_out.ui.view_components.IconsGroup
import com.example.count_out.ui.view_components.TextApp
import kotlin.math.roundToInt

@Composable
fun Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    roundType.value.amount = amountExercise(uiState, roundType)
    roundType.value.duration = durationRound(uiState, roundType)
    Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box{
            Column( modifier = Modifier.padding(start = 6.dp, bottom=6.dp),)
            {
                Row1Round(uiState = uiState, roundType)
                Row2Round(roundType)
                Row3Round(uiState = uiState, roundType)
//                Row4Round(uiState = uiState, roundType)
            }
        }
    }
}
@Composable
fun Row1Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    val nameNewSet = stringResource(id = R.string.set)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp, end = 2.dp)
    ){
        IconsCollapsing(
            onClick = { setCollapsing(uiState, roundType) },
            wrap = getCollapsing(uiState, roundType) )
        TextApp(text = stringResource(id = roundType.value.strId), style = interBold14)
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = {showSpeechRound(uiState, roundType)},
            onClickAddExercise = {
                uiState.onAddExercise(getIdRound(uiState, roundType), SetDB(name = nameNewSet))}
        )
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable
fun Row2Round( roundType: MutableState<RoundType>){
    HorizontalDivider()
    Spacer(modifier = Modifier.height(4.dp))
    Row(modifier = Modifier.padding(end = 8.dp)) {
        TextApp(
            text = stringResource(id = R.string.exercises) + ": " + roundType.value.amount,
            style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        TextApp(
            text = stringResource(id = R.string.duration) + ": " + roundType.value.duration + " " +
                    stringResource(id = R.string.min),
            style = interLight12)
    }
}
@Composable
fun Row3Round(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    ListExercises(
        uiState = uiState,
        roundType = roundType.value,
        modifier = Modifier.padding(end = 8.dp),
        showExercises = getCollapsing(uiState, roundType) && roundType.value.amount > 0)
}

fun showSpeechRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>){
    when (roundType.value) {
        RoundType.UP -> uiState.showSpeechWorkUp.value = true
        RoundType.OUT -> uiState.showSpeechWorkOut.value = true
        RoundType.DOWN -> uiState.showSpeechWorkDown.value = true
    }
}
fun amountExercise(uiState: TrainingScreenState, roundType: MutableState<RoundType>) =
    uiState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.size ?: 0

fun durationRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>): Int{
    var durationRound = 0.0
    uiState.training.rounds.find{ it.roundType == roundType.value }?.exercise?.forEach { exercise ->
        exercise.sets.forEach { set->
            durationRound += when (set.goal){
                GoalSet.DURATION-> set.duration * 60
                GoalSet.COUNT-> set.reps * set.intervalReps
                GoalSet.COUNT_GROUP -> set.reps * set.intervalReps
                GoalSet.DISTANCE -> set.distance * 600
            } + set.timeRest
        }
    } ?: 0
    return ( durationRound / 60).roundToInt()
}
//    uiState.training.rounds.find{ it.roundType == roundType }?.exercise?.size ?: 0
fun setCollapsing(uiState: TrainingScreenState, roundType: MutableState<RoundType>) {
    if (roundType.value.amount > 0) {
        when (roundType.value) {
            RoundType.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            RoundType.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            RoundType.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
    }
}
fun getCollapsing(uiState: TrainingScreenState, roundType: MutableState<RoundType>): Boolean {
    return when (roundType.value) {
        RoundType.UP -> uiState.workUpCollapsing.value
        RoundType.OUT -> uiState.workOutCollapsing.value
        RoundType.DOWN -> uiState.workDownCollapsing.value
    }
}
fun getIdRound(uiState: TrainingScreenState, roundType: MutableState<RoundType>) =
    uiState.training.rounds.find { it.roundType == roundType.value }?.idRound ?: 0