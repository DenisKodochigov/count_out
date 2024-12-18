package com.example.count_out.entity.workout

data class ListEntityTraining(
    val list: List<EntityTraining> = emptyList<EntityTraining>(),
    val roundCount: Int = 0,
    val exerciseCount: Int = 0,
)
