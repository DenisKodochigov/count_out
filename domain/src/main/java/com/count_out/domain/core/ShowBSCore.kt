package com.count_out.domain.core

//class ShowBSCore: Core()  {
//
//    fun get(request: Domain): Flow<ResultDomain<Domain>> {
//        return flowOf(ResultDomain.Success(execute(request))) }
//
//    fun execute(item: Domain): Domain{
//        return if (item is ShowBottomSheet) calculate(item)
//        else object:Domain{}
//    }
//    fun calculate(item: ShowBottomSheet): ShowBottomSheet{
//        return when(item.domain){
//            is Set -> { item.copy(set = !item.set)}
//            is Activity-> { item.copy(activity = !item.activity)}
//            is Exercise-> { item.copy(exercise = !item.exercise)}
//            is Ring -> { item.copy(ring = !item.ring) }
//            is Part -> { calculateRound(item) }
//            is Plan-> {item.copy(plan = !item.plan)}
//            is DeviceBle-> {item.copy(selectBleDevice = !item.selectBleDevice)}
//            else -> {item}
//        }
//    }
//    fun calculateRound( item: ShowBottomSheet ): ShowBottomSheet{
//        return when ((item.domain as Part).name){
//            PartName.WorkUp -> { item.copy(workUp = !item.workUp) }
//            PartName.WorkOut -> { item.copy(workOut = !item.workOut) }
//            PartName.WorkDown -> { item.copy(workDown = !item.workDown) }
//        }
//    }
//}