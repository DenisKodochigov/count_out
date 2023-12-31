package com.example.count_out.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.getIdImage
import com.example.count_out.ui.theme.sizeApp
import com.example.count_out.ui.view_components.ItemSwipe
import com.example.count_out.ui.view_components.animatedScroll

@SuppressLint("UnrememberedMutableState")
@Composable fun SettingScreen( screen: ScreenDestination,
){
    val viewModel: SettingViewModel = hiltViewModel()
//    viewModel.getWorkouts()
    RoundScreenCreateView(
        screen = screen,
        viewModel = viewModel,
    )
}
@Composable fun RoundScreenCreateView(
    screen: ScreenDestination,
    viewModel: SettingViewModel
){
    val uiState by viewModel.settingScreenState.collectAsState()

    uiState.onDismiss = remember {{ uiState.triggerRunOnClickFAB.value = false }}
    uiState.idImage = getIdImage(screen)

    screen.onClickFAB = { uiState.triggerRunOnClickFAB.value = true}

//    if (uiState.triggerRunOnClickFAB.value) BottomSheetWorkoutAdd( uiState = uiState)
    RoundScreenLayout(uiState = uiState)
}
@Composable
fun RoundScreenLayout( uiState: SettingScreenState
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
        WorkoutLazyColumn(uiState = uiState,)
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WorkoutLazyColumn(uiState: SettingScreenState,
){
    Spacer(modifier = Modifier.height(2.dp))
    LazyList(uiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable fun LazyList(uiState: SettingScreenState)
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
@Composable fun RowLazy(item: Workout, uiState: SettingScreenState, modifier: Modifier){
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
        IconStart(uiState)
        Spacer(modifier = Modifier.width(Dimen.width8))
        NameWorkout(item = item, uiState = uiState )
        Spacer(modifier = Modifier.width(Dimen.width8))
        IconEnd(uiState)
    }
}
@Composable fun IconStart(uiState: SettingScreenState){}
@Composable fun IconEnd(uiState: SettingScreenState){}
@Composable fun NameWorkout(item: Workout, uiState: SettingScreenState,){}