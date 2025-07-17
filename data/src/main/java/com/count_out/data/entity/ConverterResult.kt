package com.count_out.data.entity

import com.count_out.data.models.throwable.ResultSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC

class ConverterResult {
    fun <T: Any>execute(resultDS: ResultSource<T> ): ResultUC<T> {
        return when(resultDS){
            is ResultSource.Success-> {ResultUC.Success(resultDS.data)}
            is ResultSource.Error -> { ResultUC.Error(ThrowableUC.DataSourceTrow(resultDS.throwable)) }
        }
    }
}