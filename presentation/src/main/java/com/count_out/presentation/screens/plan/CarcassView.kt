package com.count_out.presentation.screens.plan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Domain
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.borderMy


@Composable fun CarcassTitle(
    onCollapsing: @Composable () ->Unit,
    nameItem: @Composable () ->Unit,
    infoItem: @Composable () ->Unit,
    actionItem: @Composable () ->Unit,
){
    Row( verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()){
        onCollapsing()
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row( content = { nameItem() })
            infoItem()
        }
        actionItem()
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun CarcassLeftList(
    dataState: PlanState,
    list: List<Domain>,
    textItem: @Composable (Int)-> String,
    onLongClick: (Domain)-> Unit = {},
){
    Column {
        list.forEachIndexed { ind, item ->
            CarcassLeftListItems(dataState = dataState,
                item = item,
                text = textItem(ind),
                onLongClick = { println("Долгое нажатие")},
                onClick = { setSelecting( dataState,item,list) })
        }
    }
}

@Composable fun CarcassLeftListItems(
    dataState: PlanState,
    item: Domain,
    text: String,
    onLongClick: () ->Unit,
    onClick: () ->Unit,
){
    val selected = getSelecting(dataState, item)
    Row( horizontalArrangement = Arrangement.Start,
        modifier= Modifier
            .borderMy( selected, color = colorScheme.surfaceContainerLow)
            .combinedClickable(onLongClick = onLongClick, onClick = onClick ,)
    ) {
        TextApp( modifier = Modifier.width(60.dp).padding(horizontal = 2.dp),
            textAlign = TextAlign.Center,
            text = text,
            style = typography.titleMedium)
    }
}

@Composable fun CarcassPlanScreen(
    namePlanField: @Composable ()->Unit,
    listPlans: @Composable ()->Unit,
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
        listPlans()
    }
}
