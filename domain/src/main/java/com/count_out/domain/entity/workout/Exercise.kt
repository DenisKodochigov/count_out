package com.count_out.domain.entity.workout

interface Exercise: Domain {
     val idExercise: Long
     val ringId: Long
     val idView: Int
     val activity: Activity
     val activityId: Long
     val speechKit: SpeechKit
     val sets: List<Set>
     val amountSet: Int
     val duration: Parameter
     companion object{
          fun default(ringId: Long) = object: Exercise{
               override val idExercise: Long = 0
               override val ringId: Long = ringId
               override val idView: Int = 0
               override val activity: Activity = Activity.EMPTY
               override val activityId: Long = 0
               override val speechKit: SpeechKit = SpeechKit.EMPTY
               override val sets: List<Set> = emptyList()
               override val amountSet: Int = 0
               override val duration: Parameter = Parameter.EMPTY
          }
         fun Exercise.copy(
             idExercise: Long = this@copy.idExercise,
             ringId: Long = this@copy.ringId,
             idView: Int = this@copy.idView,
             activity: Activity = this@copy.activity,
             activityId: Long = this@copy.activityId,
             speechKit: SpeechKit = this@copy.speechKit,
             sets: List<Set> = this@copy.sets,
             amountSet: Int = this@copy.amountSet,
             duration: Parameter = this@copy.duration

         ) = object: Exercise{
             override val idExercise: Long = idExercise
             override val ringId: Long = ringId
             override val idView: Int = idView
             override val activity: Activity = activity
             override val activityId: Long = activityId
             override val speechKit: SpeechKit = speechKit
             override val sets: List<Set> = sets
             override val amountSet: Int = amountSet
             override val duration: Parameter = duration
         }
     }
}