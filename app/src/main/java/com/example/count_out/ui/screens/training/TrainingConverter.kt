package com.example.count_out.ui.screens.training

import com.example.count_out.domain.use_case.UseCase
import com.example.count_out.ui.screens.prime.PrimeConvertor
import javax.inject.Inject

class TrainingConverter @Inject constructor():
    PrimeConvertor<UseCase.Response, TrainingState>() {

    override fun convertSuccess(data: UseCase.Response): TrainingState {
        return TrainingState(
            training = TODO(),
            enteredName = TODO(),
            showSpeechTraining = TODO(),
            showSpeechWorkUp = TODO(),
            showSpeechWorkOut = TODO(),
            showSpeechWorkDown = TODO(),
            showSpeechExercise = TODO(),
            showSpeechSet = TODO(),
            workUpCollapsing = TODO(),
            workOutCollapsing = TODO(),
            workDownCollapsing = TODO(),
            nameTraining = TODO(),
            roundId = TODO(),
            exercise = TODO(),
            set = TODO(),
            activities = TODO(),
            listCollapsingSet = TODO(),
            listCollapsingExercise = TODO(),
            showBottomSheetSpeech = TODO(),
            showBottomSheetSelectActivity = TODO(),
            onDismissSelectActivity = TODO(),
            onBaskScreen = TODO(),
            screenTextHeader = TODO(),
            listSpeech = TODO(),
            nameSection = TODO(),
            onConfirmationSpeech = TODO(),
            item = TODO(),
            onDismissSpeech = TODO()
        )
    }
}
