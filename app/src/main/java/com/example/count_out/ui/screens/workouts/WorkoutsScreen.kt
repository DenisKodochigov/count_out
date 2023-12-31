package com.example.count_out.ui.screens.workouts

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BlurOn
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.entity.SizeElement
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.animatedScroll
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@Composable fun WorkoutsScreen( onClickWorkout: (Long) -> Unit, screen: ScreenDestination,
){
    val viewModel: WorkoutsViewModel = hiltViewModel()
//    viewModel.getWorkouts()
    WorkoutScreenCreateView(
        onClickWorkout = onClickWorkout,
        screen = screen,
        viewModel = viewModel,
    )
}
@Composable fun WorkoutScreenCreateView(
    onClickWorkout: (Long) -> Unit,
    screen: ScreenDestination,
    viewModel: WorkoutsViewModel
){
    val uiState by viewModel.workoutScreenState.collectAsState()

//    uiState.changeNameWorkout = remember { { workout -> viewModel.changeNameWorkout(workout) }}
//    uiState.deleteWorkout = remember {{ workoutId -> viewModel.deleteWorkout(workoutId) }}
//    uiState.onAddClick = remember {{ viewModel.addWorkout(it) }}
    uiState.onDismiss = remember {{ uiState.triggerRunOnClickFAB.value = false }}
    uiState.onClickWorkout = remember {{id -> onClickWorkout(id)}}
    uiState.idImage = getIdImage(screen)

    screen.onClickFAB = { uiState.triggerRunOnClickFAB.value = true}

    WorkoutScreenLayout(uiState = uiState)
}
@Composable
fun WorkoutScreenLayout( uiState: WorkoutsScreenState
) {
    val offsetHeightPx = remember { mutableFloatStateOf(0f) }
    Column(
        Modifier
            .fillMaxHeight()
            .animatedScroll(
                height = sizeApp(SizeElement.HEIGHT_BOTTOM_BAR),
                offsetHeightPx = offsetHeightPx
            ),
    ){
        WorkoutLazyColumn(
            uiState = uiState,
            scrollOffset =-offsetHeightPx.floatValue.roundToInt())
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WorkoutLazyColumn(uiState: WorkoutsScreenState, scrollOffset:Int,
){
    Spacer(modifier = Modifier.height(2.dp))
    LazyList(uiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun LazyList(uiState: WorkoutsScreenState)
{
    val listState = rememberLazyListState()
    val listItems = uiState.workouts.value
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.testTag("1")
    )
    {
        items( items = listItems, key = { it.idWorkout })
        { item ->
            ItemSwipe(
                frontView = {
                    RowLazy(item, uiState, modifier = Modifier.animateItemPlacement()) },
                actionDragLeft = { uiState.deleteWorkout( item.idWorkout )},
                actionDragRight = { uiState.editWorkout(item) },
            )
        }
    }
}
@Composable fun RowLazy(item: Workout, uiState: WorkoutsScreenState, modifier: Modifier){
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
        IconStart(item = item, uiState = uiState)
        Spacer(modifier = Modifier.width(Dimen.width8))
        NameWorkout(item = item, uiState = uiState )
        Spacer(modifier = Modifier
            .width(Dimen.width8)
            .weight(1f))
        IconEnd(item = item, uiState = uiState)
    }
}
@Composable fun IconStart(item: Workout, uiState: WorkoutsScreenState, modifier: Modifier = Modifier){
    IconButton(onClick = { uiState.onSelect(item)}) {
        Icon(imageVector = Icons.Default.CheckCircleOutline, contentDescription = "")}
}
@Composable fun IconEnd(item: Workout, uiState: WorkoutsScreenState, modifier: Modifier = Modifier){
    IconButton(onClick = { uiState.onOtherAction(item)}) {
        Icon(imageVector = Icons.Default.BlurOn, contentDescription = "")}
}
@Composable fun NameWorkout(item: Workout, uiState: WorkoutsScreenState,){
    TextApp(
        text = item.name,
        style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
        modifier = Modifier.clickable { uiState.onSelectItem(item) })
}