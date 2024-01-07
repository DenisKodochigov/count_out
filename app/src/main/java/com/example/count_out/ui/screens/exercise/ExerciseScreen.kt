package com.example.count_out.ui.screens.exercise

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.Set
import com.example.count_out.entity.SizeElement
import com.example.count_out.ui.bottomsheet.BottomSheetSelectActivity
import com.example.count_out.ui.bottomsheet.BottomSheetSpeech
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.theme.shapeAddExercise
import com.example.count_out.ui.theme.shapesApp
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.animatedScroll

@SuppressLint("UnrememberedMutableState")
@Composable fun ExerciseScreen(roundId: Long, exerciseId:Long,
){
    val viewModel: ExerciseViewModel = hiltViewModel()
    viewModel.getExercise(roundId, exerciseId)
    ExerciseScreenCreateView( viewModel = viewModel)
}
@Composable fun ExerciseScreenCreateView( viewModel: ExerciseViewModel
){
    val uiState = viewModel.exerciseScreenState.collectAsState()
    uiState.value.onDismissSelectActivity = { uiState.value.showBottomSheetSelectActivity.value = false }
    uiState.value.onDismissSpeech = { uiState.value.showBottomSheetSpeech.value = false}
    uiState.value.nameSection = stringResource(id = R.string.exercise)

    ShowBottomSheet(uiState.value)
    ExerciseScreenLayout(uiState = uiState.value)
}
@Composable fun ShowBottomSheet(uiState: ExerciseScreenState) {
    if (uiState.showBottomSheetSelectActivity.value) BottomSheetSelectActivity(uiState)
    if (uiState.showBottomSheetSpeech.value) {
        uiState.item = uiState.exercise
        BottomSheetSpeech(uiState)
    }
}
@Composable
fun ExerciseScreenLayout(uiState: ExerciseScreenState
) {
    val offsetHeightPx = remember { mutableFloatStateOf(0f) }
    Column(
        Modifier
            .padding(Dimen.paddingAppHor)
            .fillMaxHeight()
            .animatedScroll(
                height = sizeApp(SizeElement.HEIGHT_BOTTOM_BAR),
                offsetHeightPx = offsetHeightPx
            ),
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(uiState)
        NameSection(uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
        ExerciseContent(uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
    }
}
@Composable fun NameTraining(uiState: ExerciseScreenState
){
    TextApp(
        text = uiState.nameTraining,
        textAlign = TextAlign.Start,
        style = interBold16,
        modifier = Modifier.padding(start = 12.dp)
    )
}
@Composable fun NameSection(uiState: ExerciseScreenState
){
    TextApp(
        text = "${uiState.nameRound}:${uiState.roundId}",
        textAlign = TextAlign.Start,
        style = interReg14,
        modifier = Modifier.padding(start = 24.dp)
    )
}
@Composable fun ExerciseContent(uiState: ExerciseScreenState
){
    Card (
        elevation = elevationTraining(),
        shape = MaterialTheme.shapes.extraSmall,
//        modifier = Modifier.padding(12.dp)
//            .fillMaxWidth()
//            .verticalScroll(
//                state = rememberScrollState(),
//                enabled = true,
//                flingBehavior = null,
//                reverseScrolling = false
//            )
    ){
        Column ( modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary, shape = shapesApp.extraSmall)
        ){
            Column (modifier = Modifier.padding(6.dp)
            ){
                SelectActivity(uiState)
                LazySets(uiState)
            }
            RowAddSet(uiState)
        }
    }
}
@Composable fun SelectActivity(uiState: ExerciseScreenState
){
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
    ){
        IconSelectActivity(uiState)
        Spacer(modifier = Modifier.width(12.dp))
        TextActivity(uiState)
        Spacer(modifier = Modifier.weight(1f))
        IconSpeech(uiState)
    }
}
@Composable fun IconSelectActivity(uiState: ExerciseScreenState
){
    IconButton(
        modifier = Modifier
            .width(24.dp)
            .height(24.dp),
        onClick = { uiState.showBottomSheetSelectActivity.value = true })
    {
        Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
    }
}
@Composable fun TextActivity(uiState: ExerciseScreenState
){
    TextApp(text = uiState.exercise.activity.name, style = interReg14)
}
@Composable fun IconSpeech(uiState: ExerciseScreenState
){
    IconButton(
        modifier = Modifier
            .width(24.dp)
            .height(24.dp),
        onClick = { uiState.showBottomSheetSpeech.value = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ecv), contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}
@Composable fun LazySets(uiState: ExerciseScreenState
){
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier.testTag("1")
    ){
        items( items = uiState.exercise.sets, key = { it.idSet })
        { item ->
            Spacer(modifier = Modifier.height(Dimen.width4))
            LazyContent(uiState, item)
            Spacer(modifier = Modifier.height(Dimen.width4))
        }
    }
}

@Composable fun LazyContent(uiState: ExerciseScreenState, set: Set){
    Column {
        TextApp(text = "${set.idSet}: ${set.name}", style = interThin12)
        TextApp(text = "Reps: ${set.reps}", style = interThin12)
        TextApp(text = "intervalDown: ${set.intervalDown}", style = interThin12)
        TextApp(text = "intervalReps: ${set.intervalReps}", style = interThin12)
        TextApp(text = "Intensity: ${set.intensity}", style = interThin12)
        TextApp(text = "distance: ${set.distance}", style = interThin12)
        TextApp(text = "duration: ${set.duration}", style = interThin12)
        TextApp(text = "exerciseId: ${set.exerciseId}", style = interThin12)
        TextApp(text = "groupCount: ${set.groupCount}", style = interThin12)
        TextApp(text = "timeRest: ${set.timeRest}", style = interThin12)
        TextApp(text = "weight: ${set.weight}", style = interThin12)
        TextApp(text = "speech: ${set.speech}", style = interThin12)
    }
}
@Composable fun RowAddSet(uiState: ExerciseScreenState
){
    Row (horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.tertiary, shape = shapeAddExercise)
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
                    .width(14.dp)
                    .height(14.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable fun ExerciseScreenLayoutPreview() {
    ExerciseScreenLayout( ExerciseScreenState())
}
