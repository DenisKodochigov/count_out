package com.count_out.domain.entity.router

import com.count_out.domain.entity.location.Coordinate
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromSite (
    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow(null),
)
