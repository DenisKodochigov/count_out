package com.example.count_out.entity

import com.count_out.framework.room.db.traking.TemporaryTable
import com.count_out.framework.room.db.traking.WorkoutTable
import com.example.count_out.R
import com.example.count_out.entity.enums.Goal
import com.example.count_out.entity.enums.RoundType
import com.example.count_out.entity.enums.Units
import com.example.count_out.entity.models.TrainingImpl
import com.example.count_out.entity.workout.Training
import com.example.count_out.framework.room.AppDataBase
import com.example.count_out.framework.room.db.activity.ActivityTable
import com.example.count_out.framework.room.db.exercise.ExerciseTable
import com.example.count_out.framework.room.db.round.RoundTable
import com.example.count_out.framework.room.db.set.SetTable
import com.example.count_out.framework.room.db.settings.SettingTable
import com.example.count_out.framework.room.db.speech.SpeechTable
import com.example.count_out.framework.room.db.speech_kit.SpeechKitTable
import com.example.count_out.framework.room.db.training.TrainingTable

object Plugins
{
    private val listRound = mutableListOf<RoundTable>()
    private val listTr = mutableListOf<TrainingTable>()
//    val listEx = mutableListOf<ExerciseTable>()
//    val listSpeech = mutableListOf<SpeechTable>()
//    val listActivity = listOf<Activity>(
//        ActivityTable(idActivity = 0, name = "Run", icon = R.drawable.ic_setka),
//        ActivityTable(idActivity = 1, name = "Ski", icon = R.drawable.ic_setka))

    fun init(){
        var idR = 0L
        for (t in 0..10L){
            listRound.add(RoundTable( idRound = idR++, trainingId = t, roundType = RoundType.WorkUp.ordinal))
            listRound.add(RoundTable( idRound = idR++, trainingId = t, roundType = RoundType.WorkOut.ordinal))
            listRound.add(RoundTable( idRound = idR++, trainingId = t, roundType = RoundType.WorkDown.ordinal))
            listTr.add(
                TrainingTable( idTraining = t,
                    name = "Training $t",
//                    rounds = mutableListOf(listRound[listRound.size-3], listRound[listRound.size-2], listRound[listRound.size-1])
                )
            )
        }
    }


//    fun item (id: Long): Training {
//        return TrainingImpl()
////        return listTr.find { it.idTraining == id } ?: TrainingTable() as Training
//    }
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
    db.activityDao().add(ActivityTable(idActivity = 1, name = "Бег", icon = R.drawable.ic_setka))
    db.activityDao().add(ActivityTable(idActivity = 2, name = "Беговые лыжи", icon = R.drawable.ic_setka))
    db.activityDao().add(
        ActivityTable(idActivity = 3, name = "Простучать бедра", icon = R.drawable.ic_setka,
        description = "Сядьте на край стула и кулаками простукивайте внешнюю сторону бедер.")
    )
    db.activityDao().add(ActivityTable(idActivity = 4, name = "Растереть уши", icon = R.drawable.ic_setka))
    db.activityDao().add(ActivityTable(idActivity = 5, name = "Растереть макушку", icon = R.drawable.ic_setka))
    db.activityDao().add(ActivityTable(idActivity = 6, name = "Растереть виски", icon = R.drawable.ic_setka))
    db.activityDao().add(ActivityTable(idActivity = 7, name = "Растереть заднюю часть шеи", icon = R.drawable.ic_setka))
    db.activityDao().add(
        ActivityTable(idActivity = 8, name = "Прямой подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к правой руке")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 9, name = "Диагональный подъем колен", icon = R.drawable.ic_setka,
        description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к левой руке")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 10, name = "Паук", icon = R.drawable.ic_setka,
        description = "Руки за голову. Поднимайте колени к локтям одновременно наклянясь в сторону.")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 11, name = "Приседания с выходом на носки", icon = R.drawable.ic_setka,
        description = "Глубокие приседания с подъемом на носках и вытягиванием рук вверх")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 12, name = "Сейдза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток.")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 13, name = "Кидза.", icon = R.drawable.ic_setka,
        description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток, носки на подогнутых пальцах.")
    )
    db.activityDao().add(ActivityTable(idActivity = 14, name = "Отжимания", icon = R.drawable.ic_setka))
    db.activityDao().add(
        ActivityTable(idActivity = 15, name = "Нога на ногу", icon = R.drawable.ic_setka,
        description = "Сядьте на стул. Положите одну ногу в районе ступни на другую. Медленно тяните вернюю ногу вниз, спина прямая.")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 16, name = "Велосипед на пресс", icon = R.drawable.ic_setka,
        description = "Исходное положение: лежа на спине, руки за головой, ноги чуть согнуты и приподняты над полом " +
                "не отрывая таз. Согните правую ногу в колене, поднимая ее к корпусу. Вторая нога" +
                "остается на весу. Одновременно поворачиваясь левым локтем и приподнимая корпус в сторону правого колена. " +
                "Другой стороной — теперь правым локтем вы тянитесь к левому колену. ")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 17, name = "Растяжка внутерней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, раздвиньте ноги. Покачиваясь вперед, пытайтесь логтями дотянуться до пола между ног")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 18, name = "Растяжка нижней части бедер ног", icon = R.drawable.ic_setka,
        description = "Сядьте на пол, вытяните перед собой ноги. Тянитесь вперед на выдохе.")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 19, name = "Кисти рук", icon = R.drawable.ic_setka,
        description = "Вытяните рукки и сжимайте в кулак и разжимайте кисть")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 20, name = "Гантели. Плечи", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Поднимите перед собой, разведите в стороны и поворотом оси гантелей из вертикального в горизонтальное положение. Опустите руки в исходное стостояние.")
    )
    db.activityDao().add(
        ActivityTable(idActivity = 21, name = "Гантели. Плечи. Обартный ход", icon = R.drawable.ic_setka,
        description = "Руки опущены вдоль тела. Разведите руки в стороны. Сведите перед собой с изменением оси гантелей с горизонтального в вертикальное положение. Опустите руки в исходное положение.")
    )

    db.activityDao().add(ActivityTable(idActivity = 22, name = "Гантели. Бедра. Прямые приседания", icon = R.drawable.ic_setka,))
    db.activityDao().add(ActivityTable(idActivity = 23, name = "Гантели. Бедра. Боковые приседания.", icon = R.drawable.ic_setka,))
    db.activityDao().add(ActivityTable(idActivity = 24, name = "Гантели. Бицепс.", icon = R.drawable.ic_setka,))
}
private fun createSetting( db: AppDataBase){
    db.settingDao().add(SettingTable(parameter = R.string.speech_description, value = 1))
}

private fun createTrainingId0( db: AppDataBase) {
    val idTraining = db.trainingDao().add( TrainingTable(name = "", idTraining = 0,
        speechId = addSpeechKit(db, bs = "Начало тренировки", ast = "", be = "", ae = "Тренировка окончена",))
    )
//Разминка
    db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkUp.ordinal,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
//Основная
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkOut.ordinal,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    val idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 1,  idView = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "", goal = Goal.Duration.ordinal, duration = 1440.0,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
//Заминка
    db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkDown.ordinal,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    addRecordWorkout(db)
    addRecordCount(db)
}
private fun createTrainingPlansTesting( db: AppDataBase) {
    createTrainingId0( db )
    val rest = 10.0
    val reps = 3
    val idTraining = db.trainingDao().add(
        TrainingTable(name = "Тестовая",
        speechId = addSpeechKit(db, bs = "Начало тренировки", ast = "", be = "", ae = "Тренировка окончена",))
    )
//Разминка
    var idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkUp.ordinal,
        speechId = addSpeechKit(db, bs = "Разминка", ast = "", be = "", ae = "",))
    )
    //Упражнение 1
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 4, idView = 0,  //"Растереть уши"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = reps, distance = 10.0, distanceU = Units.KM.ordinal, timeRest = rest, goal = Goal.Distance.ordinal,
            speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = reps, duration = 15.0, durationU = Units.S.ordinal, timeRest = rest, goal = Goal.Duration.ordinal,
        speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
    //Упражнение 2
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 5, idView = 1,  //"Растереть макушку"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = reps, distance = 10.0, distanceU = Units.KM.ordinal, timeRest = rest, goal = Goal.Distance.ordinal,
        speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
    //Упражнение 3
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 6, idView = 2,  //"Растереть макушку"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = reps, intervalReps = 1.0, timeRest = rest, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
    //Упражнение 4
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 7, idView = 3,  //"Растереть макушку"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", reps = reps, intervalReps = 1.0, timeRest = rest, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )

//Основная
    idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkOut.ordinal,
        speechId = addSpeechKit(db, bs = "Основная", ast = "", be = "", ae = "",))
    )
    //Упражнение 1
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 6, idView = 0,  //"Растереть виски"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = reps, intervalReps = 1.0, timeRest = rest, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
//Заминка
    idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkDown.ordinal,
        speechId = addSpeechKit(db, bs = "Заминка", ast = "", be = "", ae = "",))
    )
    //Упражнение 1
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 7, idView = 0,  //"Растереть заднюю часть шеи"
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", reps = reps, intervalReps = 1.0, timeRest = rest, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Старт", ast = "", be = "", ae = "Конец",))
    )
    addRecordWorkout(db)
    addRecordCount(db)
}
private fun createTrainingPlansReal( db: AppDataBase) {
    createTrainingId0( db )
    val idTraining = db.trainingDao().add(
        TrainingTable(name = "Зарядка",
        speechId = addSpeechKit(db, bs = "Начинаем", ast = "", be = "", ae = "Тренировка закончена.",))
    )
    workUp( db, idTraining)
    workOut( db, idTraining)
    workDown( db, idTraining)
}
private fun createTrainingPlansArm( db: AppDataBase) {
    val idTraining = db.trainingDao().add(
        TrainingTable(name = "Зарядка. Руки",
        speechId = addSpeechKit(db, bs = "Начинаем", ast = "", be = "", ae = "Тренировка закончена.",))
    )
    workUp( db, idTraining)
    workOutArm( db, idTraining)
    workDown( db, idTraining)
}
private fun createTrainingPlansLeg( db: AppDataBase){
    val idTraining = db.trainingDao().add(
        TrainingTable(name = "Зарядка. Ноги",
        speechId = addSpeechKit(db, bs = "Начинаем ", ast = "", be = "", ae = "Тренировка закончена.",))
    )
    workUp( db, idTraining)
    workOutLeg( db, idTraining)
    workDown( db, idTraining)
}
private fun workUp(db: AppDataBase, idTraining: Long){
    //Разминка
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkUp.ordinal,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к разминке", ast = "", be = "", ae = "Разминка закончена",))
    )
    //Упражнение 1
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 3, idView = 0,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 15.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 2
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 4,  idView = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", duration = 15.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 3
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 5,  idView = 2,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", duration = 15.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 4
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 6, idView = 3,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", duration = 15.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 5
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 7, idView = 4,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 5", duration = 15.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 6
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 8, idView = 5,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 1.1, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 7
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 9, idView = 6,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 1.1, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 8
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 10, idView = 7,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 1.1, timeRest = 10.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workOut(db: AppDataBase, idTraining: Long){
//Основная часть
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkOut.ordinal,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",))
    )
    //Упражнение 1
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 20,  idView = 0,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 2
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 21, idView = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 3
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 24, idView = 2,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 20, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 4
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 11, idView = 31,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 5
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 19, idView = 4,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 6
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 11, idView = 5,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 7
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 22, idView = 6,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 8
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 23, idView = 7,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 2.5, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 9
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 14, idView = 8,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 12, idView = 9,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 11
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 14, idView = 101,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRest = 10.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 12
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 13, idView = 11,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 10.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 16, idView = 12,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    //Упражнение 10
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 16,  idView = 13,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workDown(db: AppDataBase, idTraining: Long){
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkDown.ordinal,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к заминке", ast = "", be = "", ae = "Зазминка закончена",))
    )
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 17, idView = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 18, idView = 2,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
}
private fun workOutArm(db: AppDataBase, idTraining: Long){
//Основная часть
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkOut.ordinal,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",))
    )
    //Упражнение 1 Гантели. Плечи
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 20, idView = 0,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 10, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 4.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 15, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", reps = 15, intervalReps = 4.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 5", reps = 13, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 6", reps = 13, intervalReps = 4.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",))
    )

    //Упражнение 2 Приседания с выходом на носки
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 11, idView = 1,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 20.00, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

    //Упражнение 3  Гантели. Бицепс.
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 24,  idView = 2,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 10, intervalReps = 2.5, timeRest = 30.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 30.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 13, intervalReps = 2.5, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

    //Упражнение 4 Кисти рук
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 19,  idView = 3,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

    //Упражнение 5 Отжимания
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 14, idView = 4,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 13, intervalReps = 1.6, timeRest = 30.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 18, intervalReps = 1.6, timeRest = 30.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 17, intervalReps = 1.6, timeRest = 30.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

    //Упражнение 6 Сейдза
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 12, idView = 5,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

    //Упражнение 7 Велосипед на пресс
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 16, idView = 6,
        speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 1.5, timeRest = 40.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.5, timeRest = 40.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 19, intervalReps = 1.5, timeRest =5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "Старт", be = "", ae = "",))
    )

}
private fun workOutLeg(db: AppDataBase, idTraining: Long){
//Основная часть
    val idRound = db.roundDao().add(
        RoundTable(trainingId = idTraining, roundType = RoundType.WorkOut.ordinal,
        speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",))
    )
    //Упражнение 1 Гантели. Плечи
    var idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 20, idView = 0,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Прямой ход", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRest = 5.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Обратный ход", ast = "", be = "", ae = "",))
    )

    //Упражнение 2 Приседания с выходом на носки
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 11, idView = 1,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 2.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 2.0, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

    //Упражнение 3 Сейдза
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 12, idView = 2,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 60.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

    //Упражнение 4 Гантели. Бедра. Прямые приседания
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 22, idView = 3,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Левая в переди", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Правая в переди", ast = "", be = "", ae = "",))
    )

    //Упражнение 5 Кидза
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 13, idView = 4,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", duration = 30.0, timeRest = 0.0, goal = Goal.Duration.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

    //Упражнение 4 Гантели. Бедра. Прямые приседания
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 23, idView = 5,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 3", reps = 20, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 4", reps = 20, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 5", reps = 18, intervalReps = 2.5, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на левую.", ast = "", be = "", ae = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 6", reps = 18, intervalReps = 2.5, timeRest = 20.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "Приседание на правую.", ast = "", be = "", ae = "",))
    )

    //Упражнение 5 Отжимания
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 14, idView = 6,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae  = "",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 18, intervalReps = 1.6, timeRest = 0.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

    //Упражнение 6 Велосипед на пресс
    idExercise = db.exerciseDao().add(
        ExerciseTable(roundId = idRound, activityId = 16,  idView = 7,
        speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено. Востановите дыхание.",))
    )
    db.setDao().add(
        SetTable(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 60.0, goal = Goal.Count.ordinal,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",))
    )

}
private fun addSpeechKit(db: AppDataBase, bs: String, ast: String, be: String, ae: String): Long{
    return db.speechKitDao().add(
        SpeechKitTable(
            idBeforeStart = db.speechDao().add( SpeechTable(message = bs)),
            idAfterStart = db.speechDao().add( SpeechTable( message = ast)),
            idBeforeEnd = db.speechDao().add( SpeechTable( message = be)),
            idAfterEnd = db.speechDao().add( SpeechTable( message = ae))
        )
    )
}

private fun addRecordWorkout(db: AppDataBase){
    db.trackingDao().addWorkout(
        WorkoutTable(
            trainingId = 1, isSelected = true,
            name = "", address = "Мурино, Россия",
            latitude = 60.0564957, longitude = 30.4331601,
            timeZone = "Europe/Moscow", timeStart = 1042440587, timeEnd = 1042453270,
            averagePace = 0.0, maxPace = 0.0, minPace = 0.0,
            averageSpeed = 0.0,
            maxSpeed = 0.0,
            minSpeed = 0.0,
            averageHeartRate = 0.0, maxHeartRate = 0.0, minHeartRate = 0.0,
            resultSpeed = 0.0, resultTime = 0.0, resultWeight = 0.0, resultAmount = 0.0, resultRange = 0.0,
            temperature = 6.6, relativeHumidity2m = 99, apparentTemperature = 4.3, precipitation = 0.0,
            rain = 0.0, showers = 0.0, snowfall = 0.0, weatherCode = 3, cloudCover = 100,
            pressureMsl = 1011.4, surfacePressure = 1008.7, windSpeed10m = 2.6, windDirection10m = 115,
            windGusts10m = 4.4,
        )
    )
}
private fun addRecordCount(db: AppDataBase){
    val count = TemporaryTable(
        heartRate = 0, latitude = 60.0564957, longitude = 30.4331601,
        altitude = 41.39999771118160, timeLocation = 1728844945990, accuracy = 100.0f,
        speed = 0.0f, distance = 0.0f, idTraining = 1,
        idSet = 0, phaseWorkout = 0, activityId = 0,
    )

    db.trackingDao().addRecordMetric(count.copy(idSet = 1, phaseWorkout = 0, activityId = 4))
    db.trackingDao().addRecordMetric(count.copy(idSet = 1, phaseWorkout = 0, activityId = 4))
    db.trackingDao().addRecordMetric(count.copy(idSet = 1, phaseWorkout = 0, activityId = 4))
    db.trackingDao().addRecordMetric(count.copy(idSet = 1, phaseWorkout = 0, activityId = 4))
    db.trackingDao().addRecordMetric(count.copy(idSet = 1, phaseWorkout = 0, activityId = 4))

}