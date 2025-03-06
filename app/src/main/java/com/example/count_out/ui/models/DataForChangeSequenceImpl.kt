package com.example.count_out.ui.models

import com.example.count_out.entity.workout.DataForChangeSequence

data class DataForChangeSequenceImpl (
    override val trainingId: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val from: Int,
    override val to: Int,
): DataForChangeSequence