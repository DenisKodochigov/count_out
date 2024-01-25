package com.example.count_out.ui.screens.training.round

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.entity.RoundType
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.exercise.ListExercises
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold14
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.view_components.IconCollapsingSpeech
import com.example.count_out.ui.view_components.TextApp

@Composable
fun Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box() //modifier = Modifier.background(color = MaterialTheme.colorScheme.primary))
        {
            Column( modifier = Modifier.padding(start = 6.dp),)
            {
                Row1Round(uiState = uiState, roundType)
                Row2Round(uiState = uiState, roundType)
                Row3Round(uiState = uiState, roundType)
            }
        }
    }
}
@Composable
fun Row1Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp, end = 8.dp)
    ){
        TextApp(text = nameRound( uiState, roundType, stringResource(id = roundType.strId)), style = interBold14)
        Spacer(modifier = Modifier.weight(1f))
        IconCollapsingSpeech(
            idIconCollapsing = getIconCollapsing(uiState, roundType),
            onCollapsing = { setCollapsing(uiState, roundType) },
            onSpeech = {  showSpeechRound(uiState, roundType) }
        )
    }
}
fun nameRound(uiState: TrainingScreenState, roundType: RoundType, name: String): String =
    "$name: ${(uiState.training.rounds.find { it.roundType == roundType }?.idRound ?: 0)}"

@Composable
fun Row2Round(uiState: TrainingScreenState, roundType: RoundType) {
    val amountExercise = stringResource(id = R.string.exercises) + ": " + amountExercise(uiState, roundType)
    val txtDuration = stringResource(id = R.string.duration) + ": " + uiState.durationRound.value.toString()
    Row(modifier = Modifier.padding(end = 8.dp)) {
        TextApp(text = amountExercise, style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        TextApp(text = txtDuration, style = interLight12)
    }
}
@Composable
fun Row3Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Column(modifier = Modifier.padding(end = 8.dp)){
        ListExercises(
            uiState = uiState,
            roundType = roundType,
            showExercises = getCollapsing(uiState, roundType) && amountExercise(uiState, roundType) > 0)
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
        {
            Spacer(modifier = Modifier.weight(1f))
            PoleAddExercise(uiState = uiState, roundType = roundType)
        }
    }
}

fun setCollapsing(uiState: TrainingScreenState, roundType: RoundType)
{
    if (amountExercise(uiState, roundType) > 0) {
        when (roundType) {
            RoundType.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            RoundType.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            RoundType.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
    }
}
fun showSpeechRound(uiState: TrainingScreenState, roundType: RoundType)
{
    when (roundType) {
        RoundType.UP -> uiState.showSpeechWorkUp.value = true
        RoundType.OUT -> uiState.showSpeechWorkOut.value = true
        RoundType.DOWN -> uiState.showSpeechWorkDown.value = true
    }
}
fun amountExercise(uiState: TrainingScreenState, roundType: RoundType) =
    uiState.training.rounds.find{ it.roundType == roundType }?.exercise?.size ?: 0

fun getCollapsing(uiState: TrainingScreenState, roundType: RoundType): Boolean
{
    return when (roundType) {
        RoundType.UP -> uiState.workUpCollapsing.value
        RoundType.OUT -> uiState.workOutCollapsing.value
        RoundType.DOWN -> uiState.workDownCollapsing.value
    }
}
fun getIconCollapsing(uiState: TrainingScreenState, roundType: RoundType): Int
{
    return when (roundType) {
        RoundType.UP -> getIcon(uiState.workUpCollapsing.value)
        RoundType.OUT -> getIcon(uiState.workOutCollapsing.value)
        RoundType.DOWN -> getIcon(uiState.workDownCollapsing.value)
    }
}

fun getIcon(collapsing: Boolean): Int = if (collapsing) R.drawable.ic_wrap1 else R.drawable.ic_wrap
@Composable
fun PoleAddExercise(uiState: TrainingScreenState, roundType: RoundType)
{
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { uiState.onAddExercise(getIdRound(uiState, roundType)) }
//            .background(color = MaterialTheme.colorScheme.secondary, shape = shapeAddExercise)
    ) {
        TextApp(
            text = stringResource(id = R.string.add_activity),
            style = interLight12,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "",
            modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon)
        )
    }
}
fun getIdRound(uiState: TrainingScreenState, roundType: RoundType) =
    uiState.training.rounds.find { it.roundType == roundType }?.idRound ?: 0