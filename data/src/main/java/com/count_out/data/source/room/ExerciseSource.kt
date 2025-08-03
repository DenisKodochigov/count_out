package com.count_out.data.source.room

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.flow.Flow

/**
 * get плучаем конкретную реализацию ExerciseImplD
 * gets - Список SET приписанных к ExerciseImplD
 * getForRound получаем список упражнений для указанного ROUND
 * getForRing получаем список упражнений для указанного RING
 * getFilter получаем список упражненйи согласно списку idExerciseImplD
 * add если (item: ExerciseImplDTable).ringId или roundId == 0, то пропускаем задание
 *      если один из указанных ID > 0, то выполням следующую последовательность:
 *      1. создаем или копируем speechKit и получаем speechKitID.
 *      2. Создаем новое упражнение с интеграцией speechKitID, получаем exerciseID
 *      3. Создаем новый SET или копируем из входного exercise с интеграцией exerciseID.
 *      Если ExerciseImplDTable пустая, то это добавление, если заполеная, то это копирование. функции идентичны
 * update - функция обновляет только сам объект не обновляя вложенные объекты.
 * del - удаляет сам объект и вложенные объекты кроме Activity
 *  setActivityIntoExerciseImplD - меняет ID Activity в ExerciseImplD
 */
interface ExerciseSource {
    fun get(exercise: TypeSource): Flow<ResultSource<TypeSource>>
    fun getForRound(id: TypeSource): Flow<ResultSource<TypeSource>>
    fun getForRing(id: TypeSource): Flow<ResultSource<TypeSource>>
    fun getFilter(list: TypeSource): Flow<ResultSource<TypeSource>>
    fun copy(exercise: TypeSource): ResultSource<TypeSource>
    fun del(exercise: TypeSource): ResultSource<TypeSource>
    fun update(exercise: TypeSource): ResultSource<TypeSource>
}