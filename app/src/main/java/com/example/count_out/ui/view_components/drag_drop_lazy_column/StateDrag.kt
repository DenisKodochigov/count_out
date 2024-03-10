package com.example.count_out.ui.view_components.drag_drop_lazy_column

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.lazy.LazyListState
import com.example.count_out.ui.view_components.log
import javax.inject.Singleton

@Singleton
data class StateDrag(
    var maxOffsetUp: Float = 0f,
    var maxOffsetDown: Float = 0f,
    val indexItem: Int = 0,
    var offset: Float = 0f,
    var shift: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val lazyState: LazyListState = LazyListState()
){
    fun onStartDrag() {
        if (lazyState.layoutInfo.visibleItemsInfo.isNotEmpty()) {
            offset = lazyState.layoutInfo.visibleItemsInfo[indexItem].offset.toFloat()
            maxOffsetDown = lazyState.layoutInfo.visibleItemsInfo.last().offset.toFloat()
        } else {
//            log(true, "visibleItemsInfo.isEmpty")
        }
    }
    fun onStopDrag(onMoveItem: (Int) -> Unit){
        lazyState.layoutInfo.visibleItemsInfo.firstOrNull { item ->
            (offset + shift.value).toInt() in item.offset..(item.offset + item.size)
        }?.also { item ->
            if (indexItem != item.index) onMoveItem(item.index)
        }
    }
    suspend fun shiftItem( delta: Float){
        if ((offset + shift.value + delta) in maxOffsetUp..maxOffsetDown) {
            shift.snapTo(shift.value + delta)
            shift.value.plus(delta)
        }
    }
}