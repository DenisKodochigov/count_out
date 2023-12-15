package com.example.count_out.data.room.tables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Exercise
import com.example.count_out.entity.Set
import com.example.count_out.entity.SpeechActivity

@Entity(tableName = "tb_exercise")
data class ExerciseDB(
    @PrimaryKey(autoGenerate = true) override val idExercise: Long,
    override val name: String = "",
    override val picture: Any = "",
    override val colour: Color = Color.Red,
    override val icon: ImageVector? = null,
    override val videoClip: String = "",
    override val audioTrack: String ="",
    override val roundId: Long,
    override val speechId: Long,
    @Ignore override val speechActivity: SpeechActivity,
    @Ignore override val sets: List<Set>
): Exercise
