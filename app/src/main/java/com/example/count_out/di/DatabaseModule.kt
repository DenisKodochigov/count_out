package com.example.count_out.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.count_out.R
import com.example.count_out.data.room.AppDatabase
import com.example.count_out.data.room.DataDao
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.ExerciseDB
import com.example.count_out.data.room.tables.RoundDB
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.SpeechKitDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.RoundType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    lateinit var database: AppDatabase
    private var mode: Int = 1
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        when (mode) {
            0 -> database = Room.inMemoryDatabaseBuilder( appContext, AppDatabase::class.java).build()
            1 -> {
                database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateTestDb(database) }.start()
                        }
                    })
                    .build()
            }
            2 -> {
                database = Room.databaseBuilder(appContext, AppDatabase::class.java, "data.db")
                    .addCallback( object: RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateRealDb(database) }.start()
//                            Executors.newSingleThreadExecutor().execute {
//                                database.dataDao().newBasket(BasketEntity(nameBasket = "Test")) }
                        }
                    })
                    .build()
            }
            else -> {
                database = Room.databaseBuilder( appContext, AppDatabase::class.java, "data.db").build()
            }
        }
        return database
    }

    private fun prepopulateRealDb( db: AppDatabase) {
        createSetting(db)
        createActivity(db)
        createTrainingPlansReal(db)
    }
    private fun prepopulateTestDb( db: AppDatabase) {
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
        db.dataDao().addActivity(ActivityDB(idActivity = 20, name = "Гантели. Плечи. Прямой ход", icon = R.drawable.ic_setka,
            description = "Руки опущены вдоль тела. Поднимите перед собой, разведите в стороны и поворотом оси гантелей из вертикального в горизонтальное положение. Опустите руки в исходное стостояние."))
        db.dataDao().addActivity(ActivityDB(idActivity = 21, name = "Гантели. Плечи. Обартный ход", icon = R.drawable.ic_setka,
            description = "Руки опущены вдоль тела. Разведите руки в стороны. Сведите перед собой с изменением оси гантелей с горизонтального в вертикальное положение. Опустите руки в исходное положение."))
    }
    private fun createSetting( db: AppDatabase){
        db.dataDao().addSetting(SettingDB(parameter = R.string.speech_description, value = 1))
    }
    private fun createTrainingPlansTesting( db: AppDatabase) {

        val idTraining = db.dataDao().addTraining(TrainingDB(name = "Тестовая", idTraining = 1,
            speechId = addSpeechKit(db, bs = "До начала тренировки", ast = "После начала тренировки", be = "До конца тренировки", ae = "После конца тренировки",)))
//Разминка
        var idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.UP, countRing = 1,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
        //Упражнение 1
        var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 3,
            speechId = addSpeechKit(db, bs = "", ast = "", be = "", ae = "",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 10, intervalReps = 1.5, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "", be = "", ae = "",)))
    }
    private fun createTrainingPlansReal( db: AppDatabase) {

        val idTraining = db.dataDao().addTraining(TrainingDB(name = "Зарядка", idTraining = 1,
            speechId = addSpeechKit(db, bs = "Начинаем ", ast = "", be = "", ae = "Тренировка закончена.",)))
//Разминка
        var idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.UP, countRing = 1,
            speechId = addSpeechKit(db, bs = "Подготовьтесь к разминке", ast = "", be = "", ae = "Разминка закончена",)))
        //Упражнение 1
        var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 3,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 2
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 4,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 2", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 3
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 5,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 3", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 4
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 6,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 4", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 5
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 7,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 5", duration = 0.25, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
//Основная часть
        idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, countRing = 1,
            speechId = addSpeechKit(db, bs = "Подготовьтесь к основной части тренировки", ast = "", be = "", ae = "Основная часть закончена",)))
        //Упражнение 1
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 20,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 15, intervalReps = 4.0, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 2
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 21,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 2", reps = 15, intervalReps = 4.0, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 3
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 8,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 1.1, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 4
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 9,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 2", reps = 30, intervalReps = 1.1, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 5
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 10,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 3", reps = 30, intervalReps = 1.1, timeRest = 10, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 6
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 11,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 30, intervalReps = 2.0, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 7
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 19,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 8
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 14,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 17, intervalReps = 1.6, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 9
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 12,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 10
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 16,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", reps = 20, intervalReps = 1.7, timeRest = 0, goal = GoalSet.COUNT,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        //Упражнение 11
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 13,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
//Заминка
        idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.DOWN, countRing = 1,




            speechId = addSpeechKit(db, bs = "Подготовьтесь к заминке", ast = "", be = "", ae = "Зазминка закончена",)))
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 17,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
        idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 18,
            speechId = addSpeechKit(db, bs = "Упражнение", ast = "", be = "", ae = "Упражнение закончено",)))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", duration = 0.5, timeRest = 0, goal = GoalSet.DURATION,
            speechId = addSpeechKit(db, bs = "Подход ", ast = "Старт", be = "", ae = "Подход закончен",)))
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

    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}


