package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TemplatePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerWorkOut @Inject constructor(val speechManager:SpeechManager, private val playerRound: PlayerRound)
{
    suspend fun playingWorkOut(
        template: TemplatePlayer,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ) {
        speechManager.speech(template.getTraining().speech.beforeStart + " " + template.getTraining().name, pause, flowStateServiceMutable)
        speechManager.speech(template.getTraining().speech.afterStart, pause, flowStateServiceMutable )
        template.getTraining().rounds.forEachIndexed { index, _->
            playerRound.playingRound( template.apply { indexRound = index }, pause, flowStateServiceMutable)
        }
        speechManager.speech(template.getTraining().speech.beforeEnd, pause, flowStateServiceMutable )
        speechManager.speech(template.getTraining().speech.afterEnd, pause, flowStateServiceMutable )
    }
}