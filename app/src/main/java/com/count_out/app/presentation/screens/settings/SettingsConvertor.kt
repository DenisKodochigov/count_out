package com.count_out.app.presentation.screens.settings

import com.count_out.domain.use_case.UseCase
import com.count_out.app.presentation.screens.prime.PrimeConvertor
import javax.inject.Inject

class SettingsConvertor @Inject constructor():
    PrimeConvertor<UseCase.Response, SettingsState>() {
    override fun convertSuccess(data: UseCase.Response): SettingsState {
        TODO("Not yet implemented")
    }
}