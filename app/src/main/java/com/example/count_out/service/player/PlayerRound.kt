package com.example.count_out.service.player

import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.SendToWorkService
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(template: SendToWorkService, sendToUI: SendToUI, ){
        lg("playingRound ${sendToUI.runningState.value}")
        if (sendToUI.runningState.value == RunningState.Started) {
            template.getRound()?.speech?.beforeStart?.let { speechManager.speech(sendToUI, it) }
            template.getRound()?.speech?.afterStart?.let { speechManager.speech(sendToUI, it) }
            template.getRound()?.exercise?.forEachIndexed { index, _->
                playerExercise.playingExercise( template.apply { indexExercise = index }, sendToUI) }
            template.getRound()?.speech?.beforeEnd?.let { speechManager.speech(sendToUI, it) }
            template.getRound()?.speech?.afterEnd?.let { speechManager.speech(sendToUI, it) }
        }
    }
}