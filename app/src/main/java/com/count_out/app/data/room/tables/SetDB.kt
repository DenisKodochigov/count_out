//package com.count_out.app.data.room.tables
//
//import androidx.room.Entity
//import androidx.room.Ignore
//import androidx.room.PrimaryKey
//import com.count_out.app.entity.DistanceE
//import com.count_out.app.entity.GoalSet
//import com.count_out.app.entity.TimeE
//import com.count_out.app.entity.WeightE
//import com.count_out.app.entity.Zone
//import com.count_out.app.entity.speech.SpeechKit
//import com.count_out.app.entity.workout.Set
//
//@Entity(tableName = "tb_set")
//data class SetDB(
//    @PrimaryKey(autoGenerate = true) override var idSet: Long = 0L,
//    override var name: String = "",
//    override var speechId: Long = 0,
//    override var goal: GoalSet = GoalSet.DISTANCE,
//    override var exerciseId: Long = 0,
//    override var reps: Int = 0,
//    override var duration: Int = 0,
//    override var durationE: TimeE = TimeE.SEC,
//    override var distance: Double = 0.0,
//    override var distanceE: DistanceE = DistanceE.KM,
//    override var weight: Int = 0,
//    override var weightE: WeightE = WeightE.KG,
//    override var intervalReps: Double = 0.0,
//    override var intensity: Zone = Zone.EXTRA_SLOW,
//    override var intervalDown: Int = 0,
//    override var groupCount: String = "",
//    override var timeRest: Int = 0,
//    override var timeRestE: TimeE = TimeE.SEC,
//    @Ignore override var speech: SpeechKit = SpeechKitDB(),
//): Set