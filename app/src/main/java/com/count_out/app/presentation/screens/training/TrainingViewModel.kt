package com.count_out.app.presentation.screens.training

import androidx.lifecycle.viewModelScope
import com.count_out.app.presentation.models.ActionWithActivityImpl
import com.count_out.app.presentation.models.ActionWithSetImpl
import com.count_out.app.presentation.models.DataForChangeSequenceImpl
import com.count_out.app.presentation.prime.Event
import com.count_out.app.presentation.prime.PrimeViewModel
import com.count_out.app.presentation.prime.ScreenState
import com.count_out.domain.entity.Activity
import com.count_out.domain.entity.Exercise
import com.count_out.domain.entity.Training
import com.count_out.domain.use_case.training.AddExerciseUC
import com.count_out.domain.use_case.training.AddSetUC
import com.count_out.domain.use_case.training.ChangeActivityUC
import com.count_out.domain.use_case.training.ChangeNameTrainingUC
import com.count_out.domain.use_case.training.ChangeSequenceExerciseUC
import com.count_out.domain.use_case.training.ChangeSetUC
import com.count_out.domain.use_case.training.CopyExerciseUC
import com.count_out.domain.use_case.training.CopySetUC
import com.count_out.domain.use_case.training.DelExerciseUC
import com.count_out.domain.use_case.training.DeleteSetUC
import com.count_out.domain.use_case.training.GetTrainingUC
import com.count_out.domain.use_case.training.SelectActivityUC
import com.count_out.domain.use_case.training.SetColorActivityUC
import com.count_out.domain.use_case.trainings.DelTrainingUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class TrainingViewModel @Inject constructor(
    private val converter: TrainingConverter,
    private val getTraining: GetTrainingUC,
    private val delTraining: DelTrainingUC,
    private val changeNameTraining: ChangeNameTrainingUC,
    private val addExercise: AddExerciseUC,
    private val copyExercise: CopyExerciseUC,
    private val delExercise: DelExerciseUC,
    private val changeSequenceExercise: ChangeSequenceExerciseUC,
    private val selectActivity: SelectActivityUC,
    private val setColorActivity: SetColorActivityUC,
    private val changeActivity: ChangeActivityUC,
    private val addSet: AddSetUC,
    private val copySet: CopySetUC,
    private val deleteSet: DeleteSetUC,
    private val changeSet: ChangeSetUC,
): PrimeViewModel<TrainingState, ScreenState<TrainingState>>() {

    override fun initState(): ScreenState<TrainingState> = ScreenState.Loading

    override fun routeEvent(event: Event) {
        when (event) {
            is TrainingEvent.BackScreen -> { navigate.backStack()}
            is TrainingEvent.GetTraining -> { getTraining(event.id) }
            is TrainingEvent.DelTraining -> { delTraining(event.training) }
            is TrainingEvent.ChangeNameTraining -> { changeNameTraining(event.training)}
            is TrainingEvent.AddExercise -> { addExercise(event.exercise) }
            is TrainingEvent.CopyExercise -> { copyExercise(event.exercise) }
            is TrainingEvent.DelExercise -> { deleteExercise(event.exercise) }
            is TrainingEvent.ChangeSequenceExercise -> { changeSequenceExercise(event.item) }
            is TrainingEvent.SelectActivity -> { selectActivity(event.activity) }
            is TrainingEvent.SetColorActivity -> { setColorActivity(event.activity) }
            is TrainingEvent.ChangeActivity -> { changeActivity(event.activity) }
            is TrainingEvent.AddSet -> { addSet(event.item) }
            is TrainingEvent.CopySet -> { copySet(event.item) }
            is TrainingEvent.DeleteSet -> { deleteSet(event.item) }
            is TrainingEvent.ChangeSet -> { changeSet(event.item) }
        }
    }
    fun getTraining(id: Long) {
        viewModelScope.launch {
            getTraining.execute( GetTrainingUC.Request(id))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun delTraining(training: Training){
        viewModelScope.launch {
            delTraining.execute( DelTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeNameTraining(training: Training){
        viewModelScope.launch {
            changeNameTraining.execute( ChangeNameTrainingUC.Request(training))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSequenceExercise(item: DataForChangeSequenceImpl){
        viewModelScope.launch {
            changeSequenceExercise.execute( ChangeSequenceExerciseUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun addExercise(exercise: Exercise){
        viewModelScope.launch {
            addExercise.execute( AddExerciseUC.Request(exercise))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copyExercise(exercise: Exercise){
        viewModelScope.launch {
            copyExercise.execute( CopyExerciseUC.Request(exercise))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            delExercise.execute( DelExerciseUC.Request(exercise))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun selectActivity(activity: ActionWithActivityImpl){
        viewModelScope.launch {
            selectActivity.execute( SelectActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun setColorActivity(activity: Activity){
        viewModelScope.launch {
            setColorActivity.execute( SetColorActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeActivity(activity: Activity){
        viewModelScope.launch {
            changeActivity.execute( ChangeActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun addSet(item:ActionWithSetImpl){
        viewModelScope.launch {
            addSet.execute( AddSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun copySet(item:ActionWithSetImpl){
        viewModelScope.launch {
            copySet.execute( CopySetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun deleteSet(item:ActionWithSetImpl){
        viewModelScope.launch {
            deleteSet.execute( DeleteSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
    private fun changeSet(item:ActionWithSetImpl){
        viewModelScope.launch {
            changeSet.execute( ChangeSetUC.Request(item))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }
}
//}
//    private fun deleteSet(trainingId:Long, setId: Long){
//        templateMy { dataRepository.deleteSet( trainingId, setId) } }
//    private fun setSpeech(speech: SpeechKit, item: Any?) {
//        templateMy { dataRepository.setSpeech(trainingScreenState.value.training.idTraining,speech, item) } }
//    private fun deleteTraining(trainingId: Long){
//        templateNothing { dataRepository.deleteTrainingNothing(trainingId) } }
//    private fun changeNameTraining(training: Training){
//        templateMy { dataRepository.changeNameTraining(training, name) } }
//    private fun copyExercise(exercise: Exercise){
//        templateMy { dataRepository.copyExercise(trainingId, exerciseId) } }
//    private fun addExercise(roundId: Long, set: SetDB){
//        templateMy { dataRepository.addExercise(trainingScreenState.value.training.idTraining, roundId ) } }
//    private fun deleteExercise(exercise: Exercise){
//        templateMy { dataRepository.deleteExercise(trainingId, exerciseId) } }
//
//    private fun setActivityToExercise(exerciseId: Long, activityId: Long) {
//        templateMy{dataRepository.setActivityToExercise(
//            trainingScreenState.value.training.idTraining, exerciseId, activityId)} }
//    private fun onSetColorActivity(activityId: Long, color: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.onSetColorActivity( activityId, color) }
//                .fold( onSuccess = { }, onFailure = { messageApp.errorApi(it.message ?: "") })
//        }
//    }
//    private fun templateMy( funDataRepository:() -> Training){
//        viewModelScope.launch(Dispatchers.IO) {
////            kotlin.runCatching { funDataRepository() }.fold(
////                onSuccess = { _trainingScreenState.update { currentState ->
////                    currentState.copy(
////                        training = it,
////                        showBottomSheetSelectActivity = mutableStateOf(false),
////                        enteredName = mutableStateOf(it.name) ) } },
////                onFailure = { messageApp.errorApi(it.message ?: "") }
////            )
//        }
//    }
//    private fun templateNothing( funDataRepository:() -> Unit){
//        viewModelScope.launch(Dispatchers.IO) {
////            kotlin.runCatching { funDataRepository() }.fold(
////                onSuccess = {  },
////                onFailure = { messageApp.errorApi(it.message ?: "") }
////            )
//        }
//    }




//@HiltViewModel
//class TrainingViewModel @Inject constructor(
//    private val messageApp: MessageApp,
//    private val dataRepository: DataRepository
//): ViewModel() {
//    private val _trainingScreenState = MutableStateFlow(
//        TrainingScreenState(
//            training = TrainingDB(),
//            enteredName = mutableStateOf(""),
//            changeNameTraining = { training, name -> changeNameTraining(training, name) },
//            onDeleteTraining = { trainingId -> deleteTraining(trainingId) },
//            onConfirmationSpeech = { speech, item -> setSpeech( speech, item ) },
//            onAddExercise = { roundId, set -> addExercise( roundId, set )},
//            onCopyExercise = { trainingId, exerciseId -> copyExercise(trainingId, exerciseId)},
//            onDeleteExercise = { trainingId, exerciseId -> deleteExercise(trainingId, exerciseId)},
//            changeSequenceExercise = { trainingId, roundId, from, to ->
//                    changeSequenceExercise(trainingId,roundId, from, to)},
//            onSelectActivity = {
//                    exerciseId, activityId -> setActivityToExercise(exerciseId, activityId) },
//            onSetColorActivity = {
//                    activityId, color -> onSetColorActivity(activityId = activityId, color = color) },
//            onCopySet = { trainingId, setId -> copySet(trainingId, setId)},
//            onDeleteSet = { trainingId, setId -> deleteSet(trainingId, setId)},
//            onAddUpdateSet = { idExercise, set -> addUpdateSet(idExercise, set) },
//            onChangeSet = { set -> addUpdateSet(set.exerciseId, set) },
//        )
//    )
//    val trainingScreenState: StateFlow<TrainingScreenState> = _trainingScreenState.asStateFlow()
//
//    fun getTraining(id: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.getActivities() }.fold(
//                onSuccess = {
//                    _trainingScreenState.update { currentState -> currentState.copy(activities = it) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//        templateMy { dataRepository.getTraining(id) }
//    }
//    private fun addUpdateSet(exerciseId:Long, set: Set){
//        templateMy { dataRepository.addUpdateSet(
//            trainingScreenState.value.training.idTraining, exerciseId, set) } }
//    private fun copySet(trainingId:Long, setId: Long){
//        templateMy { dataRepository.copySet( trainingId, setId) } }
//    private fun deleteSet(trainingId:Long, setId: Long){
//        templateMy { dataRepository.deleteSet( trainingId, setId) } }
//    private fun setSpeech(speech: SpeechKit, item: Any?) {
//        templateMy { dataRepository.setSpeech(trainingScreenState.value.training.idTraining,speech, item) } }
//    private fun deleteTraining(trainingId: Long){
//        templateNothing { dataRepository.deleteTrainingNothing(trainingId) } }
//    private fun changeNameTraining(training: Training, name: String){
//        templateMy { dataRepository.changeNameTraining(training, name) } }
//    private fun copyExercise(trainingId: Long, exerciseId: Long){
//        templateMy { dataRepository.copyExercise(trainingId, exerciseId) } }
//    private fun addExercise(roundId: Long, set: SetDB){
//        templateMy { dataRepository.addExercise(trainingScreenState.value.training.idTraining, roundId ) } }
//    private fun deleteExercise(trainingId: Long, exerciseId: Long){
//        templateMy { dataRepository.deleteExercise(trainingId, exerciseId) } }
//    private fun changeSequenceExercise(trainingId: Long, roundId: Long, idViewForm:Int, idViewTo: Int){
//        templateMy { dataRepository.changeSequenceExercise(trainingId, roundId, idViewForm, idViewTo) } }
//    private fun setActivityToExercise(exerciseId: Long, activityId: Long) {
//        templateMy{dataRepository.setActivityToExercise(
//            trainingScreenState.value.training.idTraining, exerciseId, activityId)} }
//    private fun onSetColorActivity(activityId: Long, color: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { dataRepository.onSetColorActivity( activityId, color) }
//                .fold( onSuccess = { }, onFailure = { messageApp.errorApi(it.message ?: "") })
//        }
//    }
//    private fun templateMy( funDataRepository:() -> Training){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { funDataRepository() }.fold(
//                onSuccess = { _trainingScreenState.update { currentState ->
//                    currentState.copy(
//                        training = it,
//                        showBottomSheetSelectActivity = mutableStateOf(false),
//                        enteredName = mutableStateOf(it.name) ) } },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }
//    private fun templateNothing( funDataRepository:() -> Unit){
//        viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching { funDataRepository() }.fold(
//                onSuccess = {  },
//                onFailure = { messageApp.errorApi(it.message ?: "") }
//            )
//        }
//    }
//}