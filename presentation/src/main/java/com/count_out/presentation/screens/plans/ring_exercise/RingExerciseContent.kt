package com.count_out.presentation.screens.plans.ring_exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Ring.Companion.amount
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.plans.getCollapsing
import com.count_out.presentation.screens.plans.getSelecting
import com.count_out.presentation.screens.plans.model.PlansEvent
import com.count_out.presentation.screens.plans.model.PlansState
import com.count_out.presentation.screens.plans.set.SetBody
import com.count_out.presentation.screens.plans.setCollapsing
import com.count_out.presentation.screens.plans.setSelecting
import com.count_out.presentation.screens.plans.showActivities
import com.count_out.presentation.screens.plans.showChangeOrder
import com.count_out.presentation.screens.plans.showSpeech
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.icons.IconGoal
import com.count_out.presentation.view_element.icons.IconZone
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun RingContent(dataState: PlansState, ring: Ring, index: Int){
    if (ring.amount > 1) { RingCard(dataState, ring, index) }
    else { ring.exercises.forEach{ exercise ->  ExerciseCard(dataState, ring, exercise) } }
    Spacer(modifier = Modifier.height(4.dp))
}
@Composable fun RingCard(dataState: PlansState, ring: Ring, index: Int){
    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
    var id by remember { mutableIntStateOf(0) }
    LaunchedEffect(ring.exercises.size) {
        if (id >= ring.exercises.size) id = maxOf(0, ring.exercises.size - 1) }
    val selectedItem = getItemByIndex(id,ring.exercises)
    RingExerciseCarcass(
        getExpand = { getCollapsing(dataState, ring) },
        setExpand = { setCollapsing(dataState, ring) },
        getSelect = { item-> getSelecting(dataState, item) },
        nameItem = { RingName(dataState, ring, index, enteredName ) },
        infoItem = { RingNameInfo(ring) },
        actionItem = { RingActions(dataState, ring) },
        listItem = ring.exercises,
        onClick = { ind->
            id = ind
            setSelecting( dataState,getItemByIndex(ind,ring.exercises),ring.exercises)
        },
        onLongClick = { showChangeOrder(dataState, ring, ring.exercises) },
        nameItemLeftList = { ind->  "${stringResource(R.string.exer)} ${ind + 1}" },
        textFieldTopSetBody = { ExerciseName(dataState, selectedItem)},
        setBody = { SetBody(dataState, selectedItem.sets[0])},
        actionTitle = { ExerciseActions(dataState, ring, selectedItem) },
        iconGoal = { IconGoal(selectedItem.sets[0].goal){
                dataState.event(PlansEvent.ChangeGoal( selectedItem.sets[0]))}
        },
        iconZone = { IconZone(selectedItem.sets[0].intensity.ordinal + 1,
                onClick = {dataState.event(PlansEvent.ChangeZone(selectedItem.sets[0]))})},
        actionBottom = {}
    )
}
@Composable fun RingName(dataState: PlansState, ring: Ring, index: Int, enteredName: MutableState<String>){
    TextApp(
        text = stringResource(id = R.string.ring) + " ${index + 1}: ",
        textAlign = TextAlign.Start,
        style = typography.titleLarge,
    )
    TextFieldApp(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .width(20.dp),
        edit = true,
        typeKeyboard = TypeKeyboard.DIGIT,
        contentAlignment = Alignment.CenterStart,
        textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
        colorLine = colorScheme.outline,
        placeholder = enteredName.value,
        onChangeFocus = {
            enteredName.value = it
            dataState.event(PlansEvent.UpdateRing(ring.amount(enteredName.value.toInt())))
        }
    )
    TextApp(
        text = " ${stringResource(id = R.string.circles)}",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleLarge,
    )
}
@Composable fun RingNameInfo(ring: Ring){
    TextApp(style = MaterialTheme.typography.bodySmall,
        text = stringResource(id = R.string.exercises) + " ${ring.exercises.count()}: ",)
}
@Composable fun RingActions(dataState: PlansState, ring: Ring,){
    IconsGroup(
        onRingAdd = { dataState.event(PlansEvent.CopyRing(ring = Ring.default(ring.partId)))},
        onRingDel = { dataState.event(PlansEvent.DelRing(ring))},
        onRingSpeech = { showSpeech(dataState, ring)},
        onToExercise = {dataState.event(PlansEvent.RingOrExercise(ring))}
    )
}
@Composable fun ExerciseCard(dataState: PlansState, ring: Ring, exercise: Exercise){
    var id by remember { mutableIntStateOf(0) }
    LaunchedEffect(exercise.sets.size) {
        if (id >= exercise.sets.size) id = maxOf(0, exercise.sets.size - 1) }
    val selectedItem = getItemByIndex(id,exercise.sets)
    RingExerciseCarcass(
        getExpand = { getCollapsing(dataState, exercise) },
        setExpand = { setCollapsing(dataState, exercise) },
        getSelect = { item-> getSelecting(dataState, item) },
        nameItem = { TextApp(text = exercise.activity.name, style = typography.titleMedium,) },
        infoItem = { ExerciseNameInfo(exercise) },
        actionItem = { ExerciseActions(dataState, ring, exercise) },
        listItem = exercise.sets,
        onClick = { ind->
                id = ind
                setSelecting( dataState,getItemByIndex(ind,exercise.sets), exercise.sets)
        },
        onLongClick = { showChangeOrder(dataState, exercise, exercise.sets) },
        nameItemLeftList = { ind-> "${stringResource(R.string.set_short)} ${ind + 1}" },
        textFieldTopSetBody = { },
        setBody = { SetBody(dataState, selectedItem)},
        actionTitle = {  },
        iconGoal = { IconGoal(selectedItem.goal){ dataState.event(PlansEvent.ChangeGoal( selectedItem))} },
        iconZone = { IconZone(selectedItem.intensity.ordinal + 1,
            onClick = {dataState.event(PlansEvent.ChangeZone(selectedItem))})},
        actionBottom = { SetActions(dataState, selectedItem) }
    )
}
@Composable fun ExerciseName(dataState: PlansState, exercise: Exercise){
    TextApp( text = exercise.activity.name,
        textDecoration = TextDecoration.Underline, style = typography.titleLarge,
        modifier = Modifier.padding(bottom = 0.dp, start = 6.dp)
            .clickable { showActivities(dataState,exercise,dataState.list) }
    )
}
@Composable fun ExerciseNameInfo(exercise: Exercise){
    TextApp( style = typography.bodySmall, fontWeight = FontWeight.Light,
        text = """${stringResource(id = R.string.sets)}: ${exercise.sets.count()}/
                ${exercise.duration.value} ${stringResource(id = exercise.duration.unit.id)}""".trimMargin())
}
@Composable fun ExerciseActions(dataState: PlansState, ring: Ring, exercise: Exercise){
        IconsGroup(
            onExerciseAdd = { dataState.event(PlansEvent.CopyExercise(exercise))},
            onExerciseDel = { dataState.event(PlansEvent.DelExercise(exercise)) },
            onEdit = { showActivities(dataState,exercise, dataState.list)},
            onExerciseSpeech = { showSpeech(dataState, exercise) },
            onToRing = { dataState.event(PlansEvent.RingOrExercise(ring)) }
        )
}
@Composable fun SetActions(dataState: PlansState, set: Set){
    IconsGroup(
        onSetAdd = {dataState.event(PlansEvent.CopySet(set))},
        onSetDel = {dataState.event(PlansEvent.DeleteSet(set))},
        onSetSpeech = { showSpeech(dataState, set)},
        label = R.string.other
    )
}
fun <T: Any>getItemByIndex(ind: Int, list: List<T>): T{
    return list.getOrNull(ind) ?: list.first()
}



//@Composable fun RingCardTitle(dataState: PlansState, ring: Ring, index: Int){
//    val enteredName: MutableState<String> = remember { mutableStateOf(ring.amount.toString() ) }
//    CarcassTitle(
//        onSetCollaps = { setCollapsing(dataState, ring)},
//        startIcon = { },
//        nameItem = { RingName(dataState, ring, index, enteredName )},
//        infoItem = { TextApp(style = MaterialTheme.typography.bodySmall,
//            text = stringResource(id = R.string.exercises) + " ${ring.exercises.count()}: ",) },
//        actionItem = {
//            IconsGroup(
//                onRingAdd = { dataState.event(PlansEvent.CopyRing(ring = Ring.default(ring.partId)))},
//                onRingDel = { dataState.event(PlansEvent.DelRing(ring))},
//                onRingSpeech = { showSpeech(dataState, ring)},
//                onToExercise = {dataState.event(PlansEvent.RingOrExercise(ring))}
//            )
//        }
//    )
//}
//@Composable fun RingCardBody(dataState: PlansState, ring: Ring){
//    val visible = getCollapsing(dataState, ring)
//    AnimatedVisibility(modifier = Modifier.padding(horizontal = 4.dp), visible = visible) {
//        if (ring.exercises.isNotEmpty()) {
//            if (ring.exercises.find{ it.idExercise in dataState.selecting.exercises } == null) {
//                setSelecting(dataState, ring.exercises[0],
//                    ring.exercises.map { LongDm(it.idExercise) })
//            }
////            CarcassTuning(
////                columnRight = { RingCardBodyExerciseBody( dataState, ring)},
////                button = {},
////                listItem = ring.exercises,
////            )
//        }
//    }
//}




