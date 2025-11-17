package com.count_out.presentation.screens.plans.part

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.count_out.domain.entity.discard
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Ring
import com.count_out.presentation.R
import com.count_out.presentation.screens.plans.getCollapsing
import com.count_out.presentation.screens.plans.model.PlansEvent
import com.count_out.presentation.screens.plans.model.PlansState
import com.count_out.presentation.screens.plans.ring_exercise.RingContent
import com.count_out.presentation.screens.plans.setCollapsing
import com.count_out.presentation.screens.plans.showChangeOrder
import com.count_out.presentation.screens.plans.showSpeech
import com.count_out.presentation.view_element.EnumsTo
import com.count_out.presentation.view_element.TextApp
import com.count_out.presentation.view_element.icons.IconsGroup

@Composable fun PartContent(dataState: PlansState, part: Part){
    PartCarcass(
        setCollaps = { setCollapsing(dataState, part)},
        getCollaps = { getCollapsing(dataState, part)},
        nameItem = { TextApp(text = stringResource(id = EnumsTo(part.name).string()),
                style = MaterialTheme.typography.headlineSmall) },
        infoItem = { TextApp(style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light,
                text = """${stringResource(id = R.string.exercises)}: ${part.amount} / ${
                    part.duration.value.discard(2)} ${stringResource(id = R.string.min)}""",) },
        actionItem = {
            if (part.rings.isNotEmpty()){
                if (part.rings[0].amount > 1) {
                    IconsGroup(
                        onRingAdd = { dataState.event(PlansEvent.CopyRing(ring = Ring.default(part.idPart))) },
                        onSpeech = { showSpeech(dataState, part) })
                } else {
                    IconsGroup(
                        onExerciseAdd = { dataState.event(PlansEvent.AddExercise(Exercise.default(part.rings[0].idRing))) },
                        onSpeech = { showSpeech(dataState, part) })
                }
            }
        },
        onChangeSequence = {showChangeOrder(dataState,Ring.EMPTY,part.rings,part.idPart)},
        listRing = { part.rings.forEachIndexed { index, ring ->  RingContent(dataState, ring, index) }}
    )
}


