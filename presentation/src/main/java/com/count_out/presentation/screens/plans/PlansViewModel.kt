package com.count_out.presentation.screens.plans

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Speech
import com.count_out.domain.use_case.plans.CopyPlanUC
import com.count_out.domain.use_case.plans.DeletePlanUC
import com.count_out.domain.use_case.plans.GetPlansUC
import com.count_out.domain.use_case.plans.SelectPlanUC
import com.count_out.domain.use_case.speech.UpdateSpeechUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class PlansViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val copyPlanUC: CopyPlanUC,
    private val delPlanUC: DeletePlanUC,
    private val getPlansUC: GetPlansUC,
    private val selectPlanUC: SelectPlanUC,
    private val updateSpeechUC: UpdateSpeechUC
): PrimeViewModel<PlansState, PlansConvertor>() {

    override fun initScreenState(): ScreenState<PlansState> = ScreenState.Loading
    override fun initDataState(): PlansState = PlansState(event = { submitEvent(it)})
    override fun convertor(): PlansConvertor = PlansConvertor()

    override fun routeEvent(event: Event) {
        when (event) {
//            is PlansEvent.BackScreen -> { navigate.backStack()}
//            is PlansEvent.Edit -> { navigate.goToScreenTraining(event.item) }
            is PlansEvent.Run -> { runTraining(event.item) }
            is PlansEvent.Gets -> { getTrainings() }
            is PlansEvent.Copy -> { copyTraining(event.item) }
            is PlansEvent.Del -> { deleteTraining(event.item) }
            is PlansEvent.UpdateSpeech -> { updateSpeech(event.item) }
        }
    }

    init{
        getTrainings()
    }
    private fun runTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            selectPlanUC.execute(
                SelectPlanUC.Request(plan = plan)).collect {
                    submitState( it ) }
        }
//        navigate.goToScreenExecuteWorkout()
    }
    private fun getTrainings(){
        viewModelScope.launch(Dispatchers.IO) {
            getPlansUC.execute(GetPlansUC.Request).collect { submitState( it ) }
        } }
    private fun deleteTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            delPlanUC.execute( DeletePlanUC.Request(plan)).collect {
                submitState( it ) }
        }}
    private fun copyTraining(plan: Plan){
        viewModelScope.launch(Dispatchers.IO) {
            copyPlanUC.execute( CopyPlanUC.Request(plan)).collect { submitState( it ) }
        } }

    private fun updateSpeech(item: Speech){
        viewModelScope.launch(Dispatchers.IO) {
            updateSpeechUC.execute( UpdateSpeechUC.Request(item)).collect { submitState( it ) }
        }
    }
}