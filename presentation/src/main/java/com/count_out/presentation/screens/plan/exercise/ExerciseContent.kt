package com.count_out.presentation.screens.plan.exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.screens.plan.CarcassLeftList
import com.count_out.presentation.screens.plan.CarcassTitle
import com.count_out.presentation.screens.plan.CarcassTuningSet
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.set.SetBody
import com.count_out.presentation.screens.plan.setSelecting
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSelectActivity
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconGoal
import com.count_out.presentation.view_element.icons.IconSingle
import com.count_out.presentation.view_element.icons.IconZone
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

//#############################################################################################
//                               Circle training
@Composable fun RingCardBodyExerciseList(dataState: PlanState, ring: Ring) {
    CarcassLeftList( dataState, ring.exercises,
        { ind-> "${stringResource(R.string.exer)} ${ind + 1}" })
}
@Composable fun RingCardBodyExerciseBody(dataState: PlanState, ring: Ring) {
    val selectedExercise = ring.exercises.find { it.idExercise in dataState.selecting.exercises } ?: ring.exercises[0]
    RingCardBodyExerciseName( dataState, selectedExercise )
    SetBody( dataState, selectedExercise.sets[0] )
    ControlExercise( dataState, selectedExercise)
}
@Composable fun RingCardBodyExerciseName(dataState: PlanState, exercise: Exercise){
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
        TextApp(text = exercise.activity.name,
            textDecoration = TextDecoration.Underline,
            style = typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 0.dp, start = 6.dp)
                .clickable {
                    dataState.item = exercise
                    dataState.event(ShowBS(dataState.showBS.copy(domain = exercise.activity)))
                })
    }
}
@Composable fun ControlExercise(dataState: PlanState, exercise: Exercise){
    val set = exercise.sets[0]
    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.weight(1f))
        IconGoal(set.goal){dataState.event(PlanEvent.ChangeGoal( set))}
        Spacer(modifier = Modifier.weight(1f))
        IconZone(set.intensity.ordinal + 1, onClick = {dataState.event(PlanEvent.ChangeZone(set))})
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Default.CopyAll, label = R.string.copy,
            onClick = {dataState.event(PlanEvent.CopyExercise(exercise))} )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = R.drawable.waveform, label = R.string.speech,
            onClick = { dataState.item = exercise
            dataState.event(ShowBS(dataState.showBS.copy(domain = exercise)))} )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Default.DeleteOutline, label = R.string.delete,
            onClick = {dataState.event(PlanEvent.DelExercise(exercise))} )
        Spacer(modifier = Modifier.weight(1f))
    }
}

//#############################################################################################
//                               Regular training
@Composable fun ExercisesList(dataState: PlanState, ring: Ring) {
    Column(modifier = Modifier.padding(end = 4.dp)) {
        ring.exercises.forEach { exercise-> ExerciseItem(dataState, ring, exercise)}
    }
}
@Composable fun ExerciseItem (dataState: PlanState, ring: Ring, exercise: Exercise){
    Spacer(modifier = Modifier.padding(top = 1.dp))
    Frame(contour = contourAll1) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),) {
            TitleExerciseItem(dataState, ring, exercise)
            BodyExerciseItem(dataState, exercise)
        }
    }
}
@Composable fun TitleExerciseItem(dataState: PlanState, ring: Ring, exercise: Exercise) {
    ShowBottomSheetSpeech(dataState,dataState.showBS.exercise,exercise)
    ShowBottomSheetSelectActivity(dataState, exercise)
    val nameNewSet = stringResource(id = R.string.set) + " ${exercise.sets.size + 1}"

    CarcassTitle(onCollapsing = { IconsCollapsing( wrap = dataState.collapsing.exercises.find { it == exercise.idExercise } != null,
            onClick = { dataState.event(PlanEvent.SetCollapsing(dataState.collapsing.copy(item = exercise))) },) },
        nameItem = { TextApp(text = exercise.activity.name, style = typography.titleMedium,)},
        infoItem = { TextApp( style = typography.bodySmall, fontWeight = FontWeight.Light,
            text = """${stringResource(id = R.string.sets)}: ${exercise.amountSet}/${exercise.duration.value} ${stringResource(id = exercise.duration.unit.id)}""")},
        actionItem = {
            IconsGroup(
                onClickCopy = { dataState.event(PlanEvent.CopyExercise(exercise))},
                onClickDelete = { dataState.event(PlanEvent.DelExercise(exercise)) },
                onClickEdit = {
                    dataState.item = exercise
                    dataState.event(ShowBS(dataState.showBS.copy(domain = exercise.activity)))},
                onClickSpeech = {
                    dataState.item = exercise
                    dataState.event(ShowBS(dataState.showBS.copy(domain = exercise))) },
                onClickAddSet = { dataState.event( PlanEvent.CopySet(
                    Set.default(name = nameNewSet, exerciseId = exercise.idExercise)))},
                onClickRingExercise = { dataState.event(PlanEvent.RingOrExercise(ring)) },
                selected = ring.amount > 1
            )
        }
    )
}
@Composable fun BodyExerciseItem(dataState: PlanState, exercise: Exercise){
    val visibleLazy = dataState.collapsing.exercises.find { it ==exercise.idExercise } != null
    AnimatedVisibility( visible = visibleLazy){
        val list = exercise.sets
        if (list.isNotEmpty()) {
            if (list.find{ it.idSet in dataState.selecting.sets } == null) {
                setSelecting(dataState, list[0], list.map { LongDm(it.idSet) }) }
            CarcassTuningSet(
                columnLeft = { ListSets( dataState, exercise)},
                columnRight = { BodyExerciseItemSetBody( dataState, exercise)},
                button = {}
            )
        }
    }
}
@Composable fun ListSets(dataState: PlanState, exercise: Exercise) {
    CarcassLeftList(dataState, list = exercise.sets,
        textItem = { ind-> "${stringResource(R.string.set)} ${ind + 1}" },)
}
@Composable fun BodyExerciseItemSetBody(dataState: PlanState, exercise: Exercise){
    val selectedSet = exercise.sets.find { it.idSet in dataState.selecting.sets } ?: exercise.sets[0]
    SetBody( dataState, selectedSet )
    ControlSet( dataState, selectedSet)
}
@Composable fun ControlSet(dataState: PlanState, set: Set){

    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.weight(1f))
        IconGoal(set.goal){dataState.event(PlanEvent.ChangeGoal( set))}
        Spacer(modifier = Modifier.weight(1f))
        IconZone(set.intensity.ordinal + 1, onClick = {dataState.event(PlanEvent.ChangeZone(set))})
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Default.CopyAll, label = R.string.copy,
            onClick = {dataState.event(PlanEvent.CopySet(set))} )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = R.drawable.waveform, label = R.string.speech,
            onClick = { dataState.item = set
                dataState.event(ShowBS(dataState.showBS.copy(domain = set)))} )
        Spacer(modifier = Modifier.weight(1f))
        IconSingle(image = Icons.Default.DeleteOutline, label = R.string.delete,
            onClick = {dataState.event(PlanEvent.DeleteSet(set))} )
        Spacer(modifier = Modifier.weight(1f))
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