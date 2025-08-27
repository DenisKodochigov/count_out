package com.count_out.data.repository

import com.count_out.data.source.services.CountOutServiceSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.CountOutServiceRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceRepoImpl @Inject constructor(
    private val source: CountOutServiceSource
): CountOutServiceRepo, PrimeRepo()  {
    override fun bind(): Flow<ResultUC<TypeRepo>> = source.bind().convertor()
    override fun unbind(): Flow<ResultUC<TypeRepo>> = source.unbind().convertor()
}