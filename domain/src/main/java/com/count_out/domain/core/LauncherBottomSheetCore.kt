package com.count_out.domain.core

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.LauncherBS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LauncherBottomSheetCore: Core()  {

    fun get(request: Domain): Flow<ResultDomain<Domain>> {
        return flowOf(ResultDomain.Success(execute(request))) }

    fun execute(item: Domain): Domain {
        return item as? LauncherBS<*> ?: object:Domain{}
    }

}