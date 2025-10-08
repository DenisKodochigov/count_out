package com.count_out.data.models

interface PartDb: Data {
    val idPart: Long
    val planId: Long
    val speeches: List<SpeechDb>
    val rings: List<RingDb>
    val amount: Int
    val duration: Double
}