package com.count_out.domain.entity

import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Round
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.MutableStateFlow

object GlobalValueApp {
    var planRun: MutableStateFlow<Training?> = MutableStateFlow(null)

    fun toStepPlan(training: Training?): StepPlan?{
        var numberExercise = 1
        var exerciseCount = 0

        val list: MutableList<StepPlan> = mutableListOf()
        return training?.let { tr->
            tr.rounds.forEachIndexed { indR, round-> exerciseCount += round.exercise.count() }
            tr.rounds.forEachIndexed { indR, round->
                round.exercise.forEachIndexed { indE, exercise ->
                    if (list.isNotEmpty()){
                        val nextExercise = nextExercise(exercise)
                        for (ind in list.lastIndex downTo 0){
                            if (list[ind].nextExercise == null){
                                list[ind].nextExercise = nextExercise
                            }
                        }
                    }
                    exercise.sets.forEachIndexed { indS, set->
                        list.add(
                            object: StepPlan{
                                override val idPlan: Long = tr.idTraining
                                override val namePlan: String = tr.name
                                override val round: Round? = round
                                override val exercise: Exercise? = exercise
                                override var nextExercise: NextExercise? = null
                                override val numberExercise: Int = numberExercise
                                override val quantityExercise: Int = exerciseCount
                                override var currentSet: Set? = set
                                override val numberSet: Int = indS + 1
                                override val quantitySet: Int = exercise.sets.count()
                            })
                    }
                    numberExercise ++
                }
            }
        }.run { if (list.isEmpty()) null else list[0] }
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