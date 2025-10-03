package com.count_out.data.models

interface PlanDb {
    val idPlan: Long
    val name: String
    val speeches: List<SpeechDb>
    val parts: List<PartDb>
    val amountActivity: Int
}