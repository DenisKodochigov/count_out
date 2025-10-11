package com.count_out.domain.entity.workout

interface Exercise: Domain {
     val idExercise: Long
     val ringId: Long
     val idView: Int
     val activity: Activity?
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
               override val activity: Activity? = null
               override val activityId: Long = 0
               override val speechKit: SpeechKit = SpeechKit.EMPTY
               override val sets: List<Set> = emptyList()
               override val amountSet: Int = 0
               override val duration: Parameter = Parameter.EMPTY
          }
     }
}