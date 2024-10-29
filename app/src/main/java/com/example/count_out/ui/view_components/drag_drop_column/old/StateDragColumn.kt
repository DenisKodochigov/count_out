package com.example.count_out.ui.view_components.drag_drop_column.old

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import com.example.count_out.domain.roundMy
import com.example.count_out.ui.view_components.lg
import javax.inject.Singleton
import kotlin.math.round

@Singleton
data class StateDragColumn(
    val heightList: MutableState<Int> = mutableIntStateOf(0),
    var sizeList: Int = 0,
) {
    private var offsetBegin: Float = 0f
    private val offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    private val offsetZ: MutableState<Float> = mutableFloatStateOf(0f)
    private var heightItem: Int = 0
    private var indexItemL: Int = 0

    fun onStartDrag( indexItem: Int) {
        indexItemL = indexItem
        if (heightList.value > 0 && sizeList > 0) {
            heightItem = round((heightList.value / sizeList).toDouble()).toInt()
            offsetBegin = (indexItem * heightItem).toFloat()
            offsetZ.value = 1.0f
        }
    }
    fun onStopDrag( indexItem: Int, onMoveItem: (Int, Int) -> Unit){
        val indexTo = indexItem + roundMy(offsetY.value / heightItem)
        if (indexTo != indexItem) onMoveItem( indexItem, indexTo )
        offsetY.value =0f
        offsetZ.value = 0.0f
    }

    fun onDrag(delta: Float){
        if ((offsetBegin + offsetY.value + delta) in 0f..(heightList.value - heightItem/2).toFloat()) {
            offsetY.value += delta
        }
    }

    fun itemOffset() = offsetY.value.toInt()
    fun itemZ() = offsetZ.value

    //#################################################################

    fun onDrag(delta: Float, onMoveItem: (Int, Int) -> Unit){
        if ((offsetBegin + offsetY.value + delta) in 0f..(heightList.value - heightItem).toFloat()) {
//            lg("offsetBegin + offsetY.value + delta = ${offsetBegin + offsetY.value + delta}, heightList=${heightList.value}, heightItem=$heightItem")
            lg("offsetY.value + delta = ${offsetY.value + delta}, heightItem=$heightItem")
            offsetY.value += delta
            if (offsetY.value > heightItem) {
//                onMoveItem(indexItemL, indexItemL + 1 )
//                indexItemL ++
//                offsetY.value = 0f
//                offsetBegin = (indexItemL * heightItem).toFloat()
//                lg(" move ${indexItemL-1} to $indexItemL  offsetBegin:$offsetBegin")
            } else if (offsetY.value < (-1) * heightItem){
//                onMoveItem(indexItemL, indexItemL - 1 )
                indexItemL --
                offsetY.value = 0f
                offsetBegin = (indexItemL * heightItem).toFloat()
//                lg(" move ${indexItemL + 1} to $indexItemL  offsetBegin:$offsetBegin")
            }
        }
    }
    fun onStopDrag( ){
        offsetY.value =0f
        offsetZ.value = 0.0f
    }
}
