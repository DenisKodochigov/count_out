package com.count_out.app.presentation.models

import com.count_out.domain.entity.DataForChangeSequence

data class DataForChangeSequenceImpl (
    override val trainingId: Long,
    override val roundId: Long,
    override val ringId: Long,
    override val from: Int,
    override val to: Int,
): DataForChangeSequence