package com.count_out.framework.room.source

import com.count_out.data.models.NameIdDb
import com.count_out.data.models.PartDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.asType
import com.count_out.data.models.throwable.ResultSource.Companion.flatMap
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.PlanSource
import com.count_out.framework.result
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.plan.PlanDao
import com.count_out.framework.room.db.plan.PlanTb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlanSourceImpl @Inject constructor(
    private val dao: PlanDao,
    private val partSource: PartSource,
    private val speechSource: SpeechSourceImpl,
): PlanSource, PrimeSource() {

    override fun gets(): Flow<ResultSource<TypeSource>>{
        return try {
            dao.getPlans().filterNotNull().map { list ->
                if (list.isEmpty()) ResultSource.Error(ThrowableDS.RequestFailed())
                else ResultSource.Success(TypeSource.PlansT(list))
            }
        } catch(e: Exception) { flowOf(ResultSource.Error(ThrowableDS.extract(e))) }
    }

    override fun get(plan: TypeSource): Flow<ResultSource<TypeSource>> =
        plan.usePlanFlow{dao.getPlan( it.idPlan)}

    override fun getId(idPlan: TypeSource): Flow<ResultSource<TypeSource>> =
        idPlan.useLongFlow { id-> dao.getPlan(id).filterNotNull() }

    override fun copy(plan: TypeSource): ResultSource<TypeSource> =
        plan.useResult { planTb->
            dao.insert(planTb.copy(idPlan = 0L)).result().flatMap { ownerId->
                val listSpeech = speechSource.getListSpeech( planId = planTb.idPlan)
                    .map { it.apply { planId = ownerId.item } }
                if (speechSource.insert(listSpeech).count() == listSpeech.count())
                    ResultSource.Success(TypeSource.IntT(listSpeech.count()))
                else ResultSource.Error(ThrowableDS.RequestFailed())
            }
        }

    override fun del(plan: TypeSource): ResultSource<TypeSource> =
        plan.use { dao.delete( it).toLong() }

    override fun update(nameId: TypeSource): ResultSource<TypeSource> =
        nameId.useName { dao.updateName(it.name, it.id).toLong() }

    //##############################################################################################
    inline fun TypeSource.use(crossinline block: (PlanTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.PlanT) {
            try { block(this.item as PlanTb).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    inline fun TypeSource.useName(crossinline block: (NameIdDb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.NameIdT) {
            try { block(this.item).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())
    inline fun TypeSource.usePlanFlow(crossinline block: (PlanTb) -> Flow<PlanTb>): Flow<ResultSource<TypeSource>> =
        if (this is TypeSource.PlanT) {
            try { block(this.item as PlanTb).filterNotNull()
                .map{ plan-> ResultSource.Success(TypeSource.PlanT(plan)) }}
            catch (e: Exception) { flowOf(ResultSource.Error(ThrowableDS.extract(e))) }
        } else flowOf(ResultSource.Error(ThrowableDS.NotValidType()))

    inline fun TypeSource.useLongFlow(crossinline block: (Long) -> Flow<PlanTb>): Flow<ResultSource<TypeSource>> =
        if (this is TypeSource.LongT) {
            try { block(this.item)
                .map{ plan-> ResultSource.Success(TypeSource.PlanT(plan)) } }
            catch (e: Exception) { flowOf(ResultSource.Error(ThrowableDS.extract(e)))}
        } else flowOf(ResultSource.Error(ThrowableDS.NotValidType()))

    inline fun TypeSource.useResult(crossinline block: (PlanTb) -> ResultSource<TypeSource>): ResultSource<TypeSource> =
        if (this is TypeSource.PlanT) {
            try { block(this.item as PlanTb) }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    fun copyParts(parts: List<PartDb>, ownerId: TypeSource.LongT): ResultSource<TypeSource> =
        if (parts.isEmpty()) { Success(TypeSource.IntT(0)) }
        else {
            parts.map { pr-> partSource.copy(TypeSource.PartT(
                (pr as PartTb).apply{ this.planId = ownerId.item})) }
                .firstOrNull {it is ResultSource.Error}
                ?: Success(TypeSource.IntT(parts.size))
        }

}