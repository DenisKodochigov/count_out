package com.count_out.presentation.screens.plans

import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.use_case.plans.CopyTrainingUC
import com.count_out.domain.use_case.plans.DeleteTrainingUC
import com.count_out.domain.use_case.plans.GetTrainingsUC
import com.count_out.domain.use_case.plans.SelectTrainingUC
import com.count_out.domain.use_case.plans.UpdatePlanUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class PlansViewModel @Inject constructor(
    private val copyTrainingUC: CopyTrainingUC,
    private val delTrainingUC: DeleteTrainingUC,
    private val getTrainingsUC: GetTrainingsUC,
    private val updatePlanUC: UpdatePlanUC,
    private val selectTrainingUC: SelectTrainingUC,
    private val updateSpeechUC: UpdateSpeechUC
): PrimeViewModel<PlansState, PlansConvertor>() {
    override fun initScreenState(): ScreenState<PlansState> = ScreenState.Loading
    override fun initDataState(): PlansState = PlansState(event = { submitEvent(it)})
    override fun convertor(): PlansConvertor = PlansConvertor()

    override fun routeEvent(event: Event) {
        when (event) {
            is PlansEvent.BackScreen -> { navigate.backStack()}
            is PlansEvent.Run -> { runTraining(event.item) }
            is PlansEvent.Edit -> { navigate.goToScreenTraining(event.item) }
            is PlansEvent.Gets -> { getTrainings() }
            is PlansEvent.Copy -> { copyTraining(event.item) }
            is PlansEvent.Del -> { deleteTraining(event.item) }
            is PlansEvent.Update -> { updateTraining(event.item) }
            is PlansEvent.UpdateSpeech -> { updateSpeech(event.item) }
        }
    }
    private fun runTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            selectTrainingUC.execute(
                SelectTrainingUC.Request(plan = plan)).collect {
                    submitState( it ) }
        }
        navigate.goToScreenExecuteWorkout()
    }
    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            getTrainingsUC.execute(GetTrainingsUC.Request).collect { submitState( it ) }
        } }
    private fun deleteTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            delTrainingUC.execute( DeleteTrainingUC.Request(plan)).collect {
                submitState( it ) }
        }}
    private fun copyTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            copyTrainingUC.execute( CopyTrainingUC.Request(plan)).collect { submitState( it ) }
        } }
    private fun updateTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            updatePlanUC.execute( UpdatePlanUC.Request(plan)).collect { submitState( it ) }
        }}
    private fun updateSpeech(item: Speech){
        viewModelScope.launch(Dispatchers.IO) {
            updateSpeechUC.execute( UpdateSpeechUC.Request(item)).collect { submitState( it ) }
        }
    }
}