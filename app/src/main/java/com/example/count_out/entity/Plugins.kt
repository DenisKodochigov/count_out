package com.example.count_out.entity

import com.example.count_out.R
import com.example.count_out.data.room.AppDatabase
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB

object Plugins
{
    val listRound = mutableListOf<RoundDB>()
    val listTr = mutableListOf<TrainingDB>()
//    val listEx = mutableListOf<ExerciseDB>()
//    val listSpeech = mutableListOf<SpeechDB>()
//    val listActivity = listOf<Activity>(
//        ActivityDB(idActivity = 0, name = "Run", icon = R.drawable.ic_setka),
//        ActivityDB(idActivity = 1, name = "Ski", icon = R.drawable.ic_setka))

    val listSets = mutableListOf<Set>()
    fun init(){
        var idR = 0L
        for (t in 0..10L){
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.UP, exercise = mutableListOf()))
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.OUT, exercise = mutableListOf()))
            listRound.add(RoundDB( idRound = idR++, trainingId = t, roundType = RoundType.DOWN, exercise = mutableListOf()))
            listTr.add(
                TrainingDB( idTraining = t,
                    name = "Training $t",
                    rounds = mutableListOf(listRound[listRound.size-3], listRound[listRound.size-2], listRound[listRound.size-1]))
            )
        }
    }


    fun item (id: Long): Training{
        return listTr.find { it.idTraining == id } ?: TrainingDB() as Training
    }
}

fun prepopulateRealDb( db: AppDatabase
){
    createSetting(db)
    createActivity(db)
    createTrainingPlansReal(db)
    createTrainingPlansArm(db)
    createTrainingPlansLeg(db)
}
fun prepopulateTestDb( db: AppDatabase
){
    createSetting(db)
    createActivity(db)
    createTrainingPlansTesting(db)
}
private fun createActivity( db: AppDatabase){
    db.dataDao().addActivity(ActivityDB(idActivity = 1, name = "Бег", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 2, name = "Беговые лыжи", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 3, name = "Простучать бедра", icon = R.drawable.ic_setka,
        description = "Сядьте на край стула и кулаками простукивайте внешнюю сторону бедер."))
    db.dataDao().addActivity(ActivityDB(idActivity = 4, name = "Растереть уши", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 5, name = "Растереть макушку", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 6, name = "Растереть виски", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 7, name = "Растереть заднюю часть шеи", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 8, name = "Прямой подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к правой руке"))
    db.dataDao().addActivity(ActivityDB(idActivity = 9, name = "Диагональный подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к левой руке"))
    db.dataDao().addActivity(ActivityDB(idActivity = 10, name = "Паук", icon = R.drawable.ic_setka,
        description = "Руки за голову. Поднимайте колени к локтям одновременно наклянясь в сторону."))
    db.dataDao().addActivity(ActivityDB(idActivity = 11, name = "Приседания с выходом на носки", icon = R.drawable.ic_setka,
        description = "Глубокие приседания с подъемом на носках и вытягиванием рук вверх"))
    db.dataDao().addActivity(ActivityDB(idActivity = 12, name = "Сейдза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток."))
    db.dataDao().addActivity(ActivityDB(idActivity = 13, name = "Кидза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток, носки на подогнутых пальцах."))
    db.dataDao().addActivity(ActivityDB(idActivity = 14, name = "Отжимания", icon = R.drawable.ic_setka))
    db.dataDao().addActivity(ActivityDB(idActivity = 15, name = "Нога на ногу", icon = R.drawable.ic_setka,
        description = "Сядьте на стул. Положите одну ногу в районе ступни на другую. Медленно тяните вернюю ногу вниз, спина прямая."))
    db.dataDao().addActivity(ActivityDB(idActivity = 16, name = "Велосипед на пресс", icon = R.drawable.ic_setka,
        description = "Исходное положение: лежа на спине, руки за головой, ноги чуть согнуты и приподняты над полом " +
                "не отрывая таз. Согните правую ногу в колене, поднимая ее к корпусу. Вторая нога" +
                "остается на весу. Одновременно поворачиваясь левым локтем и приподнимая корпус в сторону правого колена. " +
                "Другой стороной — теперь правым локтем вы тянитесь к левому колену. "))
    db.dataDao().addActivity(ActivityDB(idActivity = 17, name = "Растяжка внутерней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, раздвиньте ноги. Покачиваясь вперед, пытайтесь логтями дотянуться до пола между ног"))
    db.dataDao().addActivity(ActivityDB(idActivity = 18, name = "Растяжка нижней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, вытяните перед собой ноги. Тянитесь вперед на выдохе."))
    db.dataDao().addActivity(ActivityDB(idActivity = 19, name = "Кисти рук", icon = R.drawable.ic_setka,
        description = "Вытяните рукки и сжимайте в кулак и разжимайте кисть"))
    db.dataDao().addActivity(ActivityDB(idActivity = 20, name = "Гантели. Плечи", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Поднимите перед собой, разведите в стороны и поворотом оси гантелей из вертикального в горизонтальное положение. Опустите руки в исходное стостояние."))
    db.dataDao().addActivity(ActivityDB(idActivity = 21, name = "Гантели. Плечи. Обартный ход", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Разведите руки в стороны. Сведите перед собой с изменением оси гантелей с горизонтального в вертикальное положение. Опустите руки в исходное положение."))

    db.dataDao().addActivity(ActivityDB(idActivity = 22, name = "Гантели. Бедра. Прямые приседания", icon = R.drawable.ic_setka,))
    db.dataDao().addActivity(ActivityDB(idActivity = 23, name = "Гантели. Бедра. Боковые приседания.", icon = R.drawable.ic_setka,))
    db.dataDao().addActivity(ActivityDB(idActivity = 24, name = "Гантели. Бицепс.", icon = R.drawable.ic_setka,))
}
private fun createSetting( db: AppDatabase){
    db.dataDao().addSetting(SettingDB(parameter = R.string.speech_description, value = 1))
}
private fun createTrainingPlansTesting( db: AppDatabase) {

    val rest = 1
    val reps = 3
    val idTraining = db.dataDao().addTraining(TrainingDB(name = "Тестовая", idTraining = 1,
        speechId = addSpeechKit(db, bs = "Начало тренировки", ast = "", be = "", ae = "Тренировка окончена",)))
//Разминка
    var idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.UP, countRing = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    //Упражнение 1
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 4,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = reps, intervalReps = 1.0, timeRest = rest, goal = GoalSet.COUNT,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    //Упражнение 2
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 5,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = reps, intervalReps = 1.0, timeRest = rest, goal = GoalSet.COUNT,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

//Основная
    idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, countRing = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    //Упражнение 1
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 6,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = reps, intervalReps = 1.0, timeRest = rest, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
//Заминка
    idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.DOWN, countRing = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    //Упражнение 1
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 7,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 4", reps = reps, intervalReps = 1.0, timeRest = rest, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
}
private fun createTrainingPlansReal( db: AppDatabase
) {
    val idTraining = db.dataDao().addTraining(TrainingDB(name = "Зарядка", idTraining = 1,
        speechId = addSpeechKit(db, bs = "Начинаем ", ast = "", be = "", ae = "Тренировка закончена.",)))
    workUp( db, idTraining)
    workOut( db, idTraining)
    workDown( db, idTraining)
}
private fun createTrainingPlansArm( db: AppDatabase
) {
    val idTraining = db.dataDao().addTraining(TrainingDB(name = "Зарядка. Руки", idTraining = 2,
        speechId = addSpeechKit(db, bs = "Начинаем ", ast = "", be = "", ae = "Тренировка закончена.",)))
    workUp( db, idTraining)
    workOutArm( db, idTraining)
    workDown( db, idTraining)
}
private fun createTrainingPlansLeg( db: AppDatabase
){
    val idTraining = db.dataDao().addTraining(TrainingDB(name = "Зарядка. Ноги", idTraining = 3,
        speechId = addSpeechKit(db, bs = "Начинаем ", ast = "", be = "", ae = "Тренировка закончена.",)))
    workUp( db, idTraining)
    workOutLeg( db, idTraining)
    workDown( db, idTraining)
}
private fun workUp(db: AppDatabase, idTraining: Long){
    //Разминка
    val idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.UP, countRing = 1,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к разминке", ast = "", be = "", ae = "Разминка закончена",)))
    //Упражнение 1
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 3,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 2
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 4,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 3
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 5,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 4
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 6,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 4", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 5
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 7,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 5", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 6
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 8,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 1.1, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 7
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 9,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 1.1, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 8
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 10,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 1.1, timeRest = 10, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workOut(db: AppDatabase, idTraining: Long){
//Основная часть
    val idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, countRing = 1,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",)))
    //Упражнение 1
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 20,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 2
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 21,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 3
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 24,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 20, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 4
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 11,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 5
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 19,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 6
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 11,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 7
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 22,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 8
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 23,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 9
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 14,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 12,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 11
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 14,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRest = 10, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 12
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 13,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 10, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 16,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 16,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workDown(db: AppDatabase, idTraining: Long){
    val idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.DOWN, countRing = 1,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к заминке", ast = "", be = "", ae = "Зазминка закончена",)))
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 17,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 18,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workOutArm(db: AppDatabase, idTraining: Long){
//Основная часть
    val idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, countRing = 1,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",)))
    //Упражнение 1 Гантели. Плечи
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 20,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 10, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 4.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 15, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 4", reps = 15, intervalReps = 4.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 5", reps = 13, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 6", reps = 13, intervalReps = 4.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",)))

    //Упражнение 2 Приседания с выходом на носки
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 11,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

    //Упражнение 3  Гантели. Бицепс.
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 24,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 2.5, timeRest = 30, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 30, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 13, intervalReps = 2.5, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))

    //Упражнение 4 Кисти рук
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 19,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

    //Упражнение 5 Отжимания
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 14,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 13, intervalReps = 1.6, timeRest = 30, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 18, intervalReps = 1.6, timeRest = 30, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 17, intervalReps = 1.6, timeRest = 30, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))

    //Упражнение 6 Сейдза
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 12,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))

    //Упражнение 7 Велосипед на пресс
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 16,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 1.5, timeRest = 40, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.5, timeRest = 40, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 19, intervalReps = 1.5, timeRest =5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))

}
private fun workOutLeg(db: AppDatabase, idTraining: Long){
//Основная часть
    val idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, countRing = 1,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",)))
    //Упражнение 1 Гантели. Плечи
    var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 20,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRest = 5, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",)))

    //Упражнение 2 Приседания с выходом на носки
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 11,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 2.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 2.0, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

    //Упражнение 3 Сейдза
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 12,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 1.0, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

    //Упражнение 4 Гантели. Бедра. Прямые приседания
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 22,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",)))

    //Упражнение 5 Кидза
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 13,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

    //Упражнение 4 Гантели. Бедра. Прямые приседания
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 23,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRest = 20, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",)))

    //Упражнение 5 Отжимания
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 14,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 18, intervalReps = 1.6, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

    //Упражнение 6 Велосипед на пресс
    idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 16,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",)))
    db.dataDao().addSet(
        SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 60, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))

}
private fun addSpeechKit(db: AppDatabase, bs: String, ast: String, be: String, ae: String): Long{
    return db.dataDao().addSpeechKit(
        SpeechKitDB(
            idBeforeStart = db.dataDao().addSpeech( SpeechDB(message = bs)),
            idAfterStart = db.dataDao().addSpeech( SpeechDB( message = ast)),
            idBeforeEnd = db.dataDao().addSpeech( SpeechDB( message = be)),
            idAfterEnd = db.dataDao().addSpeech( SpeechDB( message = ae))
        )
    )
}