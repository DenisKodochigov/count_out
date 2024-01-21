package com.example.count_out.ui.screens.training

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.ui.bottomsheet.BottomSheetSelectActivity
import com.example.count_out.ui.bottomsheet.BottomSheetSpeech
import com.example.count_out.ui.screens.exercise.view_component.SelectActivity
import com.example.count_out.ui.screens.exercise.view_component.getIcon
import com.example.count_out.ui.screens.training.exercise.LazySets
import com.example.count_out.ui.screens.training.exercise.RowAddSet
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.view_components.IconCollapsingSpeech
import com.example.count_out.ui.view_components.TextApp
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
        uiState.onDismissSpeech = {
//            log(true, "uiState.showSpeechTraining.value = false")
            uiState.showSpeechTraining.value = false}
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
    if (uiState.showSpeechExercise.value != null) {
        uiState.nameSection = stringResource(id = R.string.exercise)
        uiState.item = uiState.showSpeechExercise.value
        uiState.onDismissSpeech = { uiState.showSpeechExercise.value = null}
        BottomSheetSpeech(uiState)
    }
    if (uiState.showSpeechSet.value != null) {
        uiState.nameSection = stringResource(id = R.string.set)
        uiState.item = uiState.showSpeechSet.value
        uiState.onDismissSpeech = { uiState.showSpeechSet.value = null}
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
fun Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ){
        Box() //modifier = Modifier.background(color = MaterialTheme.colorScheme.primary))
        {
            Column( modifier = Modifier.padding(start = 6.dp),)
            {
                Row1Round(uiState = uiState, roundType)
                Row2Round(uiState = uiState, roundType)
                Row3Round(uiState = uiState, roundType)
            }
        }
    }
}
@Composable
fun Row1Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, end = 8.dp)
    )
    {
        TextApp(text = nameRound( uiState, roundType, stringResource(id = roundType.strId)), style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconCollapsingSpeech(
            idIconCollapsing = getIconCollapsing(uiState, roundType),
            onCollapsing = { setCollapsing(uiState, roundType) },
            onSpeech = {  showSpeechRound(uiState, roundType) }
        )
    }
}
fun nameRound(uiState: TrainingScreenState, roundType: RoundType, name: String): String{
    return "$name: ${(uiState.training.rounds.find { it.roundType == roundType }?.idRound ?: 0)}"
}
@Composable
fun Row2Round( uiState: TrainingScreenState, roundType: RoundType) {
    val amountExercise = stringResource(id = R.string.exercises) + ": " + amountExercise(uiState, roundType)
    val txtDuration = stringResource(id = R.string.duration) + ": " + uiState.durationRound.value.toString()
    Row(modifier = Modifier.padding(end = 8.dp)) {
        TextApp(text = amountExercise, style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        TextApp(text = txtDuration, style = interLight12)
    }
}
@Composable
fun Row3Round(uiState: TrainingScreenState, roundType: RoundType)
{
    Column{
        ListExercises(
            uiState = uiState,
            roundType = roundType,
            showExercises = getCollapsing(uiState, roundType) && amountExercise(uiState, roundType) > 0)
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
        {
            Spacer(modifier = Modifier.weight(1f))
            PoleAddExercise(uiState = uiState, roundType = roundType)
        }
    }
}
@Composable
fun ListExercises(uiState: TrainingScreenState, roundType: RoundType, showExercises: Boolean)
{
    val listExercise = uiState.training.rounds.find { it.roundType == roundType }?.exercise ?: emptyList()
    listExercise.forEach { exercise ->
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = showExercises
        ){
            Card( elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
            ){
                Column {
                    SelectActivity(uiState, exercise)
                    BodyExercise(uiState, exercise)
                }
            }
        }
    }
}

//@Composable fun HeaderExercise(uiState: TrainingScreenState, exercise: Exercise){
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier
//    ){
//        Row(verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .weight(1f)
//                .padding(horizontal = 12.dp, vertical = 8.dp),
////                .clickable { uiState.onClickExercise(getIdRound(uiState, roundType), itExercise.idExercise) },
//            content = { TextApp(text = exercise.activity.name, style = interLight12) }
//        )
//        IconsCopySpeechDel(
//            onCopy = { uiState.onCopyExercise(uiState.training.idTraining, exercise.idExercise)},
//            onDel = { uiState.onDeleteExercise(uiState.training.idTraining, exercise.idExercise) },
//            onSpeech = { uiState.onSpeechExercise(exercise.idExercise) })
//    }
//}
@Composable fun BodyExercise(uiState: TrainingScreenState, exercise: Exercise){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ){
        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = true
        ){
            Column (modifier = Modifier.fillMaxWidth().padding(6.dp), content = {LazySets(uiState)})
            RowAddSet(uiState)
        }
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
fun amountExercise(uiState: TrainingScreenState, roundType: RoundType): Int
{
    return uiState.training.rounds.find{ it.roundType == roundType }?.exercise?.size ?: 0
}
fun setCollapsing(uiState: TrainingScreenState, roundType: RoundType)
{
    if (amountExercise(uiState, roundType) > 0) {
        when (roundType) {
            RoundType.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            RoundType.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            RoundType.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
    }
}
fun getCollapsing(uiState: TrainingScreenState, roundType: RoundType): Boolean
{
    return when (roundType) {
        RoundType.UP -> uiState.workUpCollapsing.value
        RoundType.OUT -> uiState.workOutCollapsing.value
        RoundType.DOWN -> uiState.workDownCollapsing.value
    }
}
fun getIconCollapsing(uiState: TrainingScreenState, roundType: RoundType): Int
{
    return when (roundType) {
        RoundType.UP -> getIcon(uiState.workUpCollapsing.value)
        RoundType.OUT -> getIcon(uiState.workOutCollapsing.value)
        RoundType.DOWN -> getIcon(uiState.workDownCollapsing.value)
    }
}
fun showSpeechRound(uiState: TrainingScreenState, roundType: RoundType)
{
    when (roundType) {
        RoundType.UP -> uiState.showSpeechWorkUp.value = true
        RoundType.OUT -> uiState.showSpeechWorkOut.value = true
        RoundType.DOWN -> uiState.showSpeechWorkDown.value = true
    }
}

fun getIdRound(uiState: TrainingScreenState, roundType: RoundType): Long
{
    return uiState.training.rounds.find { it.roundType == roundType }?.idRound ?: 0
}
@Composable
fun PoleAddExercise(uiState: TrainingScreenState, roundType: RoundType)
{
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { uiState.onAddExercise(getIdRound(uiState, roundType)) }
//            .background(color = MaterialTheme.colorScheme.secondary, shape = shapeAddExercise)
    ) {
        TextApp(
            text = stringResource(id = R.string.add_activity),
            style = interThin12,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "",
            modifier = Modifier.padding(4.dp).size(Dimen.sizeIcon)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingScreenLayoutPreview() {
    TrainingScreenLayout(TrainingScreenState())
}
