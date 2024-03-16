package com.example.count_out.service.player

import androidx.compose.runtime.MutableState
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.StateWorkOut
import com.example.count_out.entity.TemplatePlayer
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    suspend fun playingSet(
        template: TemplatePlayer,
        pause: MutableState<Boolean>,
        flowStateServiceMutable: MutableStateFlow<StateWorkOut>
    ){
        if (template.getSet().reps > 0) {
            for (count in 1..template.getSet().reps){
                flowStateServiceMutable.value = StateWorkOut(set = template.getSet())
                delay((template.getSet().intervalReps * 1000).toLong())
            }
        }
        val textBeforeSet = if (template.getSet().reps > 0) {
            " " + template.getSet().reps.toString() + " повторений"
        } else if (template.getSet().duration > 0){
            if (template.getSet().duration < 1) " " + (template.getSet().duration * 60).toInt().toString() + " секунд"
            else if (template.getSet().duration == 1.0) " " + template.getSet().duration .toString() + " минута"
            else if (template.getSet().duration > 1.0 && template.getSet().duration < 5.0) " " +
                    template.getSet().duration .toString() + " минуты"
            else " " + template.getSet().duration .toString() + " минут"
        } else ""
        speechManager.speech(template.getSet().speech.beforeStart + textBeforeSet, pause, flowStateServiceMutable)
        speechManager.speech(template.getSet().speech.afterStart, pause, flowStateServiceMutable)

        if (template.getSet().reps > 0) {
            for (count in 1..template.getSet().reps){
                flowStateServiceMutable.value = StateWorkOut(set = template.getSet())
                speechManager.speakOutFlush(text = count.toString())
                log(true, "PlayerSet count: $count; interval: ${template.getSetIntervalReps()}")
                delay((template.getSetIntervalReps() * 1000).toLong())
            }
        } else if (template.getSet().duration > 0){
            delay((template.getSet().duration * 60 * 1000).toLong())
        }
        if (template.getSet().timeRest > 0) {
            speechManager.speech(template.getSet().timeRest.toString() + " секунд отдыха.", pause, flowStateServiceMutable)
            delay((template.getSet().timeRest * 1000).toLong())
        }
        speechManager.speech(template.getSet().speech.beforeEnd, pause, flowStateServiceMutable)
        speechManager.speech(template.getSet().speech.afterEnd, pause, flowStateServiceMutable)
    }
}