package com.count_out.presentation.screens.plans.plan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable fun CarcassPlanScreen(
    namePlanField: @Composable ()->Unit,
    partList: @Composable ()->Unit,
    iconsActionPlan: @Composable ()->Unit,
){
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus(true) },
    ){
        Row( verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 6.dp))
        {
            namePlanField()
            Spacer(modifier = Modifier.weight(1f))
            iconsActionPlan()
            Spacer(modifier = Modifier.width(7.dp))
        }
        partList()
    }
}
