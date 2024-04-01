package com.example.count_out.service.player

import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.helpers.delayMy
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager)
{
    suspend fun playingSet(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        if (variablesOut.stateRunning.value == StateRunning.Started) {
            val textBeforeSet = textBeforeSet(template)
            template.getSet().speech.beforeStart.addMessage = textBeforeSet
            speechManager.speech(variablesOut, template.getSet().speech.beforeStart)
            speechManager.speech(variablesOut, template.getSet().speech.afterStart)

            bodyPlayingSet(template, variablesOut)

            if (template.getSet().timeRest > 0) {
                speechManager.speech(variablesOut,
                    SpeechDB( addMessage = template.getSet().timeRest.toString() + " секунд отдыха."))
                delayMy((template.getSet().timeRest * 1000).toLong(), variablesOut.stateRunning)
            }
            speechManager.speech(variablesOut, template.getSet().speech.beforeEnd)
            speechManager.speech(variablesOut, template.getSet().speech.afterEnd)
        }
    }
    private suspend fun bodyPlayingSet(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        when (template.getSet().goal){
            GoalSet.COUNT -> playSetCount(template, variablesOut)
            GoalSet.DURATION -> playSetDURATION(template, variablesOut)
            GoalSet.DESTINATION -> {}
        }
    }
    private suspend fun playSetCount(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        for (count in 1..template.getSet().reps){
            variablesOut.set.value = template.getSet()
            speechManager.speakOutFlush(text = count.toString(), variablesOut.stateRunning)
            delayMy((template.getSetIntervalReps() * 1000).toLong(), variablesOut.stateRunning)
        }
        variablesOut.set.value = SetDB()
    }
    private suspend fun playSetDURATION(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        delayMy((template.getSet().duration * 60 * 1000).toLong(), variablesOut.stateRunning)
    }
//    private suspend fun playSetDESTINATION(
//        template: VariablesInService,
//        variablesOut: VariablesOutService,
//    ){
//
//    }

    private fun textBeforeSet(template: VariablesInService,): String{
        return when (template.getSet().goal){
            GoalSet.COUNT -> " " + template.getSet().reps.toString() + " повторений"
            GoalSet.DURATION ->
                if (template.getSet().duration < 1) " " + (template.getSet().duration * 60).toInt().toString() + " секунд"
                else if (template.getSet().duration == 1.0) " " + template.getSet().duration .toString() + " минута"
                else if (template.getSet().duration > 1.0 && template.getSet().duration < 5.0) " " +
                        template.getSet().duration .toString() + " минуты"
                else " " + template.getSet().duration .toString() + " минут"
            GoalSet.DESTINATION ->
                if (template.getSet().distance < 5.0) " " + template.getSet().distance .toString() + " километра"
                else " " + template.getSet().distance .toString() + " километров"
        }
    }
}