package com.count_out.presentation.models

import androidx.compose.runtime.Composable
import com.count_out.domain.entity.enums.TypeBS
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.presentation.view_element.bottom_sheet.ActivityBS
import com.count_out.presentation.view_element.bottom_sheet.ChangeOrderShowBS
import com.count_out.presentation.view_element.bottom_sheet.DeviceBleBS
import com.count_out.presentation.view_element.bottom_sheet.SavePlanBS
import com.count_out.presentation.view_element.bottom_sheet.SpeechBS

class LauncherBSp(): LauncherBS<LauncherBSp>() {
    override fun list(list: List<Domain>) = apply { this.list = list }
    override fun type(typeBS: TypeBS?) = apply { this.typeBS = typeBS }
    override fun owner(owner: Domain?) = apply { this.owner = owner }
    override fun init(typeBS: TypeBS?, owner: Domain?, list: List<Domain>) = apply {
        this.typeBS = typeBS
        this.owner = owner
        this.list = list
    }
    @Composable fun execute(dataState: BottomSheetInterface) {
        owner?.let{ own->
            typeBS?.let{
                when (it){
                    TypeBS.Activity -> { ActivityBS( dataState, own )}
                    TypeBS.Speech -> { SpeechBS(dataState,own )}
                    TypeBS.Order -> { ChangeOrderShowBS(dataState,own, list)}
                    TypeBS.Device -> { DeviceBleBS(dataState,own,)}
                    TypeBS.SavePlan -> { SavePlanBS(dataState,own,)}
                }
            }
        }
    }
}