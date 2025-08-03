package com.count_out.data.repository

import com.count_out.data.source.room.SpeechSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SpeechRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SpeechRepoImpl @Inject constructor(
    private val source: SpeechSource): SpeechRepo, PrimeRepo()
{
    override fun get(speech: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(speech)).convertor()

    override fun copy(speech: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.copy(toTypeSource(speech))
            .nextAction{ it-> source.get(it)}
    }

    override fun update(speech: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return source.update(toTypeSource(speech))
            .nextAction{ it-> source.get(it)}
    }
}


//override fun get(speech: Speech): Flow<ResultUC<TypeRepo>> =
//    source.get(SpeechImplD(speech).idSpeech).map{ converter.execute(it) }
//override fun get(speech: TypeRepo): Flow<ResultUC<TypeRepo>> =
//    source.get(convertorType(speech)).convertor()
//
//override fun copy(speech: TypeRepo): Flow<ResultUC<TypeRepo>> =
//    source.copy(convertorType(speech)).convertor()
//
//override fun update(speech: TypeRepo): Flow<ResultUC<TypeRepo>> =
//    source.update(convertorType(speech)).convertor()