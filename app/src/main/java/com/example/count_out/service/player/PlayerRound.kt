package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TemplatePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerRound @Inject constructor(
    val speechManager:SpeechManager,
    private val playerExercise: PlayerExercise)
{
    suspend fun playingRound(
        template: TemplatePlayer,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        speechManager.speech( template.getRound().speech.beforeStart, pause, flowStateServiceMutable)
        speechManager.speech(template.getRound().speech.afterStart, pause, flowStateServiceMutable)
        template.getRound().exercise.forEachIndexed { index, _->
            playerExercise.playingExercise( template.apply { indexExercise = index }, pause, flowStateServiceMutable)
        }
        speechManager.speech(template.getRound().speech.beforeEnd, pause, flowStateServiceMutable)
        speechManager.speech(template.getRound().speech.afterEnd, pause, flowStateServiceMutable)
    }
}