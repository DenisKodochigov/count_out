package com.example.count_out.ui.view_components.drag_drop_column

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import javax.inject.Singleton
import kotlin.math.round

@Singleton
data class StateDragColumn(
    var heightList: MutableState<Int> = mutableIntStateOf(0),
    var sizeList: Int = 0,
) {
    private var offsetBegin: Float = 0f
    private var offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    private var heightItem: Int = 0

    fun onStartDrag( indexItem: Int) {
        if (heightList.value > 0 && sizeList > 0) {
            heightItem = round((heightList.value / sizeList).toDouble()).toInt()
            offsetBegin = (indexItem * heightItem).toFloat()
        }
    }
    fun onStopDrag( indexItem: Int, onMoveItem: (Int, Int) -> Unit){
        onMoveItem( indexItem, indexItem + roundMy(offsetY.value / heightItem) )
        offsetY.value =0f
    }
    private fun roundMy( value: Float): Int{
        return if (value > 0) {
            if (value - value.toInt() >= 0.5 ) { value.toInt() + 1 }
            else { value.toInt() }
        } else {
            if (value - value.toInt() >= -0.5 ) { value.toInt() }
            else { value.toInt() - 1 }
        }
    }
    fun shiftItem(delta: Float){
        if ((offsetBegin + offsetY.value + delta) in 0f..(heightList.value - heightItem/2).toFloat()) {
            offsetY.value += delta
        }
    }
    fun itemOffset(): Int{
        return offsetY.value.toInt()
    }
}
