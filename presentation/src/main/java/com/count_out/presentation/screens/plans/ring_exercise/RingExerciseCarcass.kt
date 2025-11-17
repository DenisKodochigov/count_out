package com.count_out.presentation.screens.plans.ring_exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.models.Dimen.contourAll1
import com.count_out.presentation.screens.carcasses.CarcassTitle
import com.count_out.presentation.screens.plans.set.CarcassTuning
import com.count_out.presentation.view_element.custom_view.Frame

@Composable fun RingExerciseCarcass(
    getExpand: ()-> Boolean,
    setExpand: ()-> Unit,
    getSelect: (Domain)-> Boolean,
    nameItem: @Composable ()->Unit,
    infoItem: @Composable ()->Unit,
    actionItem: @Composable ()->Unit,
    nameItemLeftList: @Composable (Int)->String,
    listItem: List<Domain>,
    onClick: (Int)->Unit,
    textFieldTopSetBody: @Composable () -> Unit,
    actionTitle: @Composable () -> Unit,
    actionBottom: @Composable () -> Unit,
    setBody: @Composable () -> Unit,
    iconGoal: @Composable () -> Unit,
    iconZone: @Composable () -> Unit,
    onChangeSequence: ()-> Unit,
){
    Frame(colorAlpha = 0.6f, contour = contourAll1, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp)){
        Column {
            CarcassTitle(
                startIcon = { },
                onSetCollaps = setExpand,
                nameItem = nameItem,
                infoItem = infoItem,
                actionItem = { Box(modifier = Modifier.padding(end = 2.dp)){ actionItem() } },
                onChangeSequence = onChangeSequence,

            )
            AnimatedVisibility(modifier = Modifier.padding(horizontal = 0.dp), visible = getExpand()) {
                if ( listItem.isNotEmpty()) {
                    CarcassTuning(
                        button = {},
                        list = listItem,
                        getSelect = getSelect,
                        nameItemLeftList = { ind-> nameItemLeftList(ind)},
                        onClick = onClick,
                        onLongClick = onChangeSequence,
                        nameExercise = textFieldTopSetBody,
                        actionTitle = actionTitle,
                        setBody = setBody,
                        iconGoal = iconGoal,
                        iconZone = iconZone,
                        actionBottom = actionBottom,
                    )
                }
            }
        }
    }
}