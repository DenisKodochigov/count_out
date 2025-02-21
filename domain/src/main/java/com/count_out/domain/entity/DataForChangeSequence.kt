package com.count_out.domain.entity

interface DataForChangeSequence {
    val trainingId: Long
    val roundId: Long
    val ringId: Long
    val from: Int
    val to: Int
}