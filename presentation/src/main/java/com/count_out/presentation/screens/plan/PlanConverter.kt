//package com.count_out.presentation.screens.plan
//
//import com.count_out.domain.entity.workout.Activities
//import com.count_out.domain.entity.workout.Collapsing
//import com.count_out.domain.entity.workout.LauncherBS
//import com.count_out.domain.entity.workout.Plan
//import com.count_out.domain.entity.workout.Selecting
//import com.count_out.domain.use_case.UseCase
//import com.count_out.domain.use_case.other.CollapsingUC
//import com.count_out.domain.use_case.other.LauncherBottomSheetUC
//import com.count_out.domain.use_case.plans.GetPlanUC
//import com.count_out.domain.use_case.plans.SelectingUC
//import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
//import com.count_out.presentation.models.LauncherBSp
//import com.count_out.presentation.screens.prime.PrimeConvertor
//import kotlinx.coroutines.flow.MutableStateFlow
//import javax.inject.Inject
//
//class PlanConverter @Inject constructor(): PrimeConvertor<UseCase.Response, PlanState>() {
//    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<PlanState>): PlanState {
//        return when(resultData){
//            is GetPlanUC.Response-> converterLocal(resultData, state)
//            is GetActivitiesUC.Response-> converterLocal(resultData, state)
//            is CollapsingUC.Response-> converterLocal(resultData, state)
//            is SelectingUC.Response-> converterLocal(resultData, state)
//            is LauncherBottomSheetUC.Response-> converterLocal(resultData, state)
//            else -> converterOther(state)
//        }
//    }
//    private fun converterLocal(data: LauncherBottomSheetUC.Response, state: MutableStateFlow<PlanState>): PlanState {
//        if (data.launcher is LauncherBS<*>) {
//            state.value = state.value.copy(launcherBS = data.launcher as LauncherBSp) }
//        return state.value
//    }
//    private fun converterLocal(data: GetPlanUC.Response, state: MutableStateFlow<PlanState>): PlanState {
//        if (data.plan is Plan) { state.value = state.value.copy(plan = data.plan as Plan) }
//        return state.value
//    }
//    private fun converterLocal(data: GetActivitiesUC.Response, state: MutableStateFlow<PlanState>): PlanState {
//        if (data.activity is Activities) {
//            state.value = state.value.copy(list = (data.activity as Activities).activities) }
//        return state.value
//    }
//
////    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<PlanState>): PlanState {
////        if (data.show is ShowBottomSheet)
////            state.value = state.value.copy(showBS = data.show as ShowBottomSheet)
////        return state.value
////    }
//    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<PlanState>): PlanState {
//        if (data.collaps is Collapsing)
//            state.value = state.value.copy( collapsing = data.collaps as Collapsing)
//        return state.value
//    }
//    private fun converterLocal(data: SelectingUC.Response, state: MutableStateFlow<PlanState>): PlanState {
//        if (data.item is Selecting) state.value = state.value.copy( selecting = data.item as Selecting)
//        return state.value
//    }
//    private fun converterOther( state: MutableStateFlow<PlanState>): PlanState {
//        return state.value
//    }
//}
//
////            is UpdateTrainingUC.Response-> state.value
////            is CopyExerciseUC.Response-> converterLocal(resultData, state)
////            is DeleteExerciseUC.Response-> converterLocal(resultData, state)
////            is ChangeSequenceExerciseUC.Response-> converterLocal(resultData, state)
////            is CopySetUC.Response-> converterLocal(resultData, state)
////            is DeleteSetUC.Response-> converterLocal(resultData, state)
////            is UpdateSetUC.Response-> converterLocal(resultData, state)
////
////    private fun converterLocal(data: CopyExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }
////    private fun converterLocal(data: DeleteExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }
////    private fun converterLocal(data: ChangeSequenceExerciseUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }
////    private fun converterLocal(data: CopySetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }
////    private fun converterLocal(data: DeleteSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }
////    private fun converterLocal(data: UpdateSetUC.Response, state: MutableStateFlow<TrainingState>): TrainingState {
////        return state.value
////    }