package com.count_out.service.aii.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.count_out.framework.retrofit.source.UploadSourceImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val source: UploadSourceImpl
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val uploaded = source.uploadNextBatch()

        return if (uploaded) Result.success()
        else Result.retry()
    }
}