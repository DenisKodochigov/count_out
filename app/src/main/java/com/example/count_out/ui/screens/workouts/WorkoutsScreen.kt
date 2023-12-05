package com.example.count_out.ui.screens.workouts

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.navigation.ScreenDestination
import com.example.count_out.ui.bottomsheet.BottomSheetWorkoutAdd
import com.example.count_out.ui.theme.getIdImage

@SuppressLint("UnrememberedMutableState")
@Composable fun WorkoutsScreen( onClickWorkout: (Long) -> Unit, screen: ScreenDestination,
){
    val viewModel: WorkoutsViewModel = hiltViewModel()
    viewModel.getWorkouts()
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

    uiState.changeNameWorkout = remember { { workout -> viewModel.changeNameWorkout(workout) }}
    uiState.deleteWorkout = remember {{ workoutId -> viewModel.deleteWorkout(workoutId) }}
    uiState.onAddClick = remember {{ viewModel.addWorkout(it) }}
    uiState.onDismiss = remember {{ uiState.triggerRunOnClickFAB.value = false }}
    uiState.onClickWorkout = remember {{id -> onClickWorkout(id)}}
    uiState.idImage = getIdImage(screen)
    uiState.screenTextHeader = stringResource(screen.textHeader)

    screen.textFAB = stringResource(id = R.string.workout_text_fab)
    screen.onClickFAB = { uiState.triggerRunOnClickFAB.value = true}

    if (uiState.triggerRunOnClickFAB.value) BottomSheetWorkoutAdd( uiState = uiState)

}