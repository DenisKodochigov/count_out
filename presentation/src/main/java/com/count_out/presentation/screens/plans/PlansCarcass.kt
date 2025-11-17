package com.count_out.presentation.screens.plans

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.workout.Plan
import com.count_out.presentation.screens.carcasses.CarcassTitle

@Composable fun PlansCarcass(
    planList: List<Plan>,
    topBar: @Composable ()->Unit,
    startIcon: @Composable (Modifier, Plan)->Unit,
    namePlan: @Composable (String)->Unit,
    infoPlan: @Composable (Int)->Unit,
    initSelecting: @Composable (Plan)->Unit,
    actionItem: @Composable (Plan)->Unit,
    getCollaps: (Plan)-> Boolean,
    setCollaps: (Plan)-> Unit,
    onChangeSequence: ()-> Unit,
    listPart: @Composable (Plan)->Unit,
){
    Column(modifier = Modifier.fillMaxSize()) {
        topBar()
        Spacer(modifier = Modifier.fillMaxWidth())
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.weight(1f).testTag("1").animateContentSize()
        ) {
            items(planList) { item ->
                initSelecting(item)
                Card( shape = MaterialTheme.shapes.small,
//                    colors = CardColors(
//                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
//                        contentColor = MaterialTheme.colorScheme.primary,
//                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
//                        disabledContentColor = MaterialTheme.colorScheme.primary
//                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Column{
                        CarcassTitle (
                            startIcon = { modifier-> startIcon( modifier, item ) },
                            nameItem = { namePlan(item.name) },
                            infoItem = { infoPlan( item.amountActivity) },
                            actionItem = {
                                Box(modifier = Modifier.padding(end = 10.dp)){ actionItem(item) }},
                            onSetCollaps = { setCollaps(item)},
                            onChangeSequence = { onChangeSequence() }
                        )
                        AnimatedVisibility( visible = getCollaps(item)) {
                            Column(modifier = Modifier.padding(start = 0.dp)
                            ) { listPart(item) }
                        }
                    }
                }
//                Frame(contour = contourAll2, modifier = Modifier.padding(bottom = 12.dp)) {
//                    Column{
//                        CarcassTitle (
//                            startIcon = { modifier-> startIcon( modifier, item ) },
//                            nameItem = { namePlan(item.name) },
//                            infoItem = { infoPlan( item.amountActivity) },
//                            actionItem = {
//                                Box(modifier = Modifier.padding(end = 10.dp)){ actionItem(item) }},
//                            onSetCollaps = { setCollaps(item)},
//                            onChangeSequence = { onChangeSequence() }
//                        )
//                        AnimatedVisibility( visible = getCollaps(item)) {
//                            Column(modifier = Modifier.padding(start = 0.dp)
//                            ) { listPart(item) }
//                        }
//                    }
//                }
            }
        }
    }
}

//                        Row(
//                            horizontalArrangement = Arrangement.Start,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.animateItem().fillMaxWidth().padding(vertical = 6.dp)
//                        ) {
//                            Spacer(modifier = Modifier.width(12.dp))
//                            iconRun(item )
//                            Spacer(modifier = Modifier.width(16.dp))
//                            Column(modifier = Modifier.weight(1f).clickable { onClickPlanInfo()}) {
//                                namePlan(item.name)
//                                Spacer(modifier = Modifier.height(Dimen.height4))
//                                infoPlan(item.amountActivity)
//                            }
//                            Spacer(modifier = Modifier.width(Dimen.width6))
//                            iconsGroup(item)
//                            Spacer(modifier = Modifier.width(Dimen.width6))
//                        }