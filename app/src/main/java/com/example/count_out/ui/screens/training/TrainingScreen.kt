package com.example.count_out.ui.screens.training

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.bottomsheet.BottomSheetSelectActivity
import com.example.count_out.ui.bottomsheet.BottomSheetSpeech
import com.example.count_out.ui.screens.training.round.Round
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.view_components.TextFieldApp

@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreen(
    trainingId: Long,
    onBaskScreen:() -> Unit
){
    val viewModel: TrainingViewModel = hiltViewModel()
    LaunchedEffect(true){ viewModel.getTraining(trainingId)}
    TrainingScreenCreateView(
        viewModel = viewModel,
        onBaskScreen = onBaskScreen)
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TrainingScreenCreateView(
    onBaskScreen:() -> Unit,
    viewModel: TrainingViewModel
){
    val uiState = viewModel.trainingScreenState.collectAsState()
    uiState.value.onBaskScreen = onBaskScreen
    uiState.value.onDismissSelectActivity = { uiState.value.showBottomSheetSelectActivity.value = false }
    EditSpeech(uiState.value)
    TrainingScreenLayout(uiState = uiState.value)
}
@Composable fun EditSpeech(uiState: TrainingScreenState)
{
    if (uiState.showSpeechTraining.value) {
        uiState.nameSection = stringResource(id = R.string.training)
        uiState.item = uiState.training
        uiState.onDismissSpeech = { uiState.showSpeechTraining.value = false}
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechWorkUp.value) {
        uiState.nameSection = stringResource(id = R.string.work_up)
        uiState.item = uiState.training.rounds.find { it.roundType == RoundType.UP }
        uiState.onDismissSpeech = { uiState.showSpeechWorkUp.value = false }
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechWorkOut.value) {
        uiState.nameSection = stringResource(id = R.string.work_out)
        uiState.item = uiState.training.rounds.find { it.roundType == RoundType.OUT }
        uiState.onDismissSpeech = { uiState.showSpeechWorkOut.value = false }
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechWorkDown.value) {
        uiState.nameSection = stringResource(id = R.string.work_down)
        uiState.item = uiState.training.rounds.find { it.roundType == RoundType.DOWN }
        uiState.onDismissSpeech = { uiState.showSpeechWorkDown.value = false}
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechExercise.value) {
        uiState.nameSection = stringResource(id = R.string.exercise)
        uiState.item = uiState.exercise
        uiState.onDismissSpeech = { uiState.showSpeechExercise.value = false}
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechSet.value) {
        uiState.nameSection = stringResource(id = R.string.set)
        uiState.item = uiState.set
        uiState.onDismissSpeech = { uiState.showSpeechSet.value = false}
        BottomSheetSpeech(uiState)
    }
    if (uiState.showBottomSheetSelectActivity.value) BottomSheetSelectActivity(uiState)
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreenLayout( uiState: TrainingScreenState
){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.paddingAppHor)
            .clickable(
                interactionSource = interactionSource, indication = null
            ) {
                if (uiState.training.name != uiState.enteredName.value) {
                    uiState.changeNameTraining(uiState.training, uiState.enteredName.value)
                }
                focusManager.clearFocus(true)
            },
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(uiState = uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, roundType = RoundType.UP)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, roundType = RoundType.OUT)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, roundType = RoundType.DOWN)
    }
}


@Composable
fun NameTraining( uiState: TrainingScreenState )
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
    {
        TextFieldApp(
            placeholder = uiState.enteredName.value,
            typeKeyboard = TypeKeyboard.TEXT,
            contentAlignment = Alignment.BottomStart,
            textStyle = interBold16,
            onChangeValue = {
                uiState.enteredName.value = it
                uiState.changeNameTraining(uiState.training, uiState.enteredName.value)}
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            onClick = { uiState.showSpeechTraining.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ecv), contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            onClick = {
                uiState.onDeleteTraining(uiState.training.idTraining)
                uiState.onBaskScreen.invoke()
            })
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_del1),
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingScreenLayoutPreview() {
    TrainingScreenLayout(TrainingScreenState())
}
