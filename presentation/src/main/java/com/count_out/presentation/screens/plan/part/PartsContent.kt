package com.count_out.presentation.screens.plan.part

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.workout.Part
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.ring.Rings
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconSingle
import com.count_out.presentation.view_element.icons.IconsCollapsing

@Composable fun PartsContent(dataState: PlanState, part: Part){
    when(part.name){
        PartName.WorkUp -> ShowBottomSheetSpeech(dataState, dataState.showBS.workUp,part)
        PartName.WorkOut -> ShowBottomSheetSpeech(dataState, dataState.showBS.workOut,part)
        PartName.WorkDown -> ShowBottomSheetSpeech(dataState, dataState.showBS.workDown,part)
    }
    Frame(colorAlpha = 0.8f, contour = contourHor2){
        Column( modifier = Modifier.padding(start = 0.dp, bottom = 2.dp, top = 4.dp)){
            TitlePart(dataState = dataState, part = part)
            ListRing(dataState = dataState, part = part)
        }
    }
}
@Composable fun TitlePart(dataState: PlanState, part: Part){
    Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 6.dp)){
        IconsCollapsing(
            onClick = { setCollapsing(dataState, part) },
            wrap = getCollapsing(dataState, part) )
        Spacer(modifier = Modifier.width(2.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextApp(
                text = stringResource(id = EnumsTo(part.name).string()),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,)
            TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = "${ stringResource(id = R.string.exercises) }: ${part.amount}" +
                        " / ${part.duration.value.discard(2)} ${ stringResource(id = R.string.min)}",) }
        IconSingle(image = Icons.Default.GraphicEq, onClick = { showSpeechRound(dataState, part) } )
//        IconsGroup( onClickSpeech = { showSpeechRound(dataState, part) })
        Spacer(modifier = Modifier.width(6.dp))
    }
}
@Composable fun ListRing(dataState: PlanState, part: Part){
    if (getCollapsing(dataState, part)) Rings(dataState, part)
}
fun showSpeechRound(dataState: PlanState, part: Part){
    dataState.item = part
    dataState.event(ShowBS(dataState.showBS.copy(domain = part)))
}

