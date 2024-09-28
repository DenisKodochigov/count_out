package com.example.count_out.service_app

import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateService

class TrainingState {
    var stateService: StateService = StateService.DECLARED
    var error: ErrorBleService = ErrorBleService.NONE
}
