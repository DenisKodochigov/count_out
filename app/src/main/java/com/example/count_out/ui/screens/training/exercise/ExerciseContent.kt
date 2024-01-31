package com.example.count_out.ui.screens.training.exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.RoundType
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.screens.training.set.SetContent
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.IconsCollapsingCopySpeechDel
import com.example.count_out.ui.view_components.TextApp


@Composable
fun ListExercises(uiState: TrainingScreenState, roundType: RoundType, showExercises: Boolean)
{
    val listExercise = uiState.training.rounds.find { it.roundType == roundType }?.exercise ?: emptyList()
    listExercise.forEach { exercise ->
        AnimatedVisibility(modifier = Modifier.padding(top = 8.dp), visible = showExercises
        ){
            Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
            ){
                Column {
                    SelectActivity(uiState, exercise)
                    BodyExercise(uiState, exercise)
                }
            }
        }
    }
}
@Composable
fun SelectActivity(uiState: TrainingScreenState, exercise: Exercise)
{
    uiState.exercise = exercise
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 4.dp)
    ){
        IconSelectActivity(uiState, exercise)
        Spacer(modifier = Modifier.width(12.dp))
        TextApp(text = exercise.activity.name, style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconsCollapsingCopySpeechDel(
            onCopy = { uiState.onCopyExercise(uiState.training.idTraining, exercise.idExercise)},
            onSpeech = {
                uiState.exercise = exercise
                uiState.showSpeechExercise.value = true },
            onDel = { uiState.onDeleteExercise(uiState.training.idTraining, exercise.idExercise) },
            onCollapsing = { exerciseCollapsing(uiState, exercise)},
            wrap = uiState.listCollapsingExercise.value.find { it == exercise.idExercise } != null
        )
    }
}
@Composable
fun IconSelectActivity(uiState: TrainingScreenState, exercise: Exercise)
{
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),
        onClick = {
            uiState.exercise = exercise
            uiState.showBottomSheetSelectActivity.value = true })
    { Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "") }
}

@Composable fun BodyExercise(uiState: TrainingScreenState, exercise: Exercise){
    Column(
        modifier = Modifier
    ){
        AnimatedVisibility(modifier = Modifier.padding(0.dp),
            visible = uiState.listCollapsingExercise.value.find { it == exercise.idExercise } != null
        ){
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp), content = {
                uiState.exercise = exercise
                ListSets(uiState)})
        }
        RowAddSet(uiState, exercise)
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
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            content = { SetContent(uiState,set) }
        )
        Spacer(modifier = Modifier.height(1.dp))
    }
}
@Composable fun RowAddSet(uiState: TrainingScreenState, exercise: Exercise)
{
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
//                .background(color = Color.Green, shape = shapeAddExercise)
                .clickable {
                    uiState.onAddUpdateSet( exercise.idExercise,
                        SetDB(name = nameNewSet, exerciseId = exercise.idExercise)
                    )
                }
        ) {
            TextApp(
                text = stringResource(id = R.string.add_set),
                style = interLight12,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
                modifier = Modifier
                    .padding(4.dp)
                    .size(Dimen.sizeIcon)
            )
        }
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