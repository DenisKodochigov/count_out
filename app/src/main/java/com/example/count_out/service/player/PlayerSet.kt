package com.example.count_out.service.player

import android.content.Context
import com.example.count_out.R
import com.example.count_out.data.room.tables.SetDB
import com.example.count_out.data.room.tables.SpeechDB
import com.example.count_out.domain.SpeechManager
import com.example.count_out.entity.GoalSet
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.VariablesInService
import com.example.count_out.entity.VariablesOutService
import com.example.count_out.helpers.delayMy
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject

class PlayerSet @Inject constructor(val speechManager:SpeechManager, val context: Context)
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
            nextExerciseSpeech(template = template, variablesOut = variablesOut )
            playRest(template, variablesOut)

            speechManager.speech(variablesOut, template.getSet().speech.beforeEnd)
            speechManager.speech(variablesOut, template.getSet().speech.afterEnd)
        }
    }
    private suspend fun playRest(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        val message = template.getSet().timeRest.toString() + " " + context.getString(R.string.rest_time)
        if (template.getSet().timeRest > 0) {
            speechManager.speech(variablesOut, SpeechDB( addMessage = message) )
            playDelay( duration = template.getSet().timeRest, variablesOut = variablesOut)
        }
    }
    private suspend fun nextExerciseSpeech(template: VariablesInService, variablesOut: VariablesOutService,
    ){
        if (template.lastSet()) {
            val nextExercise = template.getNextExercise()?.activity?.name ?: ""
            val message = if (nextExercise != "") {
                context.getString(R.string.next_exercise) + " " + nextExercise } else ""
            speechManager.speech(variablesOut, SpeechDB( message = message ) )
        }
    }
    private suspend fun bodyPlayingSet(
        template: VariablesInService,
        variablesOut: VariablesOutService,
    ){
        when (template.getSet().goal){
            GoalSet.COUNT -> playSetCOUNT(template, variablesOut)
            GoalSet.DURATION -> playSetDURATION(template, variablesOut)
            GoalSet.DISTANCE -> playSetDISTANCE(template, variablesOut)
            GoalSet.COUNT_GROUP -> playSetCOUNTGROUP(template, variablesOut)
        }
    }
    private suspend fun playSetCOUNT(template: VariablesInService, variablesOut: VariablesOutService,
    ){
        for (count in 1..template.getSet().reps){
            variablesOut.set.value = template.getSet()
            speechManager.speakOutFlush(text = count.toString(), variablesOut.stateRunning)
            delayMy((template.getSetIntervalReps() * 1000).toLong(), variablesOut.stateRunning)
        }
        variablesOut.set.value = SetDB()
    }
    private suspend fun playSetDURATION(template: VariablesInService, variablesOut: VariablesOutService,
    ){
        playDelay( duration = (template.getSet().duration * 60).toInt(), variablesOut = variablesOut)
    }
    private suspend fun playSetCOUNTGROUP(template: VariablesInService, variablesOut: VariablesOutService,
    ){
        val listWordCount = template.getSet().groupCount.split(",")
        if ( listWordCount.isNotEmpty()){
            for (count in 0..< template.getSet().reps){
                lg("Count group $count: ${listWordCount[count%listWordCount.size]}")
                variablesOut.set.value = template.getSet()
                speechManager.speakOutFlush(text = listWordCount[count%listWordCount.size], variablesOut.stateRunning)
                delayMy((template.getSetIntervalReps() * 1000).toLong(), variablesOut.stateRunning)
            }
            variablesOut.set.value = SetDB()
        }
    }
    private suspend fun playSetDISTANCE(template: VariablesInService, variablesOut: VariablesOutService,
    ){
    }
    private suspend fun playDelay(duration: Int, variablesOut: VariablesOutService,
    ){
        if (duration < 20) {
            countDelay(duration = duration, divider = 5, variablesOut = variablesOut) }
        else if (duration in 20..119) {
            countDelay(duration = duration, divider = 10, variablesOut = variablesOut) }
        else { countDelay(duration = duration, divider = 60, variablesOut = variablesOut) }
    }

    private suspend fun countDelay(duration: Int, divider: Int, variablesOut: VariablesOutService){
        for ( count in 1..duration){
            if ( count % divider == 0) {
                speechManager.speakOutFlush(text = count.toString(), variablesOut.stateRunning)
            }
            delayMy(1000L, variablesOut.stateRunning)
        }
    }
    private fun textBeforeSet(template: VariablesInService,): String{
        return when (template.getSet().goal){
            GoalSet.COUNT -> " " + template.getSet().reps.toString() + " повторений"
            GoalSet.COUNT_GROUP -> " " + template.getSet().reps.toString() + " повторений"
            GoalSet.DURATION ->
                if (template.getSet().duration < 1) " " + (template.getSet().duration * 60).toInt().toString() + " секунд"
                else if (template.getSet().duration == 1.0) " " + template.getSet().duration .toString() + " минута"
                else if (template.getSet().duration > 1.0 && template.getSet().duration < 5.0) " " +
                        template.getSet().duration .toString() + " минуты"
                else " " + template.getSet().duration .toString() + " минут"
            GoalSet.DISTANCE ->
                if (template.getSet().distance < 5.0) " " + template.getSet().distance .toString() + " километра"
                else " " + template.getSet().distance .toString() + " километров"
        }
    }
}