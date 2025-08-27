package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.CountOutServiceRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceCore @Inject constructor(private val repo: CountOutServiceRepo): Core() {
    fun bind(): Flow<ResultUC<TypeRepo>>{
        return repo.bind() }
    fun unbind(): Flow<ResultUC<TypeRepo>>{
        return repo.unbind() }
}