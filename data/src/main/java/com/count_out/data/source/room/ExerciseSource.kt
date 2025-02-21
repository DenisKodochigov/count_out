package com.count_out.data.source.room

import com.count_out.data.entity.ExerciseImpl
import com.count_out.domain.entity.Exercise
import kotlinx.coroutines.flow.Flow

/**
 * get плучаем конкретную реализацию ExerciseImpl
 * gets - Список SET приписанных к Exercise
 * getForRound получаем список упражнений для указанного ROUND
 * getForRing получаем список упражнений для указанного RING
 * getFilter получаем список упражненйи согласно списку idExercise
 * add если (item: ExerciseTable).ringId или roundId == 0, то пропускаем задание
 *      если один из указанных ID > 0, то выполням следующую последовательность:
 *      1. создаем или копируем speechKit и получаем speechKitID.
 *      2. Создаем новое упражнение с интеграцией speechKitID, получаем exerciseID
 *      3. Создаем новый SET или копируем из входного exercise с интеграцией exerciseID.
 *      Если ExerciseTable пустая, то это добавление, если заполеная, то это копирование. функции идентичны
 * update - функция обновляет только сам объект не обновляя вложенные объекты.
 * del - удаляет сам объект и вложенные объекты кроме Activity
 *  setActivityIntoExercise - меняет ID Activity в Exercise
 */
interface ExerciseSource {
    fun gets(): Flow<List<Exercise>>
    fun get(id: Long): Flow<Exercise>
    fun getForRound(id: Long): Flow<List<Exercise>>
    fun getForRing(id: Long): Flow<List<Exercise>>
    fun getFilter(list: List<Long>): Flow<List<Exercise>>
    fun add(roundId: Long = 0, ringId: Long = 0): Flow<List<Exercise>>
    fun copy(exercise: ExerciseImpl): Flow<List<Exercise>>
    fun del(exercise: ExerciseImpl): Flow<List<Exercise>>
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long)
    fun update(exercise: ExerciseImpl): Flow<Exercise>
    fun setActivityIntoExercise(exerciseId: Long, activityId: Long): Flow<Exercise>
}