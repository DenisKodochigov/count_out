package com.example.count_out.ui.view_components.drag_drop_column

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.ui.view_components.log
import javax.inject.Singleton

@Singleton
data class StateDragColumn(
    var maxOffsetUp: Float = 0f,
    var maxOffsetDown: Float = 0f,
    val indexItem: Int = 0,
    var offset: Float = 0f,
    val enableDrag: MutableState<Boolean> = mutableStateOf(false),
    var shift: Animatable<Float, AnimationVector1D> = Animatable(0f),
){
    fun onStartDrag() {
        log(true, "onStartDrag: ")
//        if (lazyState.layoutInfo.visibleItemsInfo.isNotEmpty()) {
//            offset = lazyState.layoutInfo.visibleItemsInfo[indexItem].offset.toFloat()
//            maxOffsetDown = lazyState.layoutInfo.visibleItemsInfo.last().offset.toFloat()
//        } else {
//            log(true, "visibleItemsInfo.isEmpty")
//        }
    }
    fun onStopDrag(onMoveItem: (Int) -> Unit){
        log(true, "onStartDrag: ")
//        lazyState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
//            (offset + shift.value).toInt() in item.offset..(item.offset + item.size)
//        }?.also { item ->
//            if (indexItem != item.index) onMoveItem(item.index)
//        }
    }
    suspend fun shiftItem( delta: Float){
        log(true, "shiftItem: $delta")
        if ((offset + shift.value + delta) in maxOffsetUp..maxOffsetDown) {
            shift.snapTo(shift.value + delta)
            shift.value.plus(delta)
        }
    }
    fun enableDrag( value: Boolean){
        log(true, "onLongClick: $value")
        enableDrag.value = value
    }
}
