package com.count_out.presentation.screens.plan.part

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.presentation.R
import com.count_out.presentation.screens.plan.PlanEvent
import com.count_out.presentation.screens.plan.PlanState
import com.count_out.presentation.screens.plan.getCollapsing
import com.count_out.presentation.screens.plan.ring.Rings
import com.count_out.presentation.screens.plan.setCollapsing
import com.count_out.presentation.screens.plan.showSpeech
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.icons.IconsCollapsing
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun PartContent(dataState: PlanState, part: Part){
    CarcassParts(
        onCollapsing = { IconsCollapsing(onClick = { setCollapsing(dataState, part) },
            wrap = getCollapsing(dataState, part) )},
        nameItem = { TextApp( text = stringResource(id = EnumsTo(part.name).string()),
            style = MaterialTheme.typography.headlineSmall,)},
        infoItem = { TextApp( style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
            text = """${ stringResource(id = R.string.exercises) }: ${part.amount} / ${part.duration.value.discard(2)} ${ stringResource(id = R.string.min)}""",)},
        actionItem = {
            if (part.rings[0].amount > 1){
                IconsGroup(
                    onRingAdd = { dataState.event(PlanEvent.CopyRing(ring = Ring.default(part.idPart)))},
                    onSpeech = { showSpeech(dataState, part) })
            } else {
                IconsGroup(
                    onExerciseAdd = {dataState.event(PlanEvent.AddExercise(Exercise.default(part.rings[0].idRing)))},
                    onSpeech = { showSpeech(dataState, part) })
            }
        },
        listRing = { if (getCollapsing(dataState, part)) Rings(dataState, part) }
    )
}


