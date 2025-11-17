package com.count_out.presentation.models

import androidx.compose.runtime.Composable
import com.count_out.domain.entity.enums.TypeBS
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.presentation.view_element.bottom_sheet.ActivityBS
import com.count_out.presentation.view_element.bottom_sheet.ChangeOrderBS
import com.count_out.presentation.view_element.bottom_sheet.DeviceBleBS
import com.count_out.presentation.view_element.bottom_sheet.SavePlanBS
import com.count_out.presentation.view_element.bottom_sheet.SpeechBS

class LauncherBSp(): LauncherBS<LauncherBSp>() {
    override fun init(typeBS: TypeBS?, type: Domain?, list: List<Domain>, idOwner:Long) = apply {
        this.typeBS = typeBS
        this.idOwner = idOwner
        this.type = type
        this.list = list
    }
    @Composable fun execute(dataState: BottomSheetInterface) {

        type?.let{ own->
            typeBS?.let{
                when (it){
                    TypeBS.Activity -> { ActivityBS( dataState, own )}
                    TypeBS.Speech -> { SpeechBS(dataState,own )}
                    TypeBS.Device -> { DeviceBleBS(dataState, own)}
                    TypeBS.SavePlan -> { SavePlanBS(dataState, own)}
                    TypeBS.Order -> { ChangeOrderBS(dataState, idOwner,own, list)}
                }
            }
        }
    }
}