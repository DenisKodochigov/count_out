package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Set
import com.example.count_out.entity.SpeechActivity

@Entity(tableName = "tb_set")
data class SetDB(
    @PrimaryKey(autoGenerate = true) override val idSet: Long = 0L,
    override val name: String = "",
    override val reps: Int = 0,
    override val duration: Double = 0.0,
    override val weight: Int = 0,
    override val intervalReps: Double = 0.0,
    override val speechId: Long,
    override val exerciseId: Long,
    override val intensity: String,
    override val distance: Double,
    override val intervalDown: Double,
    override val groupCount: String,
    override val timeRest: Int,
    @Ignore override val speechActivity: SpeechActivity = SpeechActivityDB(),
): Set
