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
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.data.room.tables.TrainingDB
import com.example.count_out.entity.RoundType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    lateinit var database: AppDatabase
    private const val mode: Int = 1
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
                            Thread { prepopulateDb(database) }.start()
                        }
                    })
                    .build()
            }
            2 -> {
                database = Room.databaseBuilder(appContext, AppDatabase::class.java, "data.db")
                    .addCallback( object: RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Thread { prepopulateDb(database) }.start()
//                            Executors.newSingleThreadExecutor().execute {
//                                database.dataDao().newBasket(BasketEntity(nameBasket = "Test")) }
                        }
                    })
                    .build()
            }
            else -> {
                database = Room
                    .databaseBuilder( appContext, AppDatabase::class.java, "data.db").build()
            }
        }
        return database
    }

    private fun prepopulateDb( db: AppDatabase) {
        db.dataDao().addActivity(ActivityDB(idActivity = 1, name = "Бег", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 2, name = "Беговые лыжи", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 3, name = "Простучать бедра", icon = R.drawable.ic_setka,
            description = "Сядьте на край стула и кулаками простукивайте внешнюю сторону бедер."))
        db.dataDao().addActivity(ActivityDB(idActivity = 4, name = "Растирка ушей", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 5, name = "Растереть макушку", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 6, name = "Растереть виски", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 7, name = "Растереть заднюю часть шеи", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 8, name = "Прямой подъем колен", icon = R.drawable.ic_setka,
            description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к правой руке"))
        db.dataDao().addActivity(ActivityDB(idActivity = 9, name = "Диагональный подъем колен", icon = R.drawable.ic_setka,
            description = "Поставьте перед собой руки и поочередно подимайте ноги касаясь коленом ладони. Правую коленку к левой руке"))
        db.dataDao().addActivity(ActivityDB(idActivity = 10, name = "Приседания с выходом на носки", icon = R.drawable.ic_setka,
            description = "Глубокие приседания с подъемом на носках и вытягиванием рук вверх"))
        db.dataDao().addActivity(ActivityDB(idActivity = 11, name = "Сейдза.", icon = R.drawable.ic_setka,
            description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток."))
        db.dataDao().addActivity(ActivityDB(idActivity = 12, name = "Кидза.", icon = R.drawable.ic_setka,
            description = "Сядьте на согнутые в коленях ноги. Спина прямая, ягодицы касаются пяток, носки на подогнутых пальцах."))
        db.dataDao().addActivity(ActivityDB(idActivity = 13, name = "Отжимания", icon = R.drawable.ic_setka))
        db.dataDao().addActivity(ActivityDB(idActivity = 14, name = "Нога на ногу", icon = R.drawable.ic_setka,
            description = "Сядьте на стул. Положите одну ногу в районе ступни на другую. Медленно тяните вернюю ногу вниз, спина прямая."))
        db.dataDao().addActivity(ActivityDB(idActivity = 15, name = "Велосипед на пресс", icon = R.drawable.ic_setka,
            description = "Исходное положение: лежа на спине, руки за головой, ноги чуть согнуты. " +
                    "Руки не нужно сцеплять в замок, чтобы не помогать ими, надавливая на затылок. " +
                    "Подъемы корпуса должны осуществляться за счет мышц пресса. Поднимите обе ноги, " +
                    "не отрывая таз. Согните правую ногу в колене, поднимая ее к корпусу. Вторая нога" +
                    "остается на весу лишь слегка согнутой или прямой. Одновременно с этим движением " +
                    "оторвите верхнюю часть корпуса, поворачиваясь левым локтем в сторону правого колена. " +
                    "Важно не отрывать от пола поясницу. Касаться локтем колена необязательно. При " +
                    "подъеме корпуса выполняется выдох.Выпрямите правую ногу, одновременно сгибая левую. " +
                    "Движение ног должно быть плавным, имитирующим езду на велосипеде. Корпус при этом " +
                    "поворачивается другой стороной — теперь правым локтем вы тянитесь к левому колену. "))
        db.dataDao().addActivity(ActivityDB(idActivity = 16, name = "Расяжка ног", icon = R.drawable.ic_setka,
            description = "Сядьте на пол, раздвиньте ноги. Покачиваясь вперед, пытайтесь логтями дотянуться до пола между ног"))

        var idSpeech = db.dataDao().addSpeech(SpeechDB(
            beforeStart = "Начинаем ", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Тренировка закончена.",))
        val idTraining = db.dataDao().addTraining(TrainingDB(name = "Training_test", speechId = idSpeech))

        idSpeech = db.dataDao().addSpeech(SpeechDB(beforeStart = "Подготовьтесь к разминке", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Разминка закончена",))
        var idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.UP, speechId = idSpeech))
        idSpeech = db.dataDao().addSpeech(SpeechDB(beforeStart = "Упражнение", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Упражнение закончено",))
        var idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 3, speechId = idSpeech))
        idSpeech = db.dataDao().addSpeech(SpeechDB(beforeStart = "Подход ", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Подход закончен",))
        db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", speechId = idSpeech))

        idSpeech = db.dataDao().addSpeech(SpeechDB(beforeStart = "Подготовьтесь к основной части тренировки", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Основная часть закончена",))
            idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.OUT, speechId = idSpeech))
                idSpeech = db.dataDao().addSpeech(SpeechDB(
                    beforeStart = "Упражнение", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Упражнение закончено",))
                idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 3, speechId = idSpeech))
                idSpeech = db.dataDao().addSpeech(SpeechDB(
                    beforeStart = "Подход ", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Подход закончен",))
                db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", speechId = idSpeech))

        idSpeech = db.dataDao().addSpeech(SpeechDB(
            beforeStart = "Подготовьтесь к заминке", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Зазминка закончена",))
            idRound = db.dataDao().addRound(RoundDB(trainingId = idTraining, roundType = RoundType.DOWN, speechId = idSpeech))
                idSpeech = db.dataDao().addSpeech(SpeechDB(
                    beforeStart = "Упражнение", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Упражнение закончено",))
                idExercise = db.dataDao().addExercise(ExerciseDB(roundId = idRound, activityId = 2, speechId = idSpeech))
                idSpeech = db.dataDao().addSpeech(SpeechDB(
                    beforeStart = "Подход ", afterStart = "Начали", beforeEnd = "Закончили", afterEnd = "Подход закончен",))
                db.dataDao().addSet(SetDB(exerciseId = idExercise, name = "Set 1", speechId = idSpeech))
    }

    @Provides
    fun provideDataDao(database: AppDatabase): DataDao {
        return database.dataDao()
    }
}


