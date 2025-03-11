package com.count_out.data.router.models

import com.count_out.domain.entity.Coordinate

data class TemporaryBase (
    override val latitude: Double,
    override val longitude: Double,
    override val altitude: Double,
    override val timeLocation: Long,
    override val accuracy: Float,
    override val speed: Float,
    override val distance: Float,
    val heartRate: Int,
    val idTraining: Long,
    val idSet: Long,
    val phaseWorkout: Int,
    val activityId: Long,
): Coordinate