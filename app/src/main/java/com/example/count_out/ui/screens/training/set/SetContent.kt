package com.example.count_out.ui.screens.training.set

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.domain.toDoubleMy
import com.example.count_out.domain.toIntMy
import com.example.count_out.entity.Const.contourAll1
import com.example.count_out.entity.Const.contourBot1
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.Zone
import com.example.count_out.entity.workout.Set
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.theme.alumBodySmall
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.view_components.RadioButtonApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextAppLines
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.TextStringAndField
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.icons.IconsCollapsing
import com.example.count_out.ui.view_components.icons.IconsGroup

@Composable fun SetContent(uiState: TrainingScreenState, set: Set, amountSet: Int, index: Int){
    Column{
        if (amountSet > 1) NameSet(uiState, set)
        AdditionalInformation(uiState,set, amountSet, index)
    }
}
@Composable fun NameSet(uiState: TrainingScreenState, set: Set) {
    val textSetName = when (set.goal) {
        GoalSet.DISTANCE -> " ( ${set.distance} " + stringResource(id = R.string.km) + " )"
        GoalSet.DURATION -> " ( ${set.distance} " + stringResource(id = R.string.min) + " )"
        GoalSet.COUNT -> " ( ${set.reps} " + stringResource(id = R.string.count) + " )"
        GoalSet.COUNT_GROUP -> " ( ${set.distance} " + stringResource(id = R.string.count) + " )"
    }
    Row (verticalAlignment = Alignment.CenterVertically){
        IconsCollapsing(
            onClick = { setCollapsing(uiState, set) },
            wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null )
        TextApp(
            text = set.name,
            style = interReg14,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 8.dp))
        TextApp(
            text = textSetName,
            style = interReg14,
            textAlign = TextAlign.Start,)
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { uiState.onCopySet(uiState.training.idTraining, set.idSet) },
            onClickDelete = { uiState.onDeleteSet(uiState.training.idTraining, set.idSet) },
            onClickSpeech = {
                uiState.set = set
                uiState.showSpeechSet.value = true},
        )
    }
}

@Composable fun AdditionalInformation(uiState: TrainingScreenState, set: Set, amountSet: Int, index: Int){
    val visibleLazy = (uiState.listCollapsingSet.value.find { it == set.idSet } != null) || (amountSet == 1)
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = visibleLazy) {
        Frame(color = MaterialTheme.colorScheme.surfaceContainerLowest, contour = contourAll1) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                MenuTask( uiState, set )
                BodySet( uiState, set)
                MenuZone( uiState, set )
            }
        }
    }
}

@Composable fun MenuTask( uiState: TrainingScreenState, set: Set){
    val modifier = Modifier.padding(vertical = 0.dp, horizontal = 16.dp)
    Row(verticalAlignment = Alignment.CenterVertically){
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.goal == GoalSet.DISTANCE) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE)) },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight =  if (set.goal == GoalSet.DISTANCE) FontWeight.Bold else FontWeight.Normal,
                text = stringResource(R.string.distance))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.goal == GoalSet.DURATION) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION)) },
                fontWeight =  if (set.goal == GoalSet.DURATION) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.duration))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.goal == GoalSet.COUNT) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT)) },
                fontWeight =  if (set.goal == GoalSet.COUNT) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.count))
        }
    }
}
@Composable fun MenuZone( uiState: TrainingScreenState, set: Set){
    val modifier = Modifier
        .padding(vertical = 2.dp, horizontal = 2.dp)
        .width(60.dp)
    Row(verticalAlignment = Alignment.CenterVertically){
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.intensity == Zone.EXTRA_SLOW) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.EXTRA_SLOW)) },
                style = MaterialTheme.typography.bodySmall,
                fontWeight =  if (set.intensity == Zone.EXTRA_SLOW) FontWeight.Bold else FontWeight.Normal,
                text = stringResource(R.string.zone1))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.intensity == Zone.SLOW) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.SLOW))},
                fontWeight =  if (set.intensity == Zone.SLOW) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(R.string.zone2))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.intensity == Zone.MEDIUM) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.MEDIUM)) },
                fontWeight =  if (set.intensity == Zone.MEDIUM) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(R.string.zone3))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.intensity == Zone.HIGH) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.HIGH)) },
                fontWeight =  if (set.intensity == Zone.HIGH) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(R.string.zone4))
        }
        Frame (color = MaterialTheme.colorScheme.outline,
            contour = if (set.intensity == Zone.MAX) contourAll1 else contourBot1) {
            TextApp( modifier = modifier.clickable {
                    uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.MAX)) },
                fontWeight =  if (set.intensity == Zone.MAX) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.bodySmall,
                text = stringResource(R.string.zone5))
        }
    }
}
@Composable fun BodySet( uiState: TrainingScreenState, set: Set){
    when (set.goal){
        GoalSet.DISTANCE -> BodySetDistance( uiState, set)
        GoalSet.DURATION -> BodySetDuration( uiState, set)
        GoalSet.COUNT -> BodySetCount( uiState, set)
        else -> {}
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable fun BodySetCount(uiState: TrainingScreenState, set: Set) {

}

@Composable fun BodySetDuration(uiState: TrainingScreenState, set: Set) {

}

@Composable fun BodySetDistance(uiState: TrainingScreenState, set: Set) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)){
        FieldText(uiState, set)
    }
}

@Composable fun FieldText(uiState: TrainingScreenState,set: Set){   //B7B7B7
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier) {
//        TextFieldApp(
//            typeKeyboard = TypeKeyboard.TEXT,
//            textStyle = MaterialTheme.typography.bodyMedium,
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDoubleMy())) },
//            placeholder = "${ set.distance }",
//            edit = true
//        )
        val text by rememberSaveable { mutableStateOf( "${set.distance}" ) }
        BasicTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier.background(Color.LightGray).widthIn(min = 12.dp)
        )
        TextApp(text = stringResource(R.string.distance), textAlign = TextAlign.Center, style = alumBodySmall)
    }
}

//#####################################Old#########################################################
@Composable fun AdditionalInformation1(uiState: TrainingScreenState, set: Set, amountSet: Int){
    val visibleLazy = (uiState.listCollapsingSet.value.find { it == set.idSet } != null) || (amountSet == 1)
    Column(modifier = Modifier.testTag("1"))
    {
        AnimatedVisibility(modifier = Modifier.padding(0.dp), visible = visibleLazy){
            Row(modifier = Modifier
                .testTag("1")
                .padding(start = 8.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TextApp(text = stringResource(id = R.string.task_in_approach), style = interReg14)
                    Spacer(modifier = Modifier.height(8.dp))
                    SwitchGoal(uiState, set)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextStringAndField(
                        placeholder = set.timeRest.toString(),
                        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(timeRest = it.toIntMy())) },
                        editing = visibleLazy,
                        text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(65.dp)
                        .padding(top = 12.dp),
                    content = { SelectZone(uiState, set) })
            }
        }
    }
}
@Composable fun SwitchGoal(uiState: TrainingScreenState, set: Set) {
    val state = remember { mutableIntStateOf( set.goal.id) }
    Column( Modifier.selectableGroup())
    {
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 1,
            state = state.intValue,
            onClick = {
                state.intValue = 1
                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE)) },
            contextRight = {
                TextStringAndField(
                    placeholder = set.distance.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDoubleMy())) },
                    editing = true,
                    visibleField = state.intValue == 1,
                    text = "${stringResource(id = R.string.distance)} (${stringResource(id = R.string.km)}):"
                )},
            contextBottom = { },
        )
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 2,
            state = state.intValue,
            onClick = {
                state.intValue = 2
                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION)) },
            contextRight = {
                TextStringAndField(
                    placeholder = set.duration.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(duration = it.toDoubleMy())) },
                    editing = true,
                    visibleField = state.intValue == 2,
                    text = "${stringResource(id = R.string.duration)} (${stringResource(id = R.string.min)})")
            },
            contextBottom = { RadioButtonDurationBottom(uiState = uiState, set = set, visible = state.intValue == 2)},
        )
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 3,
            state = state.intValue,
            onClick = {
                state.intValue = 3
                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT)) },
            contextRight = {
                TextStringAndField(
                    placeholder = set.reps.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
                    editing = true,
                    visibleField = state.intValue == 3,
                    text = stringResource(id = R.string.quantity_reps))
            },
            contextBottom = { RadioButtonCountBottom(uiState = uiState, set = set, visible = state.intValue == 3)},
        )
        Spacer(modifier = Modifier.height(6.dp))
        RadioButtonApp(
            radioButtonId = 4,
            state = state.intValue,
            onClick = {
                state.intValue = 4
                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT_GROUP)) },
            contextRight = {
                TextStringAndField(
                    placeholder = set.reps.toString(),
                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
                    editing = true,
                    visibleField = state.intValue == 4,
                    text = stringResource(id = R.string.quantity_group_reps)
                )},
            contextBottom = {
                RadioButtonCountGroupBottom(uiState = uiState, set = set, visible = state.intValue == 4)},
        )
    }
}
@Composable fun RadioButtonDurationBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
    RadioButtonBottom(uiState = uiState,set = set,visible = visible,
        text = "${stringResource(id = R.string.weight)} (${stringResource(id = R.string.kg)}):")
}
@Composable fun RadioButtonCountBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
    AnimatedVisibility(modifier = Modifier.padding(top = 0.dp), visible = visible
    ){
        Column(modifier = Modifier.padding(start = 12.dp)){
            TextStringAndField(
                placeholder = set.intervalReps.toString(),
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
                editing = true,
                text = stringResource(id = R.string.time_between_counts) + " (" +
                        stringResource(id = R.string.sec) + "): ")
            Spacer(modifier = Modifier.height(0.dp))
            TextStringAndField(
                placeholder = set.intervalDown.toString(),
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
                editing = true,
                text = stringResource(id = R.string.slowing_down_counts) )
        }
    }
}
@Composable fun RadioButtonCountGroupBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
    AnimatedVisibility(modifier = Modifier.padding(top = 8.dp, start = 12.dp), visible = visible
    ){
        Column{
            TextStringAndField(
                placeholder = set.intervalReps.toString(),
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
                editing = true,
                text = stringResource(id =
                R.string.time_between_counts) + " (" + stringResource(id = R.string.sec) + "): ")
            Spacer(modifier = Modifier.height(8.dp))
            TextStringAndField(
                placeholder = set.intervalDown.toString(),
                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
                editing = true,
                text = stringResource(id = R.string.slowing_down_counts) )
            Spacer(modifier = Modifier.height(8.dp))
            TextAppLines(
                text = stringResource(id = R.string.counts_by_group_add),
                style = interReg12,
                modifier = Modifier.padding(vertical = 2.dp))
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
@Composable fun RadioButtonBottom(uiState: TrainingScreenState, set: Set, visible: Boolean, text: String) {
    AnimatedVisibility( visible = visible, modifier = Modifier.padding(top = 8.dp, start = 12.dp)
    ){
        TextStringAndField(
            placeholder = set.weight.toString(),
            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toIntMy())) },
            editing = true,
            text = text
        )
    }
}
fun setCollapsing(uiState: TrainingScreenState, set: Set) {
    val listCollapsingSet = uiState.listCollapsingSet.value.toMutableList()
    val itemList = listCollapsingSet.find { it == set.idSet }
    if ( itemList != null) {
        listCollapsingSet.remove(itemList)
        uiState.listCollapsingSet.value = listCollapsingSet
    } else {
        listCollapsingSet.add(set.idSet)
        uiState.listCollapsingSet.value = listCollapsingSet
    }
}
@Composable fun SelectZone(uiState: TrainingScreenState, set: Set){
    TextApp(
        style = interLight12,
        text = stringResource(id = R.string.zone) )
    ChipZone(Zone.EXTRA_SLOW, uiState, set)
    ChipZone(Zone.SLOW, uiState, set)
    ChipZone(Zone.MEDIUM, uiState, set)
    ChipZone(Zone.HIGH, uiState, set)
    ChipZone(Zone.MAX, uiState, set)
}
@Composable fun ChipZone(zone: Zone, uiState: TrainingScreenState, set: Set){
    ChipMy(text = ".. " + zone.maxPulse.toString(),
        selected = set.intensity == zone,
        onClick = { uiState.onChangeSet ((set as SetDB).copy(intensity = zone)) },)
}

@Composable fun ChipMy(text: String, selected: Boolean, onClick: ()->Unit){
    val delta = 0.1f
    val containerColor = CardDefaults.cardColors().containerColor
    val containerColorSelected = Color(
        red = containerColor.red + if (containerColor.red < (1.0 - delta)) delta else 0.0f,
        blue = containerColor.blue + if (containerColor.blue < (1.0 - delta)) delta else 0.0f,
        green = containerColor.green + if (containerColor.green < (1.0 - delta)) delta else 0.0f,
        alpha = containerColor.alpha
    )
    Card(
        shape = MaterialTheme.shapes.extraSmall,
        elevation = elevationTraining(),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) containerColorSelected else containerColor),
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        TextApp(text = text,
            style = interLight12 ,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
    }
}
