package com.count_out.app

import android.app.Application
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.use_case.other.CountOutServiceBindUC
import com.count_out.domain.use_case.other.CountOutServiceUnBindUC
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltAndroidApp
class CountOutApp: Application() {
    companion object App{}
}