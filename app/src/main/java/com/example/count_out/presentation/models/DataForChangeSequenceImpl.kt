package com.example.count_out.presentation.models

import com.example.domain.entity.DataForChangeSequence

data class DataForChangeSequenceImpl (
    override val trainingId: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val from: Int,
    override val to: Int,
): DataForChangeSequence