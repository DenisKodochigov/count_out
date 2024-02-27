package com.example.count_out.ui.view_components.drag_drop.example

import kotlin.random.Random

data class Item (
    val id: Int = internalId++,
    val name: String = "",
    val description: String = "",
    val color: Long = Random(id).nextLong()
){
    companion object {
        private var internalId = 0  // <-- OK to use in examples, never in production!
    }
}
