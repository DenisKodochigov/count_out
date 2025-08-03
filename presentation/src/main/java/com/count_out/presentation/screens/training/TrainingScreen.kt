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
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen
import com.count_out.presentation.models.TrainingImplP
import com.count_out.presentation.models.TypeKeyboard
import com.count_out.presentation.screens.execute.ExecuteEvent
import com.count_out.presentation.screens.execute.ExecuteState
import com.count_out.presentation.screens.prime.PrimeScreen
import com.count_out.presentation.screens.training.TrainingEvent.ShowBS
import com.count_out.presentation.screens.training.round.Round
import com.count_out.presentation.view_element.TextFieldApp
import com.count_out.presentation.view_element.TopBarApp
import com.count_out.presentation.view_element.bottom_sheet.BottomSheetSpeech
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun TrainingScreen(viewModel: TrainingViewModel, trainingId: Long){
    LaunchedEffect(Unit) { viewModel.submitEvent(TrainingEvent.GetTraining(trainingId)) }
    TrainingScreenCreateView( viewModel = viewModel )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable fun TrainingScreenCreateView( viewModel: TrainingViewModel){
    viewModel.screenState.collectAsState().value.let { screenState ->
        PrimeScreen(loader = screenState) { dataState ->
            EditSpeech(dataState = dataState)
            TrainingScreenLayout(dataState)
        }
    }
}
@Composable fun EditSpeech(dataState: TrainingState) {
    if (dataState.showBS.training) {
        dataState.nameSection = stringResource(id = R.string.training)
        dataState.item = dataState.training
        dataState.onDismissSpeech =
            { dataState.event(ShowBS(dataState.showBS.copy(element = dataState.training))) }
        BottomSheetSpeech(dataState)
    }
}

@Composable fun TrainingScreenLayout(dataState: TrainingState){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimen.paddingAppHor)
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true)
            },
    ){
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(dataState = dataState)
        dataState.training?.rounds?.forEach { round ->
            Spacer(modifier = Modifier.height(Dimen.width8))
            Round(dataState = dataState, round = round)
        }
    }
}
@Composable fun TopBar(dataState: ExecuteState){
    TopBarApp(
        text = stringResource(R.string.training_text_fab) + (dataState.stepTraining?.namePlan ?: ""),
        selected = true,
        onClickText = { dataState.event(ExecuteEvent.ToScreenPlans) } ,
    )
}

@Composable fun NameTraining(dataState: TrainingState) {

    val enteredName: MutableState<String> = remember { mutableStateOf(dataState.training?.name ?: "") }

    if (dataState.training?.idTraining == 0L) return
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp))
    {
        TextFieldApp(
            placeholder = enteredName.value,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
            edit = true,
            colorLine = MaterialTheme.colorScheme.outline,
            onChangeValue = {
                enteredName.value = it
                dataState.training?.let {
                    dataState.event( TrainingEvent.UpdateTraining(TrainingImplP(it, enteredName.value))) }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        IconsGroup(
            onClickSpeech = {
                dataState.event(ShowBS(dataState.showBS.copy(element = dataState.training))) },
            onClickDelete = {
                dataState.training?.let { dataState.event(TrainingEvent.DelTraining(dataState.training))}
                dataState.onBaskScreen.invoke()
            }
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}
