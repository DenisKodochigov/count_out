package com.example.data.entity

import com.example.domain.entity.Set
import com.example.domain.entity.enums.DistanceUnit
import com.example.domain.entity.enums.Goal
import com.example.domain.entity.enums.TimeUnit
import com.example.domain.entity.enums.WeightUnit
import com.example.domain.entity.enums.Zone

data class SetImpl(
    override val idSet: Long,
    override val name: String,
    override val exerciseId: Long,
    override val speechId: Long,
    override val speech: SpeechKitImpl?,
    override val goal: Goal,
    override val weight: Int,
    override val weightU: WeightUnit,
    override val distance: Double,
    override val distanceU: DistanceUnit,
    override val duration: Int,
    override val durationU: TimeUnit,
    override val reps: Int,
    override val intensity: Zone,
    override val intervalReps: Double,
    override val intervalDown: Int,
    override val groupCount: String,
    override val rest: Int,
    override val timeRestU: TimeUnit,
): Set
