package com.example.count_out.domain.router.models

import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.entity.DistanceE
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.TimeE
import com.example.count_out.entity.workout.Exercise
import com.example.count_out.entity.workout.StepTraining
import com.example.count_out.entity.workout.Training
import com.example.count_out.ui.modules.NextExercise
import kotlinx.coroutines.flow.MutableStateFlow

data class DataForWork (
    var training: MutableStateFlow<Training?> = MutableStateFlow(null),
    var enableSpeechDescription: MutableStateFlow<Boolean> = MutableStateFlow(true),
    var idSetChangeInterval: MutableStateFlow<Long> = MutableStateFlow(0),
    var interval: MutableStateFlow<Double> = MutableStateFlow(0.0),
    var indexMap: Int = 0,
    var indexRound: Int = 0,
    var indexExercise: Int = 0,
    var indexSet: Int = 0,
    var cancelCoroutineWork: ()-> Unit = {},
    val dataFromWork: DataFromWork? = null,

    var map: MutableList<StepTraining> = mutableListOf<StepTraining>(),
    var exerciseCount: Int = 0,
){
    fun empty(){
        indexRound = 0
        indexExercise = 0
        indexSet = 0
    }

    fun initStepTraining(){
        dataFromWork?.stepTraining?.value = if (map.isNotEmpty()) map[indexMap] else null
    }
    fun sendStepTrainingShort(){
        dataFromWork?.stepTraining?.value = map[indexMap]
    }
    fun sendStepTraining(){
        dataFromWork?.stepTraining?.value =
            if (interval.value > 0) {
                map[indexMap].copy(currentSet = map[indexMap].currentSet?.let { set ->
                   (set as SetDB).copy(intervalReps = interval.value) })}
            else map[indexMap]
    }

    fun createMapTraining(){
        var numberExercise = 1
        val list: MutableList<StepTraining> = mutableListOf()
        this.training.value?.let { tr->
            tr.rounds.forEachIndexed { indR, round-> exerciseCount += round.exercise.count() }
            tr.rounds.forEachIndexed { indR, round->
                round.exercise.forEachIndexed { indE,exercise ->
                    if (list.isNotEmpty()){
                        val nextExercise = nextExercise(exercise)
                        for (ind in list.lastIndex downTo 0){
                            if (list[ind].nextExercise == null){
                                list[ind] = list[ind].copy(nextExercise = nextExercise)
                            }
                        }
                    }
                    exercise.sets.forEachIndexed { indS, set->
                        list.add(
                            StepTraining(
                                round = round,
                                exercise = exercise,
                                numberExercise = numberExercise,
                                quantityExercise = exerciseCount,
                                currentSet = set,
                                numberSet = indS + 1,
                                quantitySet = exercise.sets.count()
                        ))
                    }
                    numberExercise ++
                }
            }
        }
        this.map = list
    }
    fun nextExercise(exercise: Exercise): NextExercise {
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
                    nextActivityName = exercise.activity.name.toString(),
                    nextExerciseId = exercise.idExercise,
                    nextExerciseQuantitySet = exercise.sets.count(),
                    nextExerciseSummarizeSet = list )
    }
}
//    fun setExecuteInfoExercise(index: Int){
//        dataFromWork?.executeInfoExercise?.value = ExecuteInfoExercise(
//            activity = map[index].exercise?.activity,
//            nextExercise = map[index].nextExercise,
//            numberExercise = map[index].numberExercise,
//            quantityExercise = exerciseCount
//        )
//    }
//    fun getRound(): Round? {
//        return try {
//            training.value?.rounds?.get(indexRound)
//        } catch (e: Exception) {
//            lg(" ERROR getRound: $e")
//            training.value?.let { trng ->
//                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
//            }
//            training.value?.rounds?.get(indexRound)
//        }
//    }
//    fun getExercise(): Exercise? {
//        return getRound()?.exercise?.let {
//            if (it.isNotEmpty()) {
//                try { getRound()?.exercise?.get(indexExercise) }
//                catch (e: Exception) {
//                    lg(" ERROR getExercise: $e")
//                    training.value?.let { trng ->
//                        if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
//                        if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
//                            indexExercise = trng.rounds[indexRound].exercise.count() - 1
//                    }
//                    training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)
//                }
//            } else null
//        }
//    }
//    fun getSet(ind: Int = indexSet): Set? {
//        return try {
//            setExecuteInfoSet(getExercise()?.sets?.get(ind))
//        } catch (e: Exception) {
//            lg(" ERROR getSet: $e")
//            training.value?.let { trng ->
//                if (trng.rounds.count() - 1 < indexRound) indexRound = trng.rounds.count() - 1
//                if (trng.rounds[indexRound].exercise.count() - 1 < indexExercise)
//                    indexExercise = trng.rounds[indexRound].exercise.count() - 1
//                if (trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1 < ind)
//                    indexSet = trng.rounds[indexRound].exercise[indexExercise].sets.count() - 1
//            }
//            setExecuteInfoSet(
//                training.value?.rounds?.get(indexRound)?.exercise?.get(indexExercise)?.sets?.get(indexSet))
//        }
//    }
//    fun setExecuteInfoSet(set: Set? = null): Set?{
//        dataFromWork?.executeInfoSet?.value = executeInfoSet(set?.idSet ?: 0L)
//        return set
//    }
//    fun executeInfoSet( idSet: Long = 0L): ExecuteInfoSet? {
//        training.value?.let { tr->
//            tr.rounds.forEachIndexed { _, round ->
//                round.exercise.forEachIndexed { _, exerciseL ->
//                    exerciseL.sets.forEachIndexed { indSet, setL ->
//                        if (setL.idSet == idSet || idSet == 0L) {
//                            return ExecuteInfoSet(
//                                currentSet = setL,
//                                numberSet = indSet + 1,
//                                quantitySet = exerciseL.sets.count()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        return null
//    }

//    fun setExecuteInfoExercise(){
//        dataFromWork?.executeInfoExercise?.value = executeInfoExercise(getExercise()?.idExercise ?: 0)
//    }
//    private fun executeInfoExercise(idExercise: Long = 0L): ExecuteInfoExercise? {
//        var finding = false
//        var activity: Activity? = null
//        var nextExercise: NextExercise? = null
//        var currentExercise: Int = 0
//        var quantityExercise: Int = 1
//        val list: MutableList<Pair<String, Int>> = mutableListOf()
//
//        training.value?.let { tr->
//            tr.rounds.forEachIndexed { _, round ->
//                round.exercise.forEachIndexed { _, exerciseL ->
//                    if (finding && exerciseL.sets.count() > 0) {
//                        exerciseL.sets.forEachIndexed { _, set ->
//                            list.add( when (set.goal) {
//                                GoalSet.DURATION -> "${set.duration / (if (set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id
//                                GoalSet.DISTANCE -> "${set.distance / (if (set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id
//                                GoalSet.COUNT -> "${set.reps}" to R.string.rep
//                                GoalSet.COUNT_GROUP -> "" to 0 }
//                            )
//                        }
//                        nextExercise = NextExercise(
//                            nextActivityName = exerciseL.activity.name,
//                            nextExerciseId = exerciseL.idExercise,
//                            nextExerciseQuantitySet = exerciseL.sets.count(),
//                            nextExerciseSummarizeSet = list )
//                        finding = false
//                    }
//                    if (exerciseL.idExercise == idExercise || idExercise == 0L) {
//                        if (exerciseL.sets.isNotEmpty()){
//                            activity = exerciseL.activity
//                            currentExercise = quantityExercise
//                            finding = true
//                        }
//                    }
//                    quantityExercise ++
//                }
//            }
//        }
//
//        return ExecuteInfoExercise(
//            activity = activity,
//            nextExercise = nextExercise,
//            numberExercise = currentExercise,
//            quantityExercise = quantityExercise - 1 )
//    }
//    fun getSetIntervalReps(): Double {
//        return try {
//            getSet()?.intervalReps ?: 0.0
//        } catch (e: Exception) {
//            lg(" ERROR getSetIntervalReps: $e")
//            0.0
//        }
//    }
//    fun initDataForUISetInfo(){
//        training.value?.let { tr->
//            tr.rounds.forEachIndexed { _, round ->
//                round.exercise.forEachIndexed { _, exerciseL ->
//                    exerciseL.sets.forEachIndexed { indSet, setL ->
//                        dataFromWork?.executeInfoSet?.value = ExecuteInfoSet(
//                            currentSet = setL,
//                            numberSet = indSet + 1,
//                            quantitySet = exerciseL.sets.count()
//                        )
//                        return
//                    }
//                }
//            }
//        }
//    }
//    fun initDataForUIExerciseInfo(){
//        var finding = false
//        var activity: Activity? = null
//        var nextExercise: NextExercise? = null
//        var currentExercise: Int = 0
//        var quantityExercise: Int = 1
//        val list: MutableList<Pair<String, Int>> = mutableListOf()
//
//        training.value?.let { tr->
//            tr.rounds.forEachIndexed { _, round ->
//                round.exercise.forEachIndexed { _, exerciseL ->
//                    if (finding) {
//                        exerciseL.sets.forEachIndexed { _, set ->
//                            list.add( when (set.goal) {
//                                GoalSet.DURATION -> "${set.duration / (if (set.durationE == TimeE.SEC) 1 else 60)}" to set.durationE.id
//                                GoalSet.DISTANCE -> "${set.distance / (if (set.distanceE == DistanceE.M) 1 else 1000)}" to set.distanceE.id
//                                GoalSet.COUNT -> "${set.reps}" to R.string.rep
//                                GoalSet.COUNT_GROUP -> "" to 0 }
//                            )
//                        }
//                        nextExercise = NextExercise(
//                            nextActivityName = exerciseL.activity.name,
//                            nextExerciseId = exerciseL.idExercise,
//                            nextExerciseQuantitySet = exerciseL.sets.count(),
//                            nextExerciseSummarizeSet = list )
//                        finding = false
//                    }
//                    if (exerciseL.sets.isNotEmpty()){
//                        activity = exerciseL.activity
//                        currentExercise = quantityExercise
//                        finding = true
//                    }
//                    quantityExercise ++
//                }
//            }
//        }
//
//        dataFromWork?.executeInfoExercise?.value = ExecuteInfoExercise(
//            activity = activity,
//            nextExercise = nextExercise,
//            numberExercise = currentExercise,
//            quantityExercise = quantityExercise - 1 )
//    }
//    fun getSetInfo(){
//        dataFromWork?.executeInfoSet?.value = ExecuteInfoSet(
//            currentSet = map[indexMap].currentSet,
//            numberSet = map[indexMap].numberSet,
//            quantitySet = map[indexMap].exercise?.sets?.count() ?: 0
//        )
//    }