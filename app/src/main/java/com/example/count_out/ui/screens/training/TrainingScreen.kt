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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.count_out.R
import com.example.count_out.entity.enums.RoundType
import com.example.count_out.ui.view_components.bottom_sheet.BottomSheetSelectActivity
import com.example.count_out.ui.view_components.bottom_sheet.BottomSheetSpeech
import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.models.TypeKeyboard
import com.example.count_out.ui.navigation.NavigateEvent
import com.example.count_out.ui.screens.prime.Action
import com.example.count_out.ui.screens.prime.PrimeScreen
import com.example.count_out.ui.screens.training.round.Round
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.mTypography
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.icons.IconsGroup

@SuppressLint("UnrememberedMutableState")
@Composable fun TrainingScreen(navigateEvent: NavigateEvent, trainingId: Long){
    val viewModel: TrainingViewModel = hiltViewModel()
    viewModel.initNavigate(navigateEvent)
    LaunchedEffect(Unit) { viewModel.submitEvent(TrainingEvent.GetTraining(trainingId)) }
    TrainingScreenCreateView( viewModel = viewModel )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun TrainingScreenCreateView( viewModel: TrainingViewModel){

    val action = Action {viewModel.submitEvent(it) }
    viewModel.dataState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            dataState.onDismissSelectActivity =
                { dataState.showBottomSheetSelectActivity.value = false}
            EditSpeech(dataState = dataState, action = action)
            TrainingScreenLayout(dataState, action = action)
        }
    }
}
@Composable fun EditSpeech(dataState: TrainingState, action:Action) {
    if (dataState.showSpeechTraining.value) {
        dataState.nameSection = stringResource(id = R.string.training)
        dataState.item = dataState.training
        dataState.onDismissSpeech = { dataState.showSpeechTraining.value = false}
        BottomSheetSpeech(dataState)
    }
    if (dataState.showSpeechWorkUp.value) {
        dataState.nameSection = stringResource(id = R.string.work_up1)
        dataState.item = dataState.training.rounds.find { it.roundType == RoundType.WorkUp }
        dataState.onDismissSpeech = { dataState.showSpeechWorkUp.value = false }
        BottomSheetSpeech(dataState)
    }
    if (dataState.showSpeechWorkOut.value) {
        dataState.nameSection = stringResource(id = R.string.work_out1)
        dataState.item = dataState.training.rounds.find { it.roundType == RoundType.WorkOut }
        dataState.onDismissSpeech = { dataState.showSpeechWorkOut.value = false }
        BottomSheetSpeech(dataState)
    }
    if (dataState.showSpeechWorkDown.value) {
        dataState.nameSection = stringResource(id = R.string.work_down1)
        dataState.item = dataState.training.rounds.find { it.roundType == RoundType.WorkDown }
        dataState.onDismissSpeech = { dataState.showSpeechWorkDown.value = false}
        BottomSheetSpeech(dataState)
    }
    if (dataState.showSpeechExercise.value) {
        dataState.nameSection = stringResource(id = R.string.exercise2)
        dataState.item = dataState.exercise
        dataState.onDismissSpeech = { dataState.showSpeechExercise.value = false}
        BottomSheetSpeech(dataState)
    }
    if (dataState.showSpeechSet.value) {
        dataState.nameSection = stringResource(id = R.string.set2)
        dataState.item = dataState.set
        dataState.onDismissSpeech = { dataState.showSpeechSet.value = false}
        BottomSheetSpeech(dataState)
    }
    if (dataState.showBottomSheetSelectActivity.value) BottomSheetSelectActivity(dataState, action)
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreenLayout(dataState: TrainingState, action:Action){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.paddingAppHor)
            .clickable(interactionSource = interactionSource, indication = null){
                focusManager.clearFocus(true)},
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(dataState = dataState, action = action)
        dataState.training.rounds.forEach { round ->
            Spacer(modifier = Modifier.height(Dimen.width8))
            Round(dataState = dataState, action = action, round = round)
        }
    }
}
@Composable
fun NameTraining(dataState: TrainingState, action:Action ) {
    if (dataState.training.idTraining == 0L) return
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp))
    {
        TextFieldApp(
            placeholder = dataState.enteredName.value,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = mTypography.headlineMedium.copy(textAlign = TextAlign.Start),
            edit = true,
            colorLine = MaterialTheme.colorScheme.outline,
            onChangeValue = {
                dataState.enteredName.value = it
                action.ex( TrainingEvent.UpdateTraining(
                    (dataState.training as TrainingImpl).copy(name = dataState.enteredName.value)))
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = { dataState.showSpeechTraining.value = true },
            onClickDelete = {
                action.ex(TrainingEvent.DelTraining(dataState.training))
                dataState.onBaskScreen.invoke()
            }
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}
