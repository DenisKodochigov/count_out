package com.example.framework.room.db.set

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_set")
data class SetTable (
    @PrimaryKey(autoGenerate = true)  var idSet: Long = 0L,
    var name: String = "",
    var speechId: Long = 0,
    var goal: Int = 1,
    var exerciseId: Long = 0,
    var reps: Int = 0,
    var duration: Int = 0,
    var durationU: Int = 1,
    var distance: Double = 0.0,
    var distanceU: Int = 1,
    var weight: Int = 0,
    var weightU: Int = 1,
    var intervalReps: Double = 0.0,
    var intensity: Int = 1,
    var intervalDown: Int = 0,
    var groupCount: String = "",
    var timeRest: Int = 0,
    var timeRestU: Int = 1,
){
//    fun toSetImpl() = SetImpl(
//        idSet = this.idSet,
//        name = this.name,
//        speechId = this.speechId,
//        goal = Goal.entries[this.goal],
//        exerciseId = this.exerciseId,
//        reps = this.reps,
//        duration = this.duration,
//        durationU = TimeUnit.entries[this.durationU],
//        distance = this.distance,
//        distanceU = DistanceUnit.entries[this.distanceU],
//        weight = this.weight,
//        weightU = WeightUnit.entries[this.weightU],
//        intervalReps = this.intervalReps,
//        intensity = Zone.entries[this.intensity],
//        intervalDown = this.intervalDown,
//        groupCount = this.groupCount,
//        timeRest = this.timeRest,
//        timeRestU = TimeUnit.entries[this.durationU],
//    )
}