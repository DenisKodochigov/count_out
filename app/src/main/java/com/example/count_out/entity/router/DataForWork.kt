package com.example.count_out.entity.router

import com.example.count_out.R
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.ui.ExecuteInfoExercise
import com.example.count_out.entity.ui.ExecuteInfoSet
import com.example.count_out.entity.ui.NextExercise
import com.example.count_out.entity.workout.Activity
import com.example.count_out.entity.workout.EntityTraining
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.Round
import com.example.count_out.entity.workout.Set
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForWork (
    var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    var intervalReps: MutableStateFlow<Double> = MutableStateFlow(0.0),
    var indexMap: Int = 0,
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
    var cancelCoroutineWork: ()-> Unit = {},
    val dataFromWork: DataFromWork? = null,

    var map: List<EntityTraining> = emptyList(),
    var roundCount: Int = 0,
    var exerciseNumber: Int = 1,
){
    fun empty(){
        indexRound = 0
        indexExercise = 0
        indexSet = 0
    }
    fun getRound(): Round? {
        return try {
            training.value?.rounds?.get(indexRound)
        } catch (e: Exception) {
            lg(" ERROR getRound: $e")
            training.value?.let { trng ->
                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
            }
            training.value?.rounds?.get(indexRound)
        }
    }
    fun getExercise(): Exercise? {
        return getRound()?.exercise?.let {
            if (it.isNotEmpty()) {
                try { getRound()?.exercise?.get(indexExercise) }
                catch (e: Exception) {
                    lg(" ERROR getExercise: $e")
                    training.value?.let { trng ->
                        if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
                        if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
                            indexExercise = trng.rounds[indexRound].exercise.count() - 1
                    }
                    training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)
                }
            } else null
        }
    }
    fun getSet(ind: Int = indexSet): Set? {
        return try {
            setExecuteInfoSet(getExercise()?.sets?.get(ind))
        } catch (e: Exception) {
            lg(" ERROR getSet: $e")
            training.value?.let { trng ->
                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
                if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
                    indexExercise = trng.rounds[indexRound].exercise.count() - 1
                if (trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1 < ind)
                    indexSet = trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1
            }
            setExecuteInfoSet(
                training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indexSet))
        }
    }

    fun getSetIntervalReps(): Double {
        return try {
            getSet()?.intervalReps ?: 0.0
        } catch (e: Exception) {
            lg(" ERROR getSetIntervalReps: $e")
            0.0
        }
    }
    fun setExecuteInfoSet(set: Set? = null): Set?{
        dataFromWork?.executeInfoSet?.value = executeInfoSet(set?.idSet ?: 0L)
        return set
    }
    fun setExecuteInfoExercise(){
        dataFromWork?.executeInfoExercise?.value = fillInfoExercise(getExercise()?.idExercise ?: 0)
    }
    private fun fillInfoExercise(idExercise: Long = 0L): ExecuteInfoExercise {
        var finding = false
        var activity: Activity? = null
        var nextExercise: NextExercise? = null
        var currentExercise: Int = 0
        var quantityExercise: Int = 1
        val list: MutableList<Pair<String, Int>> = mutableListOf()

        training.value?.let { tr->
            tr.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed { _, exerciseL ->
                    if (finding && exerciseL.sets.isNotEmpty()) {
                        exerciseL.sets.forEachIndexed { _, set ->
                            list.add( when (set.goal) {
                                GoalSet.DURATION -> "${set.duration / (if (set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id
                                GoalSet.DISTANCE -> "${set.distance / (if (set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id
                                GoalSet.COUNT -> "${set.reps}" to R.string.rep
                                GoalSet.COUNT_GROUP -> "" to 0 }
                            )
                        }
                        nextExercise = NextExercise(
                            nextActivityName = exerciseL.activity.name,
                            nextExerciseId = exerciseL.idExercise,
                            nextExerciseQuantitySet = exerciseL.sets.count(),
                            nextExerciseSummarizeSet = list )
                        finding = false
                    }
                    if (exerciseL.idExercise == idExercise || idExercise == 0L) {
                        if (exerciseL.sets.isNotEmpty()){
                            activity = exerciseL.activity
                            currentExercise = quantityExercise
                            finding = true
                        }
                    }
                    quantityExercise ++
                }
            }
        }

        return ExecuteInfoExercise(
            activity = activity,
            nextExercise = nextExercise,
            currentExercise = currentExercise,
            quantityExercise = quantityExercise - 1 )
    }
    private fun executeInfoSet(idSet: Long = 0L): ExecuteInfoSet? {
        training.value?.let { tr->
            tr.rounds.forEachIndexed { _, round ->
                round.exercise.forEachIndexed { _, exerciseL ->
                    exerciseL.sets.forEachIndexed { indSet, setL ->
                        if (setL.idSet == idSet || idSet == 0L) {
                            return ExecuteInfoSet(
                                currentSet = setL,
                                setNumber = indSet + 1,
                                quantitySet = exerciseL.sets.count()
                            )
                        }
                    }
                }
            }
        }
        return null
    }
//#################################################################################
    fun createMapTraining(){
        val list: MutableList<EntityTraining> = mutableListOf()
        this.training.value?.let { tr->
            tr.rounds.forEachIndexed { indR, round->
                round.exercise.forEachIndexed { indE,exercise ->
                    if (list.isNotEmpty()) {
                        val nextExercise = nextExercise(exercise)
                        for (ind in list.lastIndex downTo 0){
                            if (list[ind].nextExercise == null){
                                list[ind] = list[ind].copy(nextExercise = nextExercise)
                            }
                        }
                    }
                    exercise.sets.forEachIndexed { indS, set->
                        list.add(
                            EntityTraining(
                                round = round,
                                roundCurrent = indR,
                                exercise = exercise,
                                exerciseNumber = exerciseNumber,
                                set = set,
                                setNumber = indS + 1,
                                quantitySet = exercise.sets.count()
                        ))
                    }
                    exerciseNumber ++
                }
            }
            roundCount = tr.rounds.count()
        }
        this.map = list
    }
    private fun nextExercise(exercise: Exercise): NextExercise {
        val list: MutableList<Pair<String, Int>> = mutableListOf()
        exercise.sets.forEachIndexed { _, set ->
            list.add( when (set.goal) {
                GoalSet.DURATION -> "${set.duration / (if (set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id
                GoalSet.DISTANCE -> "${set.distance / (if (set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id
                GoalSet.COUNT -> "${set.reps}" to R.string.rep
                GoalSet.COUNT_GROUP -> "" to 0 }
            )
        }
        return NextExercise(
                    nextActivityName = exercise.activity.name,
                    nextExerciseId = exercise.idExercise,
                    nextExerciseQuantitySet = exercise.sets.count(),
                    nextExerciseSummarizeSet = list )
    }
    fun setExecuteInfoExercise(index: Int){
        dataFromWork?.executeInfoExercise?.value = ExecuteInfoExercise(
            activity = map[index].exercise?.activity,
            nextExercise = map[index].nextExercise,
            currentExercise = map[index].exerciseNumber,
            quantityExercise = exerciseNumber
        )
    }
    fun setExecuteInfoSet(index: Int){
        dataFromWork?.executeInfoSet?.value = ExecuteInfoSet(
            currentSet = map[index].set,
            setNumber = map[index].setNumber,
            quantitySet = map[index].quantitySet,
        )
    }
}

