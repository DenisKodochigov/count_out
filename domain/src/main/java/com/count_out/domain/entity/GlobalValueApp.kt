package com.count_out.domain.entity

import android.util.Log
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.MutableStateFlow

object GlobalValueApp {
    var planLast: MutableStateFlow<ResultDomain<Domain>?> = MutableStateFlow(null)

    fun toStepPlan(plan: Plan?): StepPlan{
        var numberExercise = 1
        var exerciseCount = 0
        val list: MutableList<StepPlan> = mutableListOf()

        val stepPlan = plan?.let { it.parts.forEach { part ->
            part.rings.forEachIndexed { indR, ring -> exerciseCount += ring.exercises.count() }
            part.rings.forEachIndexed { indR, ring ->
                ring.exercises.forEachIndexed { indE, exercise ->
                    if (list.isNotEmpty()) {
                        val nextExercise = nextExercise(exercise)
                        for (ind in list.lastIndex downTo 0) {
                            if (list[ind].nextExercise == null) {
                                list[ind].nextExercise = nextExercise
                            }
                        }
                    }
                    exercise.sets.forEachIndexed { indS, set ->
                        list.add(
                            object : StepPlan {
                                override val idPlan: Long = plan.idPlan
                                override val namePlan: String = plan.name
                                override val part: Part? = part
                                override val exercise: Exercise? = exercise
                                override var nextExercise: NextExercise? = null
                                override val numberExercise: Int = numberExercise
                                override val quantityExercise: Int = exerciseCount
                                override var currentSet: Set? = set
                                override val numberSet: Int = indS + 1
                                override val quantitySet: Int = exercise.sets.count()
                            })
                    }
                    numberExercise++
                }
            }
        } }.run { if (list.isNotEmpty()) list[0] else
            object: StepPlan{
                override val idPlan: Long = 1
                override val namePlan: String = ""
                override val part: Part? = null
                override val exercise: Exercise? = null
                override var nextExercise: NextExercise? = null
                override val numberExercise: Int = numberExercise
                override val quantityExercise: Int = exerciseCount
                override var currentSet: Set? = null
                override val numberSet: Int = 1
                override val quantitySet: Int = 1
            }
        }

        return stepPlan
    }

    fun nextExercise(exercise: Exercise): NextExercise {
        val list: MutableList<Pair<String, Int>> = mutableListOf()
        exercise.sets.forEachIndexed { _, set ->
            list.add( when (set.goal) {
                Goal.Duration -> "${set.duration.value / (if (set.duration.unit == Units.S) 1 else 60)}" to set.duration.unit.id
                Goal.Distance -> "${set.distance.value / (if (set.distance.unit == Units.MT) 1 else 1000)}" to set.distance.unit.id
                Goal.Count -> "${set.reps}" to 0
                Goal.CountGroup -> "" to 0 }
            )
        }
        return object: NextExercise{
            override val nextActivityName: String = exercise.activity?.name.toString()
            override val nextExerciseId: Long = exercise.idExercise
            override val nextExerciseQuantitySet: Int = exercise.sets.count()
            override val nextExerciseSummarizeSet: List<Pair<String, Int>> = list
        }
    }
}

//fun toStepPlan(training: Training?): StepPlan?{
//        var numberExercise = 1
//        var exerciseCount = 0
//
//        val list: MutableList<StepPlan> = mutableListOf()
//        return training?.let { tr->
//            tr.rounds.forEachIndexed { indR, round-> exerciseCount += round.exercise.count() }
//            tr.rounds.forEachIndexed { indR, round->
//                round.exercise.forEachIndexed { indE, exercise ->
//                    if (list.isNotEmpty()){
//                        val nextExercise = nextExercise(exercise)
//                        for (ind in list.lastIndex downTo 0){
//                            if (list[ind].nextExercise == null){
//                                list[ind].nextExercise = nextExercise
//                            }
//                        }
//                    }
//                    exercise.sets.forEachIndexed { indS, set->
//                        list.add(
//                            object: StepPlan{
//                                override val idPlan: Long = tr.idTraining
//                                override val namePlan: String = tr.name
//                                override val round: Round? = round
//                                override val exercise: Exercise? = exercise
//                                override var nextExercise: NextExercise? = null
//                                override val numberExercise: Int = numberExercise
//                                override val quantityExercise: Int = exerciseCount
//                                override var currentSet: Set? = set
//                                override val numberSet: Int = indS + 1
//                                override val quantitySet: Int = exercise.sets.count()
//                            })
//                    }
//                    numberExercise ++
//                }
//            }
//        }.run { if (list.isEmpty()) null else list[0] }
//    }