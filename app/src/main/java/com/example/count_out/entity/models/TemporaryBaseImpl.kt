package com.example.count_out.entity.models

import com.example.count_out.entity.workout.TemporaryBase

data class TemporaryBaseImpl(
    override val latitude: Double,
    override val longitude: Double,
    override val altitude: Double,
    override val timeLocation: Long,
    override val accuracy: Float,
    override val speed: Float,
    override val distance: Float,
    override val heartRate: Int,
    override val idTraining: Long,
    override val idSet: Long,
    override val phaseWorkout: Int,
    override val activityId: Long
): TemporaryBase
