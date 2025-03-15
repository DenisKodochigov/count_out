package com.count_out.presentation.screens.training

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.enums.RoundType
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TrainingImpl
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.prime.Action
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.screens.training.TrainingEvent.ShowBS
import com.count_out.presentation.screens.training.round.Round
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSelectActivity
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSpeech
import com.count_out.presentation.view_element.icons.IconsGroup

@SuppressLint("UnrememberedMutableState")
@Composable fun TrainingScreen(viewModel: TrainingViewModel, trainingId: Long){
    LaunchedEffect(Unit) { viewModel.submitEvent(TrainingEvent.GetTraining(trainingId)) }
    TrainingScreenCreateView( viewModel = viewModel )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun TrainingScreenCreateView( viewModel: TrainingViewModel){

    val action = Action {viewModel.submitEvent(it) }
    viewModel.dataState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
//            dataState.onDismissSelectActivity =
//                { dataState.showBottomSheetSelectActivity = false}
            EditSpeech(dataState = dataState, action = action)
            TrainingScreenLayout(dataState, action = action)
        }
    }
}
@Composable fun EditSpeech(dataState: TrainingState, action: Action) {
    if (dataState.showBS.training) {
        dataState.nameSection = stringResource(id = R.string.training)
        dataState.item = dataState.training
        dataState.onDismissSpeech =
            { action.ex(ShowBS(dataState.showBS.copy(element = dataState.training))) }
        BottomSheetSpeech(dataState)
    }
}
@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreenLayout(dataState: TrainingState, action: Action){
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
        dataState.training?.rounds?.forEach { round ->
            Spacer(modifier = Modifier.height(Dimen.width8))
            Round(dataState = dataState, action = action, round = round)
        }
    }
}
@Composable
fun NameTraining(dataState: TrainingState, action: Action) {
    val enteredName: MutableState<String> = remember { mutableStateOf("") }

    if (dataState.training?.idTraining == 0L) return
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp))
    {
        TextFieldApp(
            placeholder = enteredName.value,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
            edit = true,
            colorLine = MaterialTheme.colorScheme.outline,
            onChangeValue = {
                enteredName.value = it
                action.ex( TrainingEvent.UpdateTraining(
                    (dataState.training as TrainingImpl).copy(name = enteredName.value)))
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = {
                action.ex(ShowBS(dataState.showBS.copy(element = dataState.training))) },
            onClickDelete = {
                dataState.training?.let { action.ex(TrainingEvent.DelTraining(dataState.training))}
                dataState.onBaskScreen.invoke()
            }
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}
