package com.example.count_out.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

interface Exercise {
    val idExercise: Long
    val name: String
    val roundId: Long
    val picture: Any
    val colour: Color
    val icon: ImageVector?
    val videoClip: String
    val audioTrack: String
    val speechId: Long
    val speechActivity: SpeechActivity
    val sets: List<Set>
}
