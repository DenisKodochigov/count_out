package com.example.count_out.data.room.tables

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.WeightE
import com.example.count_out.entity.Zone
import com.example.count_out.entity.speech.SpeechKit
import com.example.count_out.entity.workout.Set

@Entity(tableName = "tb_set")
data class SetDB(
    @PrimaryKey(autoGenerate = true) override var idSet: Long = 0L,
    override var name: String = "",
    override var speechId: Long = 0,
    override var goal: GoalSet = GoalSet.DISTANCE,
    override var exerciseId: Long = 0,
    override var reps: Int = 0,
    override var duration: Double = 0.0,
    override var durationE: TimeE = TimeE.SEC,
    override var distance: Double = 0.0,
    override var distanceE: DistanceE = DistanceE.KM,
    override var weight: Int = 0,
    override var weightE: WeightE = WeightE.KG,
    override var intervalReps: Double = 0.0,
    override var intensity: Zone = Zone.EXTRA_SLOW,
    override var intervalDown: Int = 0,
    override var groupCount: String = "",
    override var timeRest: Int = 0,
    override var timeRestE: TimeE = TimeE.SEC,
    @Ignore override var speech: SpeechKit = SpeechKitDB(),
): Set