package com.example.count_out.ui.screens.settings

import com.example.count_out.domain.use_case.UseCase
import com.example.count_out.ui.screens.prime.PrimeConvertor
import javax.inject.Inject

class SettingsConvertor @Inject constructor():
    PrimeConvertor<UseCase.Response, SettingsState>() {
    override fun convertSuccess(data: UseCase.Response): SettingsState {
        TODO("Not yet implemented")
    }
}