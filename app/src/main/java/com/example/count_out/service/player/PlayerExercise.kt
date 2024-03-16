package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TemplatePlayer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerExercise @Inject constructor(val speechManager:SpeechManager, private val playerSet: PlayerSet)
{
    suspend fun playingExercise(
        template: TemplatePlayer,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        speechManager.speech(template.getExercise().speech.beforeStart + " " +
                template.getExercise().activity.name, pause, flowStateServiceMutable)
        speechManager.speech(template.getExercise().activity.description, pause, flowStateServiceMutable)
        speechManager.speech(template.getExercise().speech.afterStart, pause, flowStateServiceMutable)
        template.getExercise().sets.forEachIndexed { index, _->
            playerSet.playingSet(template.apply { indexSet = index }, pause, flowStateServiceMutable)
        }
        speechManager.speech(template.getExercise().speech.beforeEnd, pause, flowStateServiceMutable)
        speechManager.speech(template.getExercise().speech.afterEnd, pause, flowStateServiceMutable)
    }
}