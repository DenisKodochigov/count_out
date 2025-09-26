package com.count_out.framework.room.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.models.throwable.TypeSource.Companion.useLong
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ActivitySource
import com.count_out.framework.result
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivitySourceImpl @Inject constructor(private val dao: ActivityDao): ActivitySource, PrimeSource() {

    override fun gets(): Flow<ResultSource<TypeSource>> =
        dao.gets().map { list-> TypeSource.ActivitiesT(item = list)}.resultSource()

    override fun get(activity: TypeSource): Flow<ResultSource<TypeSource>> {
        return if (activity is TypeSource.ActivityT) {
            dao.get(activity.item.idActivity).map { TypeSource.ActivityT(it) }.resultSource()
        } else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }
    }

    override fun copy(activity: TypeSource): ResultSource<TypeSource> =
        activity.use { item-> dao.insert(item).toLong() }

    override fun update(activity: TypeSource): ResultSource<TypeSource> =
        activity.use { item-> dao.update(item).toLong() }

    override fun del(idActivity: TypeSource): ResultSource<TypeSource>{
        return idActivity.useLong { id->
            if( dao.checkExerciseWithActivity(id) == null) dao.del(id).toLong()
            else 0
        }
    }

    ///############################################################################################
    inline fun TypeSource.use(crossinline block: (ActivityTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.ActivityT) {
            try { block(this.item as ActivityTb).result()}
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

}
