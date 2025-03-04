package com.count_out.data.repository

import com.count_out.data.source.services.CountOutServiceSource
import com.count_out.domain.repository.CountOutServiceRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceRepoImpl @Inject constructor(private val source: CountOutServiceSource
): CountOutServiceRepo {
    override fun bind(): Flow<Boolean> = source.bind()
    override fun unbind(): Flow<Boolean> = source.unbind()
}