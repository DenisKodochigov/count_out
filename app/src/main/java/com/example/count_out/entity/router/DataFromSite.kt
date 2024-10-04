package com.example.count_out.entity.router

import com.example.count_out.entity.Coordinate
import kotlinx.coroutines.flow.MutableStateFlow

data class DataFromSite (
    val coordinate: MutableStateFlow<Coordinate?> = MutableStateFlow( null),
)
