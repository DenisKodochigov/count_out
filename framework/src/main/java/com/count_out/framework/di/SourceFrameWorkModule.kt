package com.count_out.framework.di

import com.count_out.data.source.local.LastPlanSource
import com.count_out.data.source.local.SettingsSource
import com.count_out.data.source.room.ActivitySource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.PlanSource
import com.count_out.data.source.room.RingSource
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechSource
import com.count_out.framework.datastore.LastPlanSourceImpl
import com.count_out.framework.datastore.SettingsSourceImpl
import com.count_out.framework.room.source.ActivitySourceImpl
import com.count_out.framework.room.source.ExerciseSourceImpl
import com.count_out.framework.room.source.PartSourceImpl
import com.count_out.framework.room.source.PlanSourceImpl
import com.count_out.framework.room.source.RingSourceImpl
import com.count_out.framework.room.source.SetSourceImpl
import com.count_out.framework.room.source.SpeechSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceFrameWorkModule {
    @Binds
    abstract fun bindTrainingSource(trainingSource: PlanSourceImpl): PlanSource
    @Binds
    abstract fun bindPartSource(partSource: PartSourceImpl): PartSource
    @Binds
    abstract fun bindRingSource(ringSource: RingSourceImpl): RingSource
    @Binds
    abstract fun bindExerciseSource(exerciseSource: ExerciseSourceImpl): ExerciseSource
    @Binds
    abstract fun bindSetSource(setSource: SetSourceImpl): SetSource
    @Binds
    abstract fun bindActivitySource(activitySource: ActivitySourceImpl): ActivitySource
    @Binds
    abstract fun bindSpeechSource(speechSource: SpeechSourceImpl): SpeechSource
    @Binds
    abstract fun bindSettingsSource(speechSource: SettingsSourceImpl): SettingsSource
    @Binds
    abstract fun bindLastPlanSource(speechSource: LastPlanSourceImpl): LastPlanSource
}
