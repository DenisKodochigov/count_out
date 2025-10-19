package com.count_out.data.models

import com.count_out.data.models.entity.ActivityDb
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.PartDb
import com.count_out.data.models.entity.PlanDb
import com.count_out.data.models.entity.RingDb
import com.count_out.data.models.entity.SetDb
import com.count_out.data.models.entity.SettingsDb
import com.count_out.data.models.entity.SpeechDb
import com.count_out.data.models.entity.WeatherDb
import com.count_out.data.models.entity.WeatherRequestDb
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.weather.Weather
import com.count_out.domain.entity.weather.WeatherRequest
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.entity.workout.Part
import com.count_out.domain.entity.workout.Plan
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Speech

interface Data {
    fun toDomain(ind: Int = 0): Domain

    companion object{
        fun Data.toResultData(): ResultData<Data> = ResultData.Success(this)
        fun Domain.fromDomain(): Data =
            when (this) {
                is Speech -> SpeechDb.fromDomain(this)
                is Set -> SetDb.fromDomain(this)
                is Activity -> ActivityDb.fromDomain(this)
                is Exercise -> ExerciseDb.fromDomain(this)
                is Ring -> RingDb.fromDomain(this)
                is Part -> PartDb.fromDomain(this)
                is Plan -> PlanDb.fromDomain(this)
                is Weather -> WeatherDb.fromDomain(this)
                is WeatherRequest -> WeatherRequestDb.fromDomain(this)
                is LongDm -> LongDb.fromDomain(this)
                is Settings.NameBle -> SettingsDb.NameBle.fromDomain(this)
                is Settings.AddressBle -> SettingsDb.AddressBle.fromDomain(this)
                is Settings.SpeechDescription -> SettingsDb.SpeechDescription.fromDomain(this)
                else -> Data.EMPTY
            }
        val EMPTY = object: Data{
            override fun toDomain(ind: Int): Domain = Domain.EMPTY
        }
    }
}