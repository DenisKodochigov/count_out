package com.example.count_out.ui.screens.training.set

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.domain.toDoubleMy
import com.example.count_out.domain.toIntMy
import com.example.count_out.entity.Const.contourAll1
import com.example.count_out.entity.Const.contourBot1
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.WeightE
import com.example.count_out.entity.Zone
import com.example.count_out.entity.workout.Set
import com.example.count_out.ui.screens.training.TrainingScreenState
import com.example.count_out.ui.theme.alumBodyLarge
import com.example.count_out.ui.theme.alumBodyMedium
import com.example.count_out.ui.theme.alumBodySmall
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.custom_view.Frame
import com.example.count_out.ui.view_components.custom_view.IconQ
import com.example.count_out.ui.view_components.icons.IconsCollapsing
import com.example.count_out.ui.view_components.icons.IconsGroup

val interval_between_pole = 4.dp

@Composable fun SetContent(uiState: TrainingScreenState, set: Set, amountSet: Int, index: Int){
    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp), visible = true) {
        Frame(color = colorScheme.surfaceContainerLowest, contour = contourAll1) {
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                if (amountSet > 1 && uiState.listCollapsingSet.value.find { it == set.idSet } == null)
                    FirstLine(uiState, set, index)
                else {
                    TaskSwitch( uiState, set, index, amountSet )
                    BodySet( uiState, set)
                    ZoneSwitch( uiState, set )
                }
            }
        }
    }
}
@Composable fun FirstLine(uiState: TrainingScreenState, set: Set, index: Int) {
    val setInfo = when (set.goal) {
        GoalSet.DISTANCE -> viewDistance(set) + stringResource(id = set.distanceE.id)
        GoalSet.DURATION -> "${set.duration/60} ${stringResource(id = R.string.min)}"
        GoalSet.COUNT -> "${stringResource(id = R.string.count)}: ${set.reps}"
        GoalSet.COUNT_GROUP -> "${set.reps} ${stringResource(id = R.string.count)}"
    }
    Row (verticalAlignment = Alignment.CenterVertically){
        IconsCollapsing(
            onClick = { setCollapsing(uiState, set) },
            wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null )
        TextApp(
            text = "${(index + 1)}" ,
            style = typography.titleMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp, end =16.dp))
        Spacer(modifier = Modifier.weight(1f))
        TextApp(
            text = setInfo,
            style = typography.bodySmall,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Start,)
        IconsGroup(
            onClickCopy = { uiState.onCopySet(uiState.training.idTraining, set.idSet) },
            onClickDelete = { uiState.onDeleteSet(uiState.training.idTraining, set.idSet) },
            onClickSpeech = {
                uiState.set = set
                uiState.showSpeechSet.value = true},
        )
    }
}
@Composable fun BodySet( uiState: TrainingScreenState, set: Set){
    when (set.goal){
        GoalSet.DISTANCE -> Distance( uiState, set)
        GoalSet.DURATION -> Duration( uiState, set)
        GoalSet.COUNT -> Count( uiState, set)
        else -> {}
    }
}

@Composable fun Distance(uiState: TrainingScreenState, set: Set) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)){
        DistancePole(uiState, set)
        RestPole(uiState, set)
    }
}
@Composable fun Duration(uiState: TrainingScreenState, set: Set) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp)){
        DurationPole(uiState, set)
        WeightPole(uiState, set)
        RestPole(uiState, set)
    }
}
@Composable fun Count(uiState: TrainingScreenState, set: Set) {
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)){
        IntervalPole(uiState, set, Modifier.weight(1f))
        WeightPole(uiState, set, Modifier.weight(1f))
        RestPole(uiState, set, Modifier.weight(1f))
    }
    Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp)){
        CountFieldText(uiState, set)
        CountGroupFieldText(uiState, set)
    }
}

@Composable fun DistancePole(uiState: TrainingScreenState, set: Set, modifier: Modifier = Modifier,){   //B7B7B7
    PoleInput(
        unitId1 = R.string.m,
        unitId2 = R.string.km,
        headId = R.string.distance,
        term = set.distanceE == DistanceE.M,
        placeholder = "${if (set.distanceE == DistanceE.M) set.distance else set.distance/1000 }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(
            distance = (if (set.distanceE == DistanceE.M) 1 else 1000 )* it.toDoubleMy())) },
        onChangeUnit = {uiState.onChangeSet ((set as SetDB).copy(
            distanceE = if (set.distanceE == DistanceE.M) DistanceE.KM else DistanceE.M)) }
    )
}
@Composable fun DurationPole(uiState: TrainingScreenState, set: Set, modifier: Modifier = Modifier){   //B7B7B7
    PoleInput(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.duration,
        term = set.durationE == TimeE.SEC,
        placeholder = "${ if (set.durationE == TimeE.MIN) set.duration/60 else set.duration}",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(
            duration = (if (set.durationE == TimeE.MIN) 60 else 1) * it.toInt())) },
        onChangeUnit = {uiState.onChangeSet ((set as SetDB).copy(
            durationE = if (set.durationE == TimeE.SEC) TimeE.MIN else TimeE.SEC)) }
    )
}
@Composable fun IntervalPole(uiState: TrainingScreenState, set: Set, modifier: Modifier = Modifier){   //B7B7B7
    PoleInput(
        unitId1 = R.string.sec,
        headId = R.string.interval,
        term = true,
        placeholder = "${ set.intervalReps }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy() )) },
        onChangeUnit = { }
    )
}
@Composable fun WeightPole(uiState: TrainingScreenState, set: Set, modifier: Modifier = Modifier){   //B7B7B7
    PoleInput(
        unitId1 = R.string.gr,
        unitId2 = R.string.kg,
        headId = R.string.weight,
        term = set.weightE == WeightE.GR,
        placeholder = "${ if (set.weightE == WeightE.GR) set.weight else set.weight/1000.0 }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(
            weight = ((if (set.weightE == WeightE.GR) 1 else 1000) * it.toDoubleMy()).toInt() ))},
        onChangeUnit = {uiState.onChangeSet ((set as SetDB).copy(
            weightE = if (set.weightE == WeightE.GR) WeightE.KG else WeightE.GR)) }
    )

}
@Composable fun RestPole(uiState: TrainingScreenState, set: Set, modifier: Modifier = Modifier){
    PoleInput(
        unitId1 = R.string.sec,
        unitId2 = R.string.min,
        headId = R.string.rest_time,
        term = set.timeRestE == TimeE.SEC,
        placeholder =  "${ if (set.timeRestE == TimeE.SEC) set.timeRest else set.timeRest/60 }",
        modifier = modifier,
        typeKey = TypeKeyboard.DIGIT,
        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(
            timeRest = (if (set.timeRestE == TimeE.SEC) 1 else 60) * it.toIntMy() )) },
        onChangeUnit = {uiState.onChangeSet ((set as SetDB).copy(
            timeRestE = if (set.timeRestE == TimeE.SEC) TimeE.MIN else TimeE.SEC)) }
    )
}

@Composable fun CountFieldText(uiState: TrainingScreenState, set: Set){   //B7B7B7
    FieldText( idText = R.string.count, typeKey = TypeKeyboard.DIGIT, placeholder = "${ set.reps }",
        onChange = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy()))  },)
}
@Composable fun CountGroupFieldText(uiState: TrainingScreenState, set: Set){   //B7B7B7
    FieldText( idText = R.string.counts_by_group_add,
        placeholder = set.groupCount,
        onChange = { uiState.onChangeSet ((set as SetDB).copy(groupCount = it)) },)
}

@Composable fun TaskSwitch(uiState: TrainingScreenState, set: Set, index: Int, amountSet: Int){
    Row(verticalAlignment = Alignment.CenterVertically){
        if (amountSet > 1){
            IconsCollapsing(
                onClick = { setCollapsing(uiState, set) },
                wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null )
        }
        TextApp(
            text = "${(index + 1)}" ,
            style = typography.displayMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 4.dp, end =16.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconQ.Duration(selected = set.goal == GoalSet.DURATION,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION))},)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Distance(selected = set.goal == GoalSet.DISTANCE,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE))},)
        Spacer(modifier = Modifier.width(24.dp))
        IconQ.Count(selected = set.goal == GoalSet.COUNT,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT))},)
        Spacer(modifier = Modifier.width(24.dp))
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickCopy = { uiState.onCopySet(uiState.training.idTraining, set.idSet) },
            onClickDelete = { uiState.onDeleteSet(uiState.training.idTraining, set.idSet) },
            onClickSpeech = {
                uiState.set = set
                uiState.showSpeechSet.value = true},)
    }
}
@Composable fun ZoneSwitch(uiState: TrainingScreenState, set: Set){
    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.weight(1f))
        ZoneButtonSwitch(selected = set.intensity == Zone.EXTRA_SLOW, idString = R.string.zone1,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.EXTRA_SLOW))})
        ZoneButtonSwitch(selected = set.intensity == Zone.SLOW, idString = R.string.zone2,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.SLOW))})
        ZoneButtonSwitch(selected = set.intensity == Zone.MEDIUM, idString = R.string.zone3,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.MEDIUM))})
        ZoneButtonSwitch(selected = set.intensity == Zone.HIGH, idString = R.string.zone4,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.HIGH))})
        ZoneButtonSwitch(selected = set.intensity == Zone.MAX, idString = R.string.zone5,
            onClick = {uiState.onChangeSet ((set as SetDB).copy(intensity = Zone.MAX))})
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable fun ZoneButtonSwitch(selected: Boolean, onClick: () -> Unit, idString: Int,){
    ButtonSwitch(selected = selected, idString = idString, onClick = onClick,
        style = alumBodyMedium, modifier = Modifier.width(50.dp))
}

@Composable fun ButtonSwitch(
    color: Color = colorScheme.outline,
    background: Color = colorScheme.background,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.width(35.dp).padding(vertical = 2.dp, horizontal = 2.dp),
    idString: Int,
    style: TextStyle = typography.bodySmall)
{
    Frame (color = color, contour = if (selected) contourAll1 else contourBot1,
            background = background,) {
        TextApp( modifier = modifier.clickable { onClick() },
            fontWeight =  if (selected) FontWeight.Bold else FontWeight.Normal,
            style = style,
            text = stringResource(idString))
    }
}

@Composable fun PoleInput(
    unitId1: Int,
    unitId2: Int = R.string.no,
    headId: Int,
    term: Boolean,
    placeholder: String,
    modifier: Modifier = Modifier,
    typeKey: TypeKeyboard = TypeKeyboard.TEXT,
    onChangeValue: (String)-> Unit,
    onChangeUnit: ()-> Unit,
){
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(start = interval_between_pole)
            .background(color = colorScheme.onSecondary, shape = shapes.small)
            .padding(top = 2.dp, bottom = 6.dp, start = 4.dp, end = 4.dp)
    ) {
        TextApp(text = stringResource(headId), textAlign = TextAlign.Center, style = alumBodySmall)
        Row (verticalAlignment = Alignment.Top,
        ) {
            TextFieldApp(
                typeKeyboard = typeKey,
                contentAlignment = Alignment.Center,
                textStyle = typography.bodyLarge.copy(textAlign = TextAlign.Center),
                onChangeValue = { onChangeValue(it) },
                placeholder = placeholder,
            )
            Text(
                buildAnnotatedString {
                    append("(")
                    withStyle(style = SpanStyle(fontWeight = if (term) FontWeight.ExtraBold else FontWeight.Normal))
                    { append(stringResource(unitId1)) }
                    if (unitId2 != R.string.no) {
                        append("/")
                        withStyle(style = SpanStyle(
                            fontWeight = if (term) FontWeight.Normal else FontWeight.ExtraBold))
                        { append(stringResource(unitId2)) }
                    }
                    append(")")},
                modifier = Modifier.padding(start = 2.dp, top = 2.dp).clickable{ onChangeUnit() },
                style = alumBodyLarge,
                color = colorScheme.outline
            )
        }
    }
}
@Composable fun FieldText (typeKey: TypeKeyboard = TypeKeyboard.TEXT,
    placeholder: String, idText: Int, onChange: (String)-> Unit,)
{
    val density = LocalDensity.current.density
    var width by remember { mutableStateOf( 40.dp) }
    Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(start = 12.dp)) {
        TextFieldApp(
            typeKeyboard = typeKey,
            contentAlignment = Alignment.Center,
            textStyle = typography.bodyLarge.copy(textAlign = TextAlign.Center),
            onChangeValue = {onChange(it)},
            placeholder = placeholder,
            width = width
        )
        TextApp(text = stringResource(idText), textAlign = TextAlign.Center, style = alumBodySmall,
            modifier = Modifier.onGloballyPositioned { width = (it.size.width / density).dp },)
    }
}
fun viewDistance(set:Set):String {
    return "${ if (set.distanceE == DistanceE.KM) set.distance/1000 else set.distance }"
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
//        TaskButtonSwitch(selected = set.goal == GoalSet.DISTANCE, idString = R.string.distance,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE))},)
//        TaskButtonSwitch(selected = set.goal == GoalSet.DURATION, idString = R.string.duration,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION))})
//        TaskButtonSwitch(selected = set.goal == GoalSet.COUNT, idString = R.string.count,
//            onClick = {uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT))})
//@Composable fun TaskButtonSwitch(selected: Boolean, onClick: () -> Unit, idString: Int,){
//    ButtonSwitch(selected = selected, idString = idString, onClick = onClick,
//        modifier = Modifier.width(90.dp), style = typography.bodyMedium )
//}
//#####################################Old#########################################################
//@Composable fun AdditionalInformation1(uiState: TrainingScreenState, set: Set, amountSet: Int){
//    val visibleLazy = (uiState.listCollapsingSet.value.find { it == set.idSet } != null) || (amountSet == 1)
//    Column(modifier = Modifier.testTag("1"))
//    {
//        AnimatedVisibility(modifier = Modifier.padding(0.dp), visible = visibleLazy){
//            Row(modifier = Modifier
//                .testTag("1")
//                .padding(start = 8.dp)) {
//                Column(modifier = Modifier.weight(1f)) {
//                    Spacer(modifier = Modifier.height(8.dp))
//                    TextApp(text = stringResource(id = R.string.task_in_approach), style = interReg14)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    SwitchGoal(uiState, set)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    TextStringAndField(
//                        placeholder = set.timeRest.toString(),
//                        onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(timeRest = it.toIntMy())) },
//                        editing = visibleLazy,
//                        text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
//                    Spacer(modifier = Modifier.height(16.dp))
//                }
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .width(65.dp)
//                        .padding(top = 12.dp),
//                    content = { SelectZone(uiState, set) })
//            }
//        }
//    }
//}
//@Composable fun SwitchGoal(uiState: TrainingScreenState, set: Set) {
//    val state = remember { mutableIntStateOf( set.goal.id) }
//    Column( Modifier.selectableGroup())
//    {
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 1,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 1
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DISTANCE)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.distance.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDoubleMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 1,
//                    text = "${stringResource(id = R.string.distance)} (${stringResource(id = R.string.km)}):"
//                )},
//            contextBottom = { },
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 2,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 2
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.DURATION)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.duration.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(duration = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 2,
//                    text = "${stringResource(id = R.string.duration)} (${stringResource(id = R.string.min)})")
//            },
//            contextBottom = { RadioButtonDurationBottom(uiState = uiState, set = set, visible = state.intValue == 2)},
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 3,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 3
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.reps.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 3,
//                    text = stringResource(id = R.string.quantity_reps))
//            },
//            contextBottom = { RadioButtonCountBottom(uiState = uiState, set = set, visible = state.intValue == 3)},
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 4,
//            state = state.intValue,
//            onClick = {
//                state.intValue = 4
//                uiState.onChangeSet ((set as SetDB).copy(goal = GoalSet.COUNT_GROUP)) },
//            contextRight = {
//                TextStringAndField(
//                    placeholder = set.reps.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toIntMy())) },
//                    editing = true,
//                    visibleField = state.intValue == 4,
//                    text = stringResource(id = R.string.quantity_group_reps)
//                )},
//            contextBottom = {
//                RadioButtonCountGroupBottom(uiState = uiState, set = set, visible = state.intValue == 4)},
//        )
//    }
//}
//@Composable fun RadioButtonDurationBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    RadioButtonBottom(uiState = uiState,set = set,visible = visible,
//        text = "${stringResource(id = R.string.weight)} (${stringResource(id = R.string.kg)}):")
//}
//@Composable fun RadioButtonCountBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    AnimatedVisibility(modifier = Modifier.padding(top = 0.dp), visible = visible
//    ){
//        Column(modifier = Modifier.padding(start = 12.dp)){
//            TextStringAndField(
//                placeholder = set.intervalReps.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
//                editing = true,
//                text = stringResource(id = R.string.time_between_counts) + " (" +
//                        stringResource(id = R.string.sec) + "): ")
//            Spacer(modifier = Modifier.height(0.dp))
//            TextStringAndField(
//                placeholder = set.intervalDown.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
//                editing = true,
//                text = stringResource(id = R.string.slowing_down_counts) )
//        }
//    }
//}
//@Composable fun RadioButtonCountGroupBottom(uiState: TrainingScreenState, set: Set, visible: Boolean) {
//    AnimatedVisibility(modifier = Modifier.padding(top = 8.dp, start = 12.dp), visible = visible
//    ){
//        Column{
//            TextStringAndField(
//                placeholder = set.intervalReps.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toDoubleMy())) },
//                editing = true,
//                text = stringResource(id =
//                R.string.time_between_counts) + " (" + stringResource(id = R.string.sec) + "): ")
//            Spacer(modifier = Modifier.height(8.dp))
//            TextStringAndField(
//                placeholder = set.intervalDown.toString(),
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toIntMy())) },
//                editing = true,
//                text = stringResource(id = R.string.slowing_down_counts) )
//            Spacer(modifier = Modifier.height(8.dp))
//            TextAppLines(
//                text = stringResource(id = R.string.counts_by_group_add),
//                style = interReg12,
//                modifier = Modifier.padding(vertical = 2.dp))
//            TextFieldApp(
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = set.groupCount,
//                typeKeyboard = TypeKeyboard.TEXT,
//                textStyle = interLight12,
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(groupCount = it)) })
//        }
//    }
//}
//@Composable fun RadioButtonBottom(uiState: TrainingScreenState, set: Set, visible: Boolean, text: String) {
//    AnimatedVisibility( visible = visible, modifier = Modifier.padding(top = 8.dp, start = 12.dp)
//    ){
//        TextStringAndField(
//            placeholder = set.weight.toString(),
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toIntMy())) },
//            editing = true,
//            text = text
//        )
//    }
//}

//@Composable fun SelectZone(uiState: TrainingScreenState, set: Set){
//    TextApp(
//        style = interLight12,
//        text = stringResource(id = R.string.zone) )
//    ChipZone(Zone.EXTRA_SLOW, uiState, set)
//    ChipZone(Zone.SLOW, uiState, set)
//    ChipZone(Zone.MEDIUM, uiState, set)
//    ChipZone(Zone.HIGH, uiState, set)
//    ChipZone(Zone.MAX, uiState, set)
//}
//@Composable fun ChipZone(zone: Zone, uiState: TrainingScreenState, set: Set){
//    ChipMy(text = ".. " + zone.maxPulse.toString(),
//        selected = set.intensity == zone,
//        onClick = { uiState.onChangeSet ((set as SetDB).copy(intensity = zone)) },)
//}
//
//@Composable fun ChipMy(text: String, selected: Boolean, onClick: ()->Unit){
//    val delta = 0.1f
//    val containerColor = CardDefaults.cardColors().containerColor
//    val containerColorSelected = Color(
//        red = containerColor.red + if (containerColor.red < (1.0 - delta)) delta else 0.0f,
//        blue = containerColor.blue + if (containerColor.blue < (1.0 - delta)) delta else 0.0f,
//        green = containerColor.green + if (containerColor.green < (1.0 - delta)) delta else 0.0f,
//        alpha = containerColor.alpha
//    )
//    Card(
//        shape = shapes.extraSmall,
//        elevation = elevationTraining(),
//        colors = CardDefaults.cardColors(
//            containerColor = if (selected) containerColorSelected else containerColor),
//        modifier = Modifier
//            .padding(horizontal = 6.dp, vertical = 4.dp)
//            .clickable { onClick() }
//    ) {
//        TextApp(text = text,
//            style = interLight12 ,
//            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
//    }
//}
