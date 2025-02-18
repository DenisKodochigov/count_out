package com.example.framework.injection

import com.example.data.source.room.ExerciseSource
import com.example.data.source.room.RingSource
import com.example.data.source.room.RoundSource
import com.example.data.source.room.SetSource
import com.example.data.source.room.SpeechKitSource
import com.example.data.source.room.SpeechSource
import com.example.data.source.room.TrainingSource
import com.example.framework.room.source.ExerciseSourceImpl
import com.example.framework.room.source.RingSourceImpl
import com.example.framework.room.source.RoundSourceImpl
import com.example.framework.room.source.SetSourceImpl
import com.example.framework.room.source.SpeechKitSourceImpl
import com.example.framework.room.source.SpeechSourceImpl
import com.example.framework.room.source.TrainingSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//In the same package, create a new class called LocalDataSourceModule, in
//which we connect the abstractions to the bindings:

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRingSource(ringSource: RingSourceImpl): RingSource
    @Binds
    abstract fun bindRoundSource(roundSource: RoundSourceImpl): RoundSource
    @Binds
    abstract fun bindExerciseSource(exerciseSource: ExerciseSourceImpl): ExerciseSource
    @Binds
    abstract fun bindSpeechKitSource(speechKitSource: SpeechKitSourceImpl): SpeechKitSource
    @Binds
    abstract fun bindSpeechSource(speechSource: SpeechSourceImpl): SpeechSource
    @Binds
    abstract fun bindSetSource(setSource: SetSourceImpl): SetSource
    @Binds
    abstract fun bindTrainingSource(trainingSource: TrainingSourceImpl): TrainingSource

}
