package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.WeatherRepo
import com.count_out.domain.repository.plans.SpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherCore @Inject constructor(private val repo: WeatherRepo): Core()  {
    fun get(weatherRequest: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.get(weatherRequest) }
}