package com.example.count_out.ui.screens.training.exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Set
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.view_components.GroupIcons4
import com.example.count_out.ui.view_components.RadioButtonApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextAppLines
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.TextStringAndField

@Composable
fun LazySets(uiState: TrainingScreenState)
{
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.testTag("1").padding(horizontal = 3.dp)
    ){
        items( items = uiState.exercise.sets, key = { it.idSet })
        { set ->
            Card (
                elevation = elevationTraining(),
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.tertiary,
//                    contentColor = MaterialTheme.colorScheme.onTertiary,
//                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
//                    disabledContentColor = MaterialTheme.colorScheme.onSecondary,
//                ),
                content = { SetEdit(uiState,set) }
            )
        }
    }
}
@Composable fun RowAddSet(uiState: TrainingScreenState)
{
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
//                .background(color = MaterialTheme.colorScheme.tertiary, shape = shapeAddExercise)
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
                    .size(Dimen.sizeIcon)
            )
        }
    }
}

@Composable
fun SetEdit(uiState: TrainingScreenState, set: Set)
{
    Spacer(modifier = Modifier.height(Dimen.width4))
    NameSet(uiState,set)
    AdditionalInformation(uiState,set)
    Spacer(modifier = Modifier.height(Dimen.width4))
}
@Composable
fun NameSet(uiState: TrainingScreenState, set: Set)
{
    val distance = if (set.distance > 0.0) "( ${set.distance} " + stringResource(id = R.string.km) + " )" else ""
    val duration = if (set.duration > 0)  "(${set.duration} " + stringResource(id = R.string.min) + ")" else ""

    Row (verticalAlignment = Alignment.CenterVertically){
        TextApp(
            text = "${set.idSet}: ",
            style = interReg14,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp))
        TextFieldApp(
            modifier =Modifier,
            placeholder = set.name,
            contentAlignment = Alignment.BottomStart,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = interReg14,
            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(name = it)) })
        TextApp(
            text = if (set.distance > 0) distance else duration,
            style = interReg14,
            textAlign = TextAlign.Start,)
        Spacer(modifier = Modifier.weight(1f))
        GroupIcons4(
            onCopy = { /*TODO*/ },
            onSpeech = { /*TODO*/ },
            onDel = { /*TODO*/ },
            onCollapsing = { setCollapsing(uiState, set) },
            wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null
        )
    }
}
@Composable
fun AdditionalInformation(uiState: TrainingScreenState, set: Set)
{
    val visibleLazy = uiState.listCollapsingSet.value.find { it == set.idSet } != null
    Column(modifier = Modifier
        .testTag("1")
        .padding(horizontal = 8.dp))
    {
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visibleLazy
        ){
            Column{
                Spacer(modifier = Modifier.height(8.dp))
                TextApp(text = stringResource(id = R.string.task_in_approach), style = interReg14)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchDuration(uiState, set)
                Spacer(modifier = Modifier.height(8.dp))
                TextStringAndField(
                    placeholder = set.timeRest.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(timeRest = it.toInt())) },
                    editing = visibleLazy,
                    text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
            }
        }
    }
}
@Composable
fun SwitchDuration(uiState: TrainingScreenState, set: Set)
{
    val state = remember { mutableIntStateOf(1) }
    Column(
        Modifier
            .selectableGroup()
            .padding(start = 12.dp))
    {
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 1,
            state = state.intValue,
            onClick = { state.intValue = 1},
            context = { RadioButtonDistance(uiState = uiState, set = set, visible = state.intValue == 1)}
        )
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 2,
            state = state.intValue,
            onClick = { state.intValue = 2},
            context = { RadioButtonDuration(uiState = uiState, set = set, visible = state.intValue == 2) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 3,
            state = state.intValue,
            onClick = { state.intValue = 3},
            context = { RadioButtonCount(uiState = uiState, set = set, visible = state.intValue == 3)}
        )
    }
}
@Composable
fun RadioButtonDistance(uiState: TrainingScreenState, set: Set, visible: Boolean)
{
    Column {
        TextStringAndField(
            placeholder = set.distance.toString(),
            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDouble())) },
            editing = true,
            text = stringResource(id = R.string.distance) + " (" + stringResource(id = R.string.km) + "): ")
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
        ){
            TextStringAndField(
                placeholder = set.intensity,
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intensity = it)) },
                editing = true,
                text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
        }
    }

}
@Composable
fun RadioButtonDuration(uiState: TrainingScreenState, set: Set, visible: Boolean)
{
    Column {
        TextStringAndField(
            placeholder = set.duration.toString(),
            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(duration = it.toInt())) },
            editing = true,
            text = stringResource(id = R.string.duration) + " (" + stringResource(id = R.string.min) + "): ")
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
        ){
            Row{
                TextStringAndField(
                    placeholder = set.intensity,
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intensity = it)) },
                    editing = true,
                    text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
                Spacer(modifier = Modifier.width(12.dp))
                TextStringAndField(
                    placeholder = set.weight.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toInt())) },
                    editing = true,
                    text = stringResource(id = R.string.weight) + " (" + stringResource(id = R.string.kg) + "): ")
            }
        }
    }
}
@Composable
fun RadioButtonCount(uiState: TrainingScreenState, set: Set, visible: Boolean)
{
    Column {
        TextStringAndField(
            placeholder = set.reps.toString(),
            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toInt())) },
            editing = true,
            text = stringResource(id = R.string.quantity_reps) )
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
        ){
            Column{
                TextStringAndField(
                    placeholder = set.weight.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toInt())) },
                    editing = true,
                    text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
                TextStringAndField(
                    placeholder = set.intervalReps.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toInt())) },
                    editing = true,
                    text = stringResource(id = R.string.time_between_counts) + " (" + stringResource(id = R.string.sec) + "): ")
                TextStringAndField(
                    placeholder = set.intervalDown.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toInt())) },
                    editing = true,
                    text = stringResource(id = R.string.slowing_down_counts) )
                SwitchCount(uiState, set)
            }
        }
    }
}
@Composable
fun SwitchCount(uiState: TrainingScreenState, set: Set)
{
    val state = remember { mutableIntStateOf(1) }
    Column(
        Modifier
            .selectableGroup()
            .padding(start = 12.dp))
    {
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 1,
            state = state.intValue,
            onClick = { state.intValue = 1},
            context = { RadioButtonCountingOrder()}
        )
        Spacer(modifier = Modifier.height(4.dp))
        RadioButtonApp(
            radioButtonId = 2,
            state = state.intValue,
            onClick = { state.intValue = 2},
            context = { RadioButtonCountingGroup(uiState = uiState, set = set, visible = state.intValue == 2)}
        )
    }
}
@Composable
fun RadioButtonCountingOrder()
{
    TextApp(
        text = stringResource(id = R.string.counting_in_order),
        style = interLight12,
        modifier = Modifier.padding(vertical = 2.dp))
}
@Composable
fun RadioButtonCountingGroup(uiState: TrainingScreenState, set: Set, visible: Boolean)
{
    Column{
        TextAppLines(
            text = stringResource(id = R.string.counts_by_group),
            style = interLight12,
            modifier = Modifier.padding(vertical = 2.dp))
        AnimatedVisibility(modifier = Modifier.padding(bottom = 4.dp), visible = visible
        ){
            TextFieldApp(
                modifier = Modifier.fillMaxWidth(),
                placeholder = set.groupCount,
                contentAlignment = Alignment.BottomStart,
                typeKeyboard = TypeKeyboard.TEXT,
                textStyle = interLight12,
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(groupCount = it)) })
        }
    }
}
fun setCollapsing(uiState: TrainingScreenState,  set: Set): Boolean
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