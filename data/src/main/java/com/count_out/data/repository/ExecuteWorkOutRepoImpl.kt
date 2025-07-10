package com.count_out.data.repository

import android.util.Log
import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.models.throwable.ThrowableDataSource
import com.count_out.data.router.models.NextExerciseImpl
import com.count_out.data.router.models.StepTrainingImpl
import com.count_out.data.source.room.TrainingSource
import com.count_out.domain.entity.NextExercise
import com.count_out.domain.entity.StepTraining
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Training
import com.count_out.domain.repository.ExecuteWorkOutRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExecuteWorkOutRepoImpl @Inject constructor(
    private val source: TrainingSource,
    private val converter: ConverterResult,): ExecuteWorkOutRepo
{
    override fun start() {}
    override fun stop() {}
    override fun pause() {}
    override fun save() {}
    override fun upInterval() {}
    override fun downInterval() {}
    var exerciseCount: Int = 0

    override fun getStepPlan(): Flow<ResultUC<StepTraining>> {
        return source.get2(id = 1L).map { plan->
            when(plan){
                is ResultDataSource.Error -> { ResultUC.Error(ThrowableUC.DataSourceTrow(plan.throwable)) }
                is ResultDataSource.Success<*> -> {
                    createMapTraining( plan.data as Training)?.let { ResultUC.Success(it) } ?:
                    ResultUC.Error(ThrowableUC.RepoTrow(Exception("return null"))) as ResultUC<Nothing>
                }
            }
        }
    }

    fun createMapTraining(training: Training?): StepTraining?{
        var numberExercise = 1
        val list: MutableList<StepTraining> = mutableListOf()
        return training?.let { tr->
            tr.rounds.forEachIndexed { indR, round-> exerciseCount += round.exercise.count() }
            tr.rounds.forEachIndexed { indR, round->
                round.exercise.forEachIndexed { indE, exercise ->
                    if (list.isNotEmpty()){
                        val nextExercise = nextExercise(exercise)
                        for (ind in list.lastIndex downTo 0){
                            if (list[ind].nextExercise == null){
                                list[ind] = (list[ind] as StepTrainingImpl).copy(nextExercise = nextExercise)
                            }
                        }
                    }
                    exercise.sets.forEachIndexed { indS, set->
                        list.add(
                            StepTrainingImpl(
                                idPlan = tr.idTraining,
                                round = round,
                                exercise = exercise,
                                numberExercise = numberExercise,
                                quantityExercise = exerciseCount,
                                currentSet = set,
                                numberSet = indS + 1,
                                quantitySet = exercise.sets.count(),
                                nextExercise = null
                            ))
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
        return NextExerciseImpl(
            nextActivityName = exercise.activity?.name.toString(),
            nextExerciseId = exercise.idExercise,
            nextExerciseQuantitySet = exercise.sets.count(),
            nextExerciseSummarizeSet = list )
    }
}