package com.example.count_out.ui.screens.exercise.view_component

//@Composable
//fun SetEdit(uiState: ExerciseScreenState, set: Set)
//{
//    Spacer(modifier = Modifier.height(Dimen.width4))
//    NameSet(uiState,set)
//    AdditionalInformation(uiState,set)
//    Spacer(modifier = Modifier.height(Dimen.width4))
//}
//@Composable
//fun NameSet(uiState: ExerciseScreenState, set: Set)
//{
//    val distance = if (set.distance > 0.0) "( ${set.distance} " + stringResource(id = R.string.km) + " )" else ""
//    val duration = if (set.duration > 0)  "(${set.duration} " + stringResource(id = R.string.min) + ")" else ""
//
//    Row (verticalAlignment = Alignment.CenterVertically){
//        TextApp(
//            text = "${set.idSet}: ",
//            style = interReg14,
//            textAlign = TextAlign.Start,
//            modifier = Modifier.padding(start = 8.dp))
//        TextFieldApp(
//            modifier =Modifier,
//            placeholder = set.name,
//            contentAlignment = Alignment.BottomStart,
//            typeKeyboard = TypeKeyboard.TEXT,
//            textStyle = interReg14,
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(name = it)) })
//        TextApp(
//            text = if (set.distance > 0) distance else duration,
//            style = interReg14,
//            textAlign = TextAlign.Start,)
//        Spacer(modifier = Modifier.weight(1f))
//        GroupIcons4(
//            onCopy = { /*TODO*/ },
//            onSpeech = { /*TODO*/ },
//            onDel = { /*TODO*/ },
//            onCollapsing = { setCollapsing(uiState, set) },
//            wrap = uiState.listCollapsingSet.value.find { it == set.idSet } != null
//        )
//    }
//}
//@Composable
//fun AdditionalInformation(uiState: ExerciseScreenState, set: Set)
//{
//    val visibleLazy = uiState.listCollapsingSet.value.find { it == set.idSet } != null
//    Column(modifier = Modifier
//        .testTag("1")
//        .padding(horizontal = 8.dp))
//    {
//        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visibleLazy
//        ){
//            Column{
//                Spacer(modifier = Modifier.height(8.dp))
//                TextApp(text = stringResource(id = R.string.task_in_approach), style = interReg14)
//                Spacer(modifier = Modifier.height(8.dp))
//                SwitchDuration(uiState, set)
//                Spacer(modifier = Modifier.height(8.dp))
//                TextStringAndField(
//                    placeholder = set.timeRest.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(timeRest = it.toInt())) },
//                    editing = visibleLazy,
//                    text = stringResource(id = R.string.time_to_rest) + " (" + stringResource(id = R.string.sec) + "): ",)
//            }
//        }
//    }
//}
//@Composable
//fun SwitchDuration(uiState: ExerciseScreenState, set: Set)
//{
//    val state = remember { mutableIntStateOf(1) }
//    Column(
//        Modifier
//            .selectableGroup()
//            .padding(start = 12.dp))
//    {
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 1,
//            state = state.intValue,
//            onClick = { state.intValue = 1},
//            context = { RadioButtonDistance(uiState = uiState, set = set, visible = state.intValue == 1)}
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 2,
//            state = state.intValue,
//            onClick = { state.intValue = 2},
//            context = { RadioButtonDuration(uiState = uiState, set = set, visible = state.intValue == 2) }
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 3,
//            state = state.intValue,
//            onClick = { state.intValue = 3},
//            context = { RadioButtonCount(uiState = uiState, set = set, visible = state.intValue == 3)}
//        )
//    }
//}
//@Composable
//fun RadioButtonDistance(uiState: ExerciseScreenState, set: Set, visible: Boolean)
//{
//    Column {
//        TextStringAndField(
//            placeholder = set.distance.toString(),
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(distance = it.toDouble())) },
//            editing = true,
//            text = stringResource(id = R.string.distance) + " (" + stringResource(id = R.string.km) + "): ")
//        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
//        ){
//            TextStringAndField(
//                placeholder = set.intensity,
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intensity = it)) },
//                editing = true,
//                text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
//        }
//    }
//
//}
//@Composable
//fun RadioButtonDuration(uiState: ExerciseScreenState, set: Set, visible: Boolean)
//{
//    Column {
//        TextStringAndField(
//            placeholder = set.duration.toString(),
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(duration = it.toInt())) },
//            editing = true,
//            text = stringResource(id = R.string.duration) + " (" + stringResource(id = R.string.min) + "): ")
//        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
//        ){
//            Row{
//                TextStringAndField(
//                    placeholder = set.intensity,
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intensity = it)) },
//                    editing = true,
//                    text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
//                Spacer(modifier = Modifier.width(12.dp))
//                TextStringAndField(
//                    placeholder = set.weight.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toInt())) },
//                    editing = true,
//                    text = stringResource(id = R.string.weight) + " (" + stringResource(id = R.string.kg) + "): ")
//            }
//        }
//    }
//}
//@Composable
//fun RadioButtonCount(uiState: ExerciseScreenState, set: Set, visible: Boolean)
//{
//    Column {
//        TextStringAndField(
//            placeholder = set.reps.toString(),
//            onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(reps = it.toInt())) },
//            editing = true,
//            text = stringResource(id = R.string.quantity_reps) )
//        AnimatedVisibility(modifier = Modifier.padding(4.dp), visible = visible
//        ){
//            Column{
//                TextStringAndField(
//                    placeholder = set.weight.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(weight = it.toInt())) },
//                    editing = true,
//                    text = stringResource(id = R.string.intensity) + " (" + stringResource(id = R.string.zone) + "): ")
//                TextStringAndField(
//                    placeholder = set.intervalReps.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalReps = it.toInt())) },
//                    editing = true,
//                    text = stringResource(id = R.string.time_between_counts) + " (" + stringResource(id = R.string.sec) + "): ")
//                TextStringAndField(
//                    placeholder = set.intervalDown.toString(),
//                    onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(intervalDown = it.toInt())) },
//                    editing = true,
//                    text = stringResource(id = R.string.slowing_down_counts) )
//                SwitchCount(uiState, set)
//            }
//        }
//    }
//}
//@Composable
//fun SwitchCount(uiState: ExerciseScreenState, set: Set)
//{
//    val state = remember { mutableIntStateOf(1) }
//    Column(
//        Modifier
//            .selectableGroup()
//            .padding(start = 12.dp))
//    {
//        Spacer(modifier = Modifier.height(6.dp))
//        RadioButtonApp(
//            radioButtonId = 1,
//            state = state.intValue,
//            onClick = { state.intValue = 1},
//            context = { RadioButtonCountingOrder()}
//        )
//        Spacer(modifier = Modifier.height(4.dp))
//        RadioButtonApp(
//            radioButtonId = 2,
//            state = state.intValue,
//            onClick = { state.intValue = 2},
//            context = { RadioButtonCountingGroup(uiState = uiState, set = set, visible = state.intValue == 2)}
//        )
//    }
//}
//@Composable
//fun RadioButtonCountingOrder()
//{
//    TextApp(
//        text = stringResource(id = R.string.counting_in_order),
//        style = interLight12,
//        modifier = Modifier.padding(vertical = 2.dp))
//}
//@Composable
//fun RadioButtonCountingGroup(uiState: ExerciseScreenState, set: Set, visible: Boolean)
//{
//    Column{
//        TextAppLines(
//            text = stringResource(id = R.string.counts_by_group),
//            style = interLight12,
//            modifier = Modifier.padding(vertical = 2.dp))
//        AnimatedVisibility(modifier = Modifier.padding(bottom = 4.dp), visible = visible
//        ){
//            TextFieldApp(
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = set.groupCount,
//                contentAlignment = Alignment.BottomStart,
//                typeKeyboard = TypeKeyboard.TEXT,
//                textStyle = interLight12,
//                onChangeValue = { uiState.onChangeSet ((set as SetDB).copy(groupCount = it)) })
//        }
//    }
//}
//fun setCollapsing(uiState: ExerciseScreenState,  set: Set): Boolean
//{
//    val listCollapsingSet = uiState.listCollapsingSet.value.toMutableList()
//    val itemList = listCollapsingSet.find { it == set.idSet }
//    return if ( itemList != null) {
//        listCollapsingSet.remove(itemList)
//        uiState.listCollapsingSet.value = listCollapsingSet
//        false
//    } else {
//        listCollapsingSet.add(set.idSet)
//        uiState.listCollapsingSet.value = listCollapsingSet
//        true
//    }
//}