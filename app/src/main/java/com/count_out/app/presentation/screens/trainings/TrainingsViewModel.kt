package com.count_out.app.presentation.screens.trainings

import androidx.lifecycle.viewModelScope
import com.count_out.app.presentation.prime.Event
import com.count_out.app.presentation.prime.PrimeViewModel
import com.count_out.app.presentation.prime.ScreenState
import com.count_out.domain.entity.Training
import com.count_out.domain.use_case.trainings.AddTrainingUC
import com.count_out.domain.use_case.trainings.CopyTrainingUC
import com.count_out.domain.use_case.trainings.DeleteTrainingUC
import com.count_out.domain.use_case.trainings.GetTrainingsUC
import com.count_out.domain.use_case.trainings.SelectTrainingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingsViewModel @Inject constructor(
    private val converter: TrainingsConvertor,
    private val addTraining: AddTrainingUC,
    private val copyTraining: CopyTrainingUC,
    private val delTraining: DeleteTrainingUC,
    private val getTrainings: GetTrainingsUC,
    private val selectTraining: SelectTrainingUC,
): PrimeViewModel<TrainingsState, ScreenState<TrainingsState>>() {
    override fun initState(): ScreenState<TrainingsState> = ScreenState.Loading
    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingsEvent.BackScreen -> { navigate.backStack()}
            is TrainingsEvent.Run -> { navigate.goToScreenExecuteWorkout(event.id)}
            is TrainingsEvent.Edit -> { navigate.goToScreenTraining(event.id) }
            is TrainingsEvent.Gets -> { getTrainings() }
            is TrainingsEvent.Add -> { addTraining() }
            is TrainingsEvent.Copy -> { copyTraining(event.training) }
            is TrainingsEvent.Del -> { deleteTraining(event.training) }
            is TrainingsEvent.Select -> { selectTraining(event.training) }
        }
    }

    private fun getTrainings(){
        viewModelScope.launch {
            getTrainings.execute( GetTrainingsUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun addTraining(){
        viewModelScope.launch {
            addTraining.execute(AddTrainingUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }}
    private fun deleteTraining(training: Training){
        viewModelScope.launch {
            delTraining.execute( DeleteTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}
    private fun copyTraining(training: Training){
        viewModelScope.launch {
            copyTraining.execute( CopyTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        } }
    private fun selectTraining(training: Training){
        viewModelScope.launch {
            selectTraining.execute( SelectTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }}

}
//private fun getTrainings(){
//    viewModelScope.launch(Dispatchers.IO) {
//        runCatching { dataRepository.getTrainingsFlow() }.fold(
//            onSuccess = { list->
//                    list.map { converter.convert(it) }.collect { submitState(it) }
//                    list.collect{ _trainingsScreenState.update { currentState ->
//                    currentState.copy( trainings = it ) }}
//            },
//            onFailure = { messageApp.errorApi(it.message ?: "") }
//        )
//    }
//}
//    private val _trainingsScreenState = MutableStateFlow(
//        TrainingsScreenState(
//            trainings = emptyList(),
//            onAddTraining = { addTraining() },
//            onDeleteTraining = { trainingId -> deleteTraining(trainingId) },
//            onCopyTraining = { trainingId -> copyTraining(trainingId) },
//        )
//    )
//    val trainingsScreenState: StateFlow<TrainingsScreenState> = _trainingsScreenState.asStateFlow()
//    private fun addTraining(){ templateMy { dataRepository.addTraining() } }
//    private fun deleteTraining(id: Long){ templateMy { dataRepository.deleteTraining(id) } }
//    private fun copyTraining(id: Long){ templateMy { dataRepository.copyTraining(id) } }
//    private fun selectTraining(id: Long){ templateMy { dataRepository.selectTraining(id) } }
//    private fun templateMy( funDataRepository:() -> List<Training> ){
//        viewModelScope.launch(Dispatchers.IO) {
//            runCatching { funDataRepository() }.fold(
//                onSuccess = {
////                    lg("TrainingsViewModel ${ it.count()}")
////                    _trainingsScreenState.update { currentState ->
////                    currentState.copy( trainings = it ) }
//                            },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }