package com.count_out.app.presentation.screens.settings

import com.count_out.app.presentation.prime.PrimeConvertor
import com.count_out.domain.use_case.UseCase
import javax.inject.Inject

class SettingsConvertor @Inject constructor():
    PrimeConvertor< UseCase.Response, SettingsState>() {
    override fun convertSuccess(data: UseCase.Response): SettingsState {
        TODO("Not yet implemented")
    }
}