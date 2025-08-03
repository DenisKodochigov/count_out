package com.count_out.framework.room.source

import android.database.sqlite.SQLiteConstraintException
import android.util.Log.e
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ActivitySource
import com.count_out.framework.room.db.activity.ActivityDao
import com.count_out.framework.room.db.activity.ActivityTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ActivitySourceImpl @Inject constructor(private val dao: ActivityDao): ActivitySource, PrimeSource() {

    override fun gets(): Flow<ResultSource<TypeSource>> =
        dao.gets().map { list->
            TypeSource.Activities(item = list.map { it.toActivity() })}.resultSource()

    override fun get(id: TypeSource): Flow<ResultSource<TypeSource>> {
        return if (id is TypeSource.LongT) {
            dao.get(id.item).map { TypeSource.ActivityT(it.toActivity()) }.resultSource()
        } else flow { emit (ResultSource.Error(ThrowableDS.NotValidType())) }
    }

    override fun copy(activity: TypeSource): ResultSource<TypeSource> {
        return try {
            if (activity is TypeSource.ActivityT) {
                dao.add(ActivityTable(activity.item)).let {
                    if (it > 0L) ResultSource.Success(TypeSource.LongT(item = it))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    override fun update(activity: TypeSource): ResultSource<TypeSource> {
        return try {
            if (activity is TypeSource.ActivityT) {
                dao.update(ActivityTable(activity.item)).let {
                    if (it > 0L) ResultSource.Success(TypeSource.IntT(item = it))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }

    override fun del(id: TypeSource): ResultSource<TypeSource>{
        return try {
            if (id is TypeSource.LongT) {
                if( dao.checkExerciseWithActivity(id.item) == null){
                    dao.del(id.item).let {
                        if (it > 0) ResultSource.Success(TypeSource.IntT(item = it))
                        else ResultSource.Error(ThrowableDS.RequestFailed())
                    }
                } else ResultSource.Error(ThrowableDS.RequestFailed())
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch(e: SQLiteConstraintException) { ResultSource.Error(ThrowableDS.extract(e))}
    }
}
