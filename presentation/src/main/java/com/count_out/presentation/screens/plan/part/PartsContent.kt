package com.count_out.presentation.screens.plan.part

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.enums.PartName
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.presentation.R
import com.count_out.presentation.models.Dimen.contourHor2
import com.count_out.presentation.screens.plan.CarcassTitle
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanEvent.ShowBS
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.ring.Rings
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.bottom_sheet.ShowBottomSheetSpeech
import com.count_out.presentation.view_element.custom_view.Frame
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

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
    CarcassTitle(
        onCollapsing = { IconsCollapsing(onClick = { setCollapsing(dataState, part) },
            wrap = getCollapsing(dataState, part) )},
        nameItem = { TextApp( text = stringResource(id = EnumsTo(part.name).string()),
            style = MaterialTheme.typography.headlineSmall,)},
        infoItem = { TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
            text = """${ stringResource(id = R.string.exercises) }: ${part.amount} / ${part.duration.value.discard(2)} ${ stringResource(id = R.string.min)}""",)},
        actionItem = {
            if (part.rings[0].amount > 1){
                IconsGroup(
                    onClickAddRing = { dataState.event(PlanEvent.CopyRing(ring = Ring.default(part.idPart)))},
                    onClickSpeech = { showSpeechRound(dataState, part) })
            } else {
                IconsGroup(
                    onClickAddExercise = {dataState.event(PlanEvent.AddExercise(Exercise.default(part.rings[0].idRing)))},
                    onClickSpeech = { showSpeechRound(dataState, part) })
            }
        }
    )
}
@Composable fun ListRing(dataState: PlanState, part: Part){
    if (getCollapsing(dataState, part)) Rings(dataState, part)
}
fun showSpeechRound(dataState: PlanState, part: Part){
    dataState.item = part
    dataState.event(ShowBS(dataState.showBS.copy(domain = part)))
}

