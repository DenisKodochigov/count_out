package com.example.count_out.ui.screens.training

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.count_out.entity.RoundType
import com.example.count_out.entity.TypeKeyboard
import com.example.count_out.entity.TypeText
import com.example.count_out.entity.no_use.Workout
import com.example.count_out.ui.bottomsheet.BottomSheetSpeech
import com.example.count_out.ui.theme.Dimen
import com.example.count_out.ui.theme.elevationTraining
import com.example.count_out.ui.theme.interBold16
import com.example.count_out.ui.theme.interLight12
import com.example.count_out.ui.theme.interReg14
import com.example.count_out.ui.theme.interThin12
import com.example.count_out.ui.theme.shapeAddExercise
import com.example.count_out.ui.theme.styleApp
import com.example.count_out.ui.view_components.TextApp
import com.example.count_out.ui.view_components.TextFieldApp
import com.example.count_out.ui.view_components.log

@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreen(
    trainingId: Long,
    onClickExercise: (Long, Long) -> Unit,
    onBaskScreen:() -> Unit
) {
    val viewModel: TrainingViewModel = hiltViewModel()
    viewModel.getTraining(trainingId)
    TrainingScreenCreateView(
        onClickExercise = onClickExercise,
        viewModel = viewModel,
        onBaskScreen = onBaskScreen)
}

@Composable
fun TrainingScreenCreateView(
    onClickExercise: (Long, Long) -> Unit,
    onBaskScreen:() -> Unit,
    viewModel: TrainingViewModel
) {
    val uiState by viewModel.trainingScreenState.collectAsState()
    uiState.onClickExercise =
        remember { { idRound, idExercise -> onClickExercise( idRound, idExercise) } }
    uiState.onBaskScreen = onBaskScreen
    uiState.onAddExercise = remember { { id -> onClickExercise(id, 0) } }

    EditSpeech(uiState)
    TrainingScreenLayout(uiState = uiState)
}

@Composable fun EditSpeech(uiState: TrainingScreenState){
    if (uiState.showSpeechTraining.value) {
        uiState.nameSection = stringResource(id = R.string.training)
        uiState.item = uiState.training
        uiState.onDismissSpeech = {
            log(true, "uiState.showSpeechTraining.value = false")
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
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TrainingScreenLayout( uiState: TrainingScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = Dimen.paddingAppHor),
    ) {
        Spacer(modifier = Modifier.height(Dimen.width8))
        NameTraining(uiState = uiState)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeRoundType = RoundType.UP)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeRoundType = RoundType.OUT)
        Spacer(modifier = Modifier.height(Dimen.width8))
        Round(uiState = uiState, typeRoundType = RoundType.DOWN)
    }
}

@Composable
fun Round(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Card(
        elevation = elevationTraining(), shape = MaterialTheme.shapes.extraSmall
    ) {
        Box(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Row1(uiState = uiState, typeRoundType)
                Row2(uiState = uiState)
                Row3(uiState = uiState, typeRoundType)
            }
        }

    }
}

@Composable
fun Row1(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
    {
        TextApp(text = stringResource(id = typeRoundType.strId), style = interReg14)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            onClick = { showSpeechRound(uiState, typeRoundType) }) {
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
            onClick = { setCollapsing(uiState, typeRoundType) }) {
            Icon(
                painter = painterResource(id = getIconCollapsing(uiState, typeRoundType)),
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun Row2( uiState: TrainingScreenState ) {
    val txtDuration = stringResource(id = R.string.duration) + ": " +
            uiState.durationRound.value.toString()
    TextApp(text = txtDuration, style = interLight12)
}

@Composable
fun Row3(uiState: TrainingScreenState, typeRoundType: RoundType)
{
    if (!getCollapsing(uiState, typeRoundType) || amountExercise(uiState, typeRoundType) == 0) {
        Row3Wrap(uiState, typeRoundType)
    } else {
        Row3UnWrap(uiState, typeRoundType)
    }
}

@Composable
fun Row3Wrap(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        val amountExercise = stringResource(id = R.string.exercises) + ": " +
                amountExercise(uiState, typeRoundType)
        TextApp(text = amountExercise, style = interLight12)
        Spacer(modifier = Modifier.weight(1f))
        PoleAddExercise(uiState = uiState, typeRoundType = typeRoundType)
    }
}

@Composable
fun Row3UnWrap(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Column {
        val amountExercise = stringResource(id = R.string.exercises) + ": " +
                amountExercise(uiState, typeRoundType)
        TextApp(text = amountExercise, style = interLight12)
        LazyExercise(uiState = uiState, typeRoundType = typeRoundType)
        PoleAddExercise(uiState = uiState, typeRoundType = typeRoundType)
    }
}

@Composable
fun NameTraining( uiState: TrainingScreenState ) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
    {
        TextFieldApp(
            enterValue = uiState.enteredName,
            textAlign = TextAlign.Start,
            typeKeyboard = TypeKeyboard.TEXT,
            textStyle = interBold16,
            onChangeValue = {
                uiState.changeNameTraining(uiState.training.idTraining, uiState.enteredName.value)}
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

fun amountExercise(uiState: TrainingScreenState, typeRoundType: RoundType): Int {
    return when (typeRoundType) {
        RoundType.UP -> uiState.workUp.value?.exercise?.size ?: 0
        RoundType.OUT -> uiState.workOut.value?.exercise?.size ?: 0
        RoundType.DOWN -> uiState.workDown.value?.exercise?.size ?: 0
    }
}

fun setCollapsing(uiState: TrainingScreenState, typeRoundType: RoundType) {
    if (amountExercise(uiState, typeRoundType) > 0) {
        when (typeRoundType) {
            RoundType.UP -> uiState.workUpCollapsing.value = !uiState.workUpCollapsing.value
            RoundType.OUT -> uiState.workOutCollapsing.value = !uiState.workOutCollapsing.value
            RoundType.DOWN -> uiState.workDownCollapsing.value = !uiState.workDownCollapsing.value
        }
    }
}
fun getCollapsing(uiState: TrainingScreenState, typeRoundType: RoundType): Boolean {
    return when (typeRoundType) {
        RoundType.UP -> uiState.workUpCollapsing.value
        RoundType.OUT -> uiState.workOutCollapsing.value
        RoundType.DOWN -> uiState.workDownCollapsing.value
    }
}
fun getIconCollapsing(uiState: TrainingScreenState, typeRoundType: RoundType): Int {
    return when (typeRoundType) {
        RoundType.UP -> getIcon(uiState.workUpCollapsing.value)
        RoundType.OUT -> getIcon(uiState.workOutCollapsing.value)
        RoundType.DOWN -> getIcon(uiState.workDownCollapsing.value)
    }
}
fun showSpeechRound(uiState: TrainingScreenState, typeRoundType: RoundType) {
    when (typeRoundType) {
        RoundType.UP -> uiState.showSpeechWorkUp.value = true
        RoundType.OUT -> uiState.showSpeechWorkOut.value = true
        RoundType.DOWN -> uiState.showSpeechWorkDown.value = true
    }
}

fun getIcon(collapsing: Boolean): Int = if (collapsing) R.drawable.ic_wrap1 else R.drawable.ic_wrap

fun getIdRound(uiState: TrainingScreenState, typeRoundType: RoundType): Long {
    return uiState.training.rounds.find { it.roundType == typeRoundType }?.idRound ?: 0
}
@Composable
fun PoleAddExercise(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { uiState.onAddExercise(getIdRound(uiState, typeRoundType)) }
            .background(color = MaterialTheme.colorScheme.secondary, shape = shapeAddExercise)
    ) {
        TextApp(
            text = stringResource(id = R.string.add_activity),
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

@Composable
fun LazyExercise(uiState: TrainingScreenState, typeRoundType: RoundType) {
    Row(modifier = Modifier.clickable { getIdRound(uiState,typeRoundType) }) {
//        TextApp(text = stringResource(id = R.string.add_activity), style = interThin12)
//        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "")
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TrainingLazyColumn(
    uiState: TrainingScreenState,
) {
    Spacer(modifier = Modifier.height(2.dp))
    LazyList(uiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyList(uiState: TrainingScreenState) {
    val listState = rememberLazyListState()
    val listItems = uiState.training
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.testTag("1")
    )
    {
//        items( items = listItems, key = { it.idWorkout })
//        { item ->
//            ItemSwipe(
//                frontView = {
//                    RowLazy(item, uiState, modifier = Modifier.animateItemPlacement()) },
//                actionDragLeft = { uiState.deleteWorkout( item.idWorkout )},
//                actionDragRight = { uiState.editWorkout(item) },
//            )
//        }
    }
}

@Composable
fun RowLazy(item: Workout, uiState: TrainingScreenState, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        IconStart(item = item, uiState = uiState)
        Spacer(modifier = Modifier.width(Dimen.width8))
        NameWorkout(item = item, uiState = uiState)
        Spacer(
            modifier = Modifier
                .width(Dimen.width8)
                .weight(1f)
        )
        IconEnd(item = item, uiState = uiState)
    }
}

@Composable
fun IconStart(item: Workout, uiState: TrainingScreenState, modifier: Modifier = Modifier) {
//    IconButton(onClick = { uiState.onSelect(item)}) {
//        Icon(imageVector = Icons.Default.CheckCircleOutline, contentDescription = "")}
}

@Composable
fun IconEnd(item: Workout, uiState: TrainingScreenState, modifier: Modifier = Modifier) {
//    IconButton(onClick = { uiState.onOtherAction(item)}) {
//        Icon(imageVector = Icons.Default.BlurOn, contentDescription = "")}
}

@Composable
fun NameWorkout(item: Workout, uiState: TrainingScreenState) {
    TextApp(
        text = item.name,
        style = styleApp(nameStyle = TypeText.TEXT_IN_LIST),
    )
//        modifier = Modifier.clickable { uiState.onSelectItem(item) })
}

@Preview(showBackground = true)
@Composable
fun TrainingScreenLayoutPreview() {
    TrainingScreenLayout(TrainingScreenState())
}
