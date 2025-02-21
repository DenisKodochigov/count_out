package com.count_out.domain.entity.router.models

import com.count_out.app.entity.workout.Coordinate
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromSite (
    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
)
