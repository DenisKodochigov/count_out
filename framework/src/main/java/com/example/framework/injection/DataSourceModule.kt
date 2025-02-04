package com.example.framework.injection

import com.example.data.source.room.SetSource
import com.example.data.source.room.TrainingSource
import com.example.framework.room.source.SetSourceImpl
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
    abstract fun bindSetSource(setSource: SetSourceImpl): SetSource

    @Binds
    abstract fun bindTrainingSource(trainingSource: TrainingSourceImpl): TrainingSource

}
