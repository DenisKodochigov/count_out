package com.count_out.presentation.models

import androidx.compose.runtime.Composable
import com.count_out.domain.entity.enums.TypeBS
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.presentation.view_element.bottom_sheet.ActivityBS
import com.count_out.presentation.view_element.bottom_sheet.ChangeOrderShowBS
import com.count_out.presentation.view_element.bottom_sheet.SpeechBS

class LauncherBSp(): LauncherBS<LauncherBSp>() {
    override fun element(element: List<Domain>) = apply { this.elements = element }
    override fun type(typeBS: TypeBS?) = apply { this.typeBS = typeBS }
    override fun owner(owner: Domain?) = apply { this.owner = owner }
    @Composable fun execute(dataState: BottomSheetInterface) {
        if (elements.isNotEmpty()){
            typeBS?.let{
                when (it){
                    TypeBS.Activity -> { ActivityBS(dataState, elements)}
                    TypeBS.Speech -> { SpeechBS(dataState,elements)}
                    TypeBS.Order -> { ChangeOrderShowBS(dataState,elements)}
                    TypeBS.Device -> { ChangeOrderShowBS(dataState,elements)}
                }
            }
        }
    }
}