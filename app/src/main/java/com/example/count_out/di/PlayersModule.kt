package com.example.count_out.di

import com.example.count_out.domain.SpeechManager
import com.example.count_out.service.player.PlayerExercise
import com.example.count_out.service.player.PlayerRound
import com.example.count_out.service.player.PlayerSet
import com.example.count_out.service.player.PlayerWorkOut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayersModule {
    @Singleton
    @Provides
    fun providePlayerWorkOut(speechManager: SpeechManager, playerRound: PlayerRound): PlayerWorkOut {
        return PlayerWorkOut(speechManager, playerRound)
    }
    @Singleton
    @Provides
    fun providePlayerRound(speechManager: SpeechManager, playerExercise: PlayerExercise): PlayerRound {
        return PlayerRound(speechManager, playerExercise)
    }
    @Singleton
    @Provides
    fun providePlayerExercise(speechManager: SpeechManager, playerSet: PlayerSet): PlayerExercise {
        return PlayerExercise(speechManager, playerSet)
    }
    @Singleton
    @Provides
    fun providePlayerSet(speechManager: SpeechManager): PlayerSet {
        return PlayerSet(speechManager)
    }
}