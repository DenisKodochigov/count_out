package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.Set
import com.example.count_out.entity.Speech

@Entity(tableName = "tb_set")
data class SetDB(
    @PrimaryKey(autoGenerate = true) override var idSet: Long = 0L,
    override var name: String = "",
    override var speechId: Long = 0,
    override var exerciseId: Long = 0,
    override var reps: Int = 0,
    override var duration: Double = 0.0,
    override var weight: Int = 0,
    override var intervalReps: Double = 0.0,
    override var intensity: String = "",
    override var distance: Double = 0.0,
    override var intervalDown: Int = 0,
    override var groupCount: String = "",
    override var timeRest: Int = 0,
    @Ignore override var speech: Speech = SpeechDB(),
): Set
