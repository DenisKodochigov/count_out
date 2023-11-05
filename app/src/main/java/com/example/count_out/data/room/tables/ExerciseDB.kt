package com.example.count_out.data.room.tables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.count_out.entity.Exercise

data class ExerciseDB(
    override val name: String = "",
    override val picture: Any = "",
    override val colour: Color = Color.Red,
    override val icon: ImageVector? = null,
    override val videoClip: String = "",
    override val audioTrack: String =""
): Exercise
