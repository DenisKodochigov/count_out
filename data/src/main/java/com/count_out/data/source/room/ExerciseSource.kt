package com.count_out.data.source.room

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData

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
    fun insert(exercise: Data): ResultData<Data>
    fun del(exercise: Data): ResultData<Data>
    fun update(exercise: Data): ResultData<Data>
    fun changeSequence(setViewId: Data): ResultData<Data>
}
