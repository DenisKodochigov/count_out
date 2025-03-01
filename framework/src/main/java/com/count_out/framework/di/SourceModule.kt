package com.count_out.framework.di

import com.count_out.data.source.framework.BleSource
import com.count_out.data.source.room.ActivitySource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.RoundSource
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.data.source.room.TrainingSource
import com.count_out.framework.room.source.ActivitySourceImpl
import com.count_out.framework.room.source.ExerciseSourceImpl
import com.count_out.framework.room.source.RingSourceImpl
import com.count_out.framework.room.source.RoundSourceImpl
import com.count_out.framework.room.source.SetSourceImpl
import com.count_out.framework.room.source.SpeechKitSourceImpl
import com.count_out.framework.room.source.SpeechSourceImpl
import com.count_out.framework.room.source.TrainingSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    abstract fun bindTrainingSource(trainingSource: TrainingSourceImpl): TrainingSource
    @Binds
    abstract fun bindRingSource(ringSource: RingSourceImpl): RingSource
    @Binds
    abstract fun bindRoundSource(roundSource: RoundSourceImpl): RoundSource
    @Binds
    abstract fun bindExerciseSource(exerciseSource: ExerciseSourceImpl): ExerciseSource
    @Binds
    abstract fun bindSetSource(setSource: SetSourceImpl): SetSource
    @Binds
    abstract fun bindActivitySource(activitySource: ActivitySourceImpl): ActivitySource
    @Binds
    abstract fun bindSpeechKitSource(speechKitSource: SpeechKitSourceImpl): SpeechKitSource
    @Binds
    abstract fun bindSpeechSource(speechSource: SpeechSourceImpl): SpeechSource
}
