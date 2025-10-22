package com.count_out.framework.room.entity

import android.util.Log
import com.count_out.domain.entity.enums.Units
import com.count_out.framework.R
import com.count_out.framework.room.AppDataBase
import com.count_out.framework.room.db.activity.ActivityTb
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.old.settings.SettingTb
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanTb
import com.count_out.framework.room.db.ring.RingTb
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.speech.SpeechTb

private fun createPlanId0(db: AppDataBase) {
    val idPlan = db.planDao().insert(PlanTb(name = "", idPlan = 0,))
    insertSpeeches(db, planId = idPlan, bs = "Начало тренировки", ae = "Тренировка окончена")
//Разминка
    var idPart = db.partDao().insert(PartTb(planId = idPlan))
    insertSpeeches(db, partId = idPart)
//Основная
    idPart = db.partDao().insert(PartTb(planId = idPlan))
    insertSpeeches(db, partId = idPart)
    val idRing = db.ringDao().insert(RingTb(partId = idPart))
    insertSpeeches(db, ringId = idRing)
    val idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 1, idView = 1))
    insertSpeeches(db, exerId = idExercise)
    val idSet = db.setDao().insert(SetTb(exerciseId = idExercise, name = "",
        goal = 2, durationV = 1440.0))
    insertSpeeches(db, setId = idSet)
//Заминка
    idPart = db.partDao().insert(PartTb(planId = idPlan))
    insertSpeeches(db, partId = idPart)
    insertRecordWorkout(db)
    insertRecordCount(db)
}
fun prepopulateRealDb( db: AppDataBase){
    createSetting(db)
    createActivity(db)
    createTrainingPlansReal(db)
    createTrainingPlansArm(db)
    createTrainingPlansLeg(db)
}
fun prepopulateTestDb( db: AppDataBase){
    createSetting(db)
    createActivity(db)
    createTrainingPlansTesting(db)
}
private fun createActivity( db: AppDataBase){
    db.activityDao().insert(ActivityTb(idActivity = 1, name = "Бег", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 2, name = "Беговые лыжи", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 3, name = "Простучать бедра", icon = R.drawable.ic_setka,
        description = "Сядьте на край стула и кулаками простукивайте внешнюю сторону бедер."))
    db.activityDao().insert(ActivityTb(idActivity = 4, name = "Растереть уши", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 5, name = "Растереть макушку", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 6, name = "Растереть виски", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 7, name = "Растереть заднюю часть шеи", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 8, name = "Прямой подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к правой руке"))
    db.activityDao().insert(ActivityTb(idActivity = 9, name = "Диагональный подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к левой руке"))
    db.activityDao().insert(ActivityTb(idActivity = 10, name = "Паук", icon = R.drawable.ic_setka,
        description = "Руки за голову. Поднимайте колени к локтям одновременно наклянясь в сторону."))
    db.activityDao().insert(ActivityTb(idActivity = 11, name = "Приседания с выходом на носки", icon = R.drawable.ic_setka,
        description = "Глубокие приседания с подъемом на носках и вытягиванием рук вверх"))
    db.activityDao().insert(ActivityTb(idActivity = 12, name = "Сейдза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток."))
    db.activityDao().insert(ActivityTb(idActivity = 13, name = "Кидза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток, носки на подогнутых пальцах."))
    db.activityDao().insert(ActivityTb(idActivity = 14, name = "Отжимания", icon = R.drawable.ic_setka))
    db.activityDao().insert(ActivityTb(idActivity = 15, name = "Нога на ногу", icon = R.drawable.ic_setka,
        description = "Сядьте на стул. Положите одну ногу в районе ступни на другую. Медленно тяните вернюю ногу вниз, спина прямая."))
    db.activityDao().insert(ActivityTb(idActivity = 16, name = "Велосипед на пресс", icon = R.drawable.ic_setka,
        description = "Исходное положение: лежа на спине, руки за головой, ноги чуть согнуты и приподняты над полом " +
                "не отрывая таз. Согните правую ногу в колене, поднимая ее к корпусу. Вторая нога" +
                "остается на весу. Одновременно поворачиваясь левым локтем и приподнимая корпус в сторону правого колена. " +
                "Другой стороной — теперь правым локтем вы тянитесь к левому колену. "))
    db.activityDao().insert(ActivityTb(idActivity = 17, name = "Растяжка внутерней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, раздвиньте ноги. Покачиваясь вперед, пытайтесь логтями дотянуться до пола между ног"))
    db.activityDao().insert(ActivityTb(idActivity = 18, name = "Растяжка нижней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, вытяните перед собой ноги. Тянитесь вперед на выдохе."))
    db.activityDao().insert(ActivityTb(idActivity = 19, name = "Кисти рук", icon = R.drawable.ic_setka,
        description = "Вытяните рукки и сжимайте в кулак и разжимайте кисть"))
    db.activityDao().insert(ActivityTb(idActivity = 20, name = "Гантели. Плечи", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Поднимите перед собой, разведите в стороны и поворотом оси гантелей из вертикального в горизонтальное положение. Опустите руки в исходное стостояние."))
    db.activityDao().insert(ActivityTb(idActivity = 21, name = "Гантели. Плечи. Обартный ход", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Разведите руки в стороны. Сведите перед собой с изменением оси гантелей с горизонтального в вертикальное положение. Опустите руки в исходное положение."))

    db.activityDao().insert(ActivityTb(idActivity = 22, name = "Гантели. Бедра. Прямые приседания", icon = R.drawable.ic_setka,))
    db.activityDao().insert(ActivityTb(idActivity = 23, name = "Гантели. Бедра. Боковые приседания.", icon = R.drawable.ic_setka,))
    db.activityDao().insert(ActivityTb(idActivity = 24, name = "Гантели. Бицепс.", icon = R.drawable.ic_setka,))
}
private fun createSetting( db: AppDataBase){
    db.settingDao().insert(SettingTb(parameter = R.string.speech_description, value = 1))
}

private fun createTrainingPlansTesting( db: AppDataBase) {
    createPlanId0( db )
    val rest = 10.0
    val reps = 3
    val idPlan = db.planDao().insert(PlanTb(name = "Тестовая"))
    insertSpeeches(db, planId = idPlan, bs = "Начало тренировки", ae = "Тренировка окончена")
//Разминка
    var idPart = db.partDao().insert(PartTb( planId = idPlan ))
    insertSpeeches(db, partId = idPart, bs = "Разминка")
    var idRing = db.ringDao().insert(RingTb(partId = idPart))
    insertSpeeches(db, ringId = idRing)
    //Упражнение 1
    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 4, idView = 0)) //"Растереть уши"
    insertSpeeches(db, exerId = idExercise)
    var idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise, name = "Set 2", reps = reps, distanceV = 10.0,
            distanceU = Units.KM.ordinal, timeRestV = rest, goal = 0, weightV = 2.0,
            weightU = Units.GR.ordinal, durationU = Units.S.ordinal,
        ))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise,
            name = "Set 1",
            reps = reps,
            durationV = 15.0,
            durationU = Units.S.ordinal,
            timeRestV = rest,
            goal = 2,
            weightU = Units.GR.ordinal,
            distanceU = Units.MT.ordinal,
        ))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
    //Упражнение 2
    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 5, idView = 1))  //"Растереть макушку"
    insertSpeeches(db, exerId = idExercise)
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise,
            name = "Set 2",
            reps = reps,
            distanceV = 10.0,
            distanceU = Units.KM.ordinal,
            timeRestV = rest,
            goal = 1,
            weightU = Units.GR.ordinal,
            durationU = Units.S.ordinal,
        ))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
    //Упражнение 3
    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 6, idView = 2))  //"Растереть макушку"
    insertSpeeches(db, exerId = idExercise)
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise, name = "Set 3", reps = reps, intervalReps = 1.0,
            timeRestV = rest, goal = 3, weightU = Units.GR.ordinal,
            distanceU = Units.MT.ordinal, durationU = Units.S.ordinal))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
    //Упражнение 4
    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 7, idView = 3))  //"Растереть макушку"
    insertSpeeches(db, exerId = idExercise)
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise, name = "Set 4", reps = reps, intervalReps = 1.0,
            timeRestV = rest, goal = 3, weightU = Units.GR.ordinal,
            distanceU = Units.MT.ordinal, durationU = Units.S.ordinal))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")

//Основная
    idPart = db.partDao().insert(PartTb(planId = idPlan ))
    insertSpeeches(db, partId = idPart, bs = "Основная")
    idRing = db.ringDao().insert(RingTb(partId = idPart))
    insertSpeeches(db, ringId = idRing)
    //Упражнение 1
    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 6, idView = 0))  //"Растереть виски"
    insertSpeeches(db, exerId = idExercise)
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise, name = "Set 3", reps = reps, intervalReps = 1.0,
            timeRestV = rest, goal = 3, weightU = Units.GR.ordinal,
            distanceU = Units.MT.ordinal, durationU = Units.S.ordinal))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
//Заминка
    idPart = db.partDao().insert(PartTb(planId = idPlan ))
    insertSpeeches(db, partId = idPart, bs = "Заминка")
    idRing = db.ringDao().insert(RingTb(partId = idPart))
    insertSpeeches(db, ringId = idRing)
    //Упражнение 1
    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idRing, activityId = 7, idView = 0))//"Растереть заднюю часть шеи"
    insertSpeeches(db, exerId = idExercise)
    idSet = db.setDao().insert(SetTb(
            exerciseId = idExercise, name = "Set 4", reps = reps, intervalReps = 1.0,
            timeRestV = rest, goal = 3, weightU = Units.GR.ordinal,
            distanceU = Units.MT.ordinal, durationU = Units.S.ordinal))
    insertSpeeches(db, setId = idSet, bs = "Старт", ae = "Конец")
    insertRecordWorkout(db)
    insertRecordCount(db)
}
private fun insertSpeeches(
    db: AppDataBase, planId: Long? = null, partId: Long? = null,ringId: Long? = null,exerId: Long? = null,setId: Long? = null,
    bs: String = "", ast: String = "", be: String = "", ae: String = ""
){
    db.speechDao().insert(SpeechTb(0L, setId, exerId, ringId, partId, planId, message = bs))
    db.speechDao().insert(SpeechTb(0L, setId, exerId, ringId, partId, planId, message = ast))
    db.speechDao().insert(SpeechTb(0L, setId, exerId, ringId, partId, planId, message = be))
    db.speechDao().insert(SpeechTb(0L, setId, exerId, ringId, partId, planId, message = ae))
//    db.speechKitDao().update(SpeechKitTable(id, idS1, idS2,idS3,idS4))
//    return db.speechKitDao().insert(
//        SpeechKitTable(
//            idBeforeStart = db.speechDao().insert( SpeechTable(message = bs)),
//            idAfterStart = db.speechDao().insert( SpeechTable( message = ast)),
//            idBeforeEnd = db.speechDao().insert( SpeechTable( message = be)),
//            idAfterEnd = db.speechDao().insert( SpeechTable( message = ae)),
//        )
//    )
}
private fun createTrainingPlansReal( db: AppDataBase) {
//    createplanId0( db )
//    val idPlan = db.dataDao().insertTraining(PlanTb(name = "Зарядка",
//        speechId = insertSpeechKit(db, bs = "Начинаем", ae = "Тренировка закончена.",)))
//    workUp( db, idPlan)
//    workOut( db, idPlan)
//    workDown( db, idPlan)
}
private fun createTrainingPlansArm( db: AppDataBase) {
//    val idPlan = db.dataDao().insertTraining(PlanTb(name = "Зарядка. Руки",
//        speechId = insertSpeechKit(db, bs = "Начинаем", ae = "Тренировка закончена.",)))
//    workUp( db, idPlan)
//    workOutArm( db, idPlan)
//    workDown( db, idPlan)
}
private fun createTrainingPlansLeg( db: AppDataBase){
//    val idPlan = db.dataDao().insertTraining(PlanTb(name = "Зарядка. Ноги",
//        speechId = insertSpeechKit(db, bs = "Начинаем ", ae = "Тренировка закончена.",)))
//    workUp( db, idPlan)
//    workOutLeg( db, idPlan)
//    workDown( db, idPlan)
}
private fun workUp(db: AppDataBase, idPlan: Long){
//    //Разминка
//    val idPart = db.partDao().insert(PartTb(planId = idPlan, roundType = RoundType.UP, countRing = 1,
//        speechId = insertSpeechKit(db, bs = "Подготовьтесь к разминке", ae = "Разминка закончена",)))
//    //Упражнение 1
//    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 3, idView = 0,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 15, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 2
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 4,  idView = 1,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", duration = 15, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 3
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 5,  idView = 2,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", duration = 15, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 4
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 6, idView = 3,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 4", duration = 15, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 5
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 7, idView = 4,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 5", duration = 15, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 6
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 8, idView = 5,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 1.1, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 7
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 9, idView = 6,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 1.1, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 8
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 10, idView = 7,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 1.1, timeRestV = 10, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
}
private fun workOut(db: AppDataBase, idPlan: Long){
////Основная часть
//    val idPart = db.partDao().insert(PartTb(planId = idPlan, roundType = RoundType.OUT, countRing = 1,
//        speechId = insertSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ae = "Основная часть закончена",)))
//    //Упражнение 1
//    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 20,  idView = 0,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 2
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 21, idView = 1,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 3
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 24, idView = 2,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 20, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 4
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 11, idView = 31,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 5
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 19, idView = 4,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 6
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 11, idView = 5,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено. Востановите дыхание.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 7
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 22, idView = 6,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 8
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 23, idView = 7,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 9
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 14, idView = 8,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 10
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 12, idView = 9,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 11
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 14, idView = 101,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRestV = 10, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 12
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 13, idView = 11,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 10, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 10
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 16, idView = 12,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено. Востановите дыхание.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    //Упражнение 10
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 16,  idView = 13,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено. Востановите дыхание.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
}
private fun workDown(db: AppDataBase, idPlan: Long){
//    val idPart = db.partDao().insert(PartTb(planId = idPlan, roundType = RoundType.DOWN, countRing = 1,
//        speechId = insertSpeechKit(db, bs = "Подготовьтесь к заминке", ae = "Зазминка закончена",)))
//    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 17, idView = 1,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 18, idView = 2,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
}
private fun workOutArm(db: AppDataBase, idPlan: Long){
////Основная часть
//    val idPart = db.partDao().insert(PartTb(planId = idPlan, roundType = RoundType.OUT, countRing = 1,
//        speechId = insertSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ae = "Основная часть закончена",)))
//    //Упражнение 1 Гантели. Плечи
//    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 20, idView = 0,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 10, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Прямой ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 4.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Обратный ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 15, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Прямой ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 4", reps = 15, intervalReps = 4.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Обратный ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 5", reps = 13, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Прямой ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 6", reps = 13, intervalReps = 4.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Обратный ход", ae = "",)))
//
//    //Упражнение 2 Приседания с выходом на носки
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 11, idView = 1,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))
//
//    //Упражнение 3  Гантели. Бицепс.
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 24,  idView = 2,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 2.5, timeRestV = 30, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRestV = 30, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 13, intervalReps = 2.5, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//
//    //Упражнение 4 Кисти рук
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 19,  idView = 3,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
//    )
//
//    //Упражнение 5 Отжимания
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 14, idView = 4,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 13, intervalReps = 1.6, timeRestV = 30, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 18, intervalReps = 1.6, timeRestV = 30, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 17, intervalReps = 1.6, timeRestV = 30, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//
//    //Упражнение 6 Сейдза
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 12, idView = 5,
//        speechId = insertSpeechKit(db, bs = "", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//
//    //Упражнение 7 Велосипед на пресс
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 16, idView = 6,
//        speechId = insertSpeechKit(db, bs = "", ae = "Упражнение закончено. Востановите дыхание.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 1.5, timeRestV = 40, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.5, timeRestV = 40, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 19, intervalReps = 1.5, timeRestV =5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",)))

}
private fun workOutLeg(db: AppDataBase, idPlan: Long){
////Основная часть
//    val idPart = db.partDao().insert(PartTb(planId = idPlan, roundType = RoundType.OUT, countRing = 1,
//        speechId = insertSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ae = "Основная часть закончена",)))
//    //Упражнение 1 Гантели. Плечи
//    var idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 20, idView = 0,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Прямой ход", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRestV = 5, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Обратный ход", ae = "",)))
//
//    //Упражнение 2 Приседания с выходом на носки
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 11, idView = 1,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 2.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 2.0, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))
//
//    //Упражнение 3 Сейдза
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 12, idView = 2,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 60, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, )))
//
//    //Упражнение 4 Гантели. Бедра. Прямые приседания
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 22, idView = 3,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Левая в переди", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Правая в переди", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Левая в переди", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Правая в переди", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Левая в переди", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Правая в переди", ae = "",)))
//
//    //Упражнение 5 Кидза
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 13, idView = 4,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", duration = 30, timeRestV = 0, goal = GoalSet.DURATION,
//            speechId = insertSpeechKit(db, )))
//
//    //Упражнение 4 Гантели. Бедра. Прямые приседания
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 23, idView = 5,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на левую.", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на правую.", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на левую.", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на правую.", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на левую.", ae = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRestV = 20, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, bs = "Приседание на правую.", ae = "",)))
//
//    //Упражнение 5 Отжимания
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 14, idView = 6,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae  = "",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 18, intervalReps = 1.6, timeRestV = 0, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))
//
//    //Упражнение 6 Велосипед на пресс
//    idExercise = db.exerciseDao().insert(ExerciseTb(ringId = idPart, activityId = 16,  idView = 7,
//        speechId = insertSpeechKit(db, bs = "Упражнение", ae = "Упражнение закончено. Востановите дыхание.",)))
//    db.setDao().insert(
//        SetTb(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRestV = 60, goal = GoalSet.COUNT,
//            speechId = insertSpeechKit(db, )))

}

private fun insertRecordWorkout(db: AppDataBase){
//    db.dataDao().insertWorkout(
//        WorkoutTable(
//            planId = 1, isSelected = true,
//            name = "", insertress = "Мурино, Россия",
//            latitude = 60.0564957, longitude = 30.4331601,
//            timeZone = "Europe/Moscow", timeStart = 1042440587, timeEnd = 1042453270,
//            averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
//            averageSpeed = 0.0,
//            maxSpeed = 0.0,
//            minSpeed = 0.0,
//            averageHeartRate = 0.0, maxHeartRate = 0.0, minHeartRate = 0.0,
//            resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0, resultAmount = 0.0, resultRange = 0.0,
//            temperature = 6.6, relativeHumidity2m = 99, apparentTemperature = 4.3, precipitation = 0.0,
//            rain = 0.0, showers = 0.0, snowfall = 0.0, weatherCode = 3, cloudCover = 100,
//            pressureMsl = 1011.4, surfacePressure = 1008.7, windSpeed10m = 2.6, windDirection10m = 115,
//            windGusts10m = 4.4,
//        )
//    )
}
private fun insertRecordCount(db: AppDataBase){
//    val count = CountDB(
//        workoutId = 1, heartRate = 0, latitude = 60.0564957, longitude = 30.4331601,
//        altitude = 41.39999771118160, timeLocation = 1728844945990, accuracy = 100.0f,
//        speed = 0.0f, distance = 0.0f, idPlan = 1,
//        sensor1 = 0.0, sensor2 = 0.0, sensor3 = 0.0,
//        idSet = 0, phaseWorkout = 0, activityId = 0,
//    )
//
//    db.dataDao().insertCounts(
//        listOf( count, count, count, count,count, count, count, count, count,
//            count.copy(idSet = 1, phaseWorkout = 0, activityId = 4),
//            count.copy(idSet = 1, phaseWorkout = 0, activityId = 4),
//            count.copy(idSet = 1, phaseWorkout = 0, activityId = 4),
//            count.copy(idSet = 1, phaseWorkout = 0, activityId = 4),
//        )
//    )
}