package com.count_out.data.entity

import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC

class ConverterResult {
    fun <T: Any>execute(resultDS: ResultDataSource<T> ): ResultUC<T> {
        return when(resultDS){
            is ResultDataSource.Success-> {ResultUC.Success(resultDS.data)}
            is ResultDataSource.Error -> { ResultUC.Error(ThrowableUC.DataSourceTrow(resultDS.throwable)) }
        }
    }
}