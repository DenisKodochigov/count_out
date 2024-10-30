package com.example.count_out.ui.view_components.drag_drop_column.column

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import javax.inject.Singleton


@Singleton
data class StateDDColumn( var sizeList: Int = 0, ) {
    private val mapOffset: MutableMap<Int, Int> = mutableMapOf()
    private val offsetY: MutableState<Float> = mutableFloatStateOf(0f)
    val heightItem: MutableState<Int> = mutableIntStateOf(0)
    val heightList: MutableState<Int> = mutableIntStateOf(0)

    private var indexDD: Int = 0
    private var indexMoved: Int = 0
    private var offsetStart: Int = 0

    fun onStartDrag(offset: Float) {
        initMap()
        offsetStart = offset.toInt()
        offsetY.value = offset
        indexDD = (offset / heightItem.value).toInt()
        indexMoved = indexDD
    }
    fun onDrag(delta: Float){
        offsetY.value += delta
        if (offsetY.value in 0f..(heightList.value + heightItem.value/2f)) {
            if (offsetY.value > heightItem.value * (indexMoved + 1.5)) {
                if (indexMoved + 1 <= sizeList - 1) {
                    indexMoved += 1
                    mapOffset[indexMoved] = (mapOffset[indexMoved] ?: 0) - heightItem.value
                }
            } else if (offsetY.value < heightItem.value * (indexMoved - 0.5)){
                if (indexMoved - 1 >= 0) {
                    indexMoved -= 1
                    mapOffset[indexMoved] = (mapOffset[indexMoved] ?: 0) + heightItem.value
                }
            }
        }
    }
    fun onStopDrag( onMoveItem: (Int, Int) -> Unit){
        offsetY.value =0f
        offsetStart = 0
        initMap()
        onMoveItem(indexDD,indexMoved)
    }
    fun itemZ(index: Int) = if (index == indexDD) 1f else 0f
    fun offsetIt(index: Int) = (mapOffset[index] ?: 0) + if (index == indexDD) { offsetY.value.toInt() - offsetStart } else 0
    private fun initMap() { for ( i in 0..< sizeList) mapOffset[i] = 0}
}
