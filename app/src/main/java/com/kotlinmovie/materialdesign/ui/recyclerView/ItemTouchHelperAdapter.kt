package com.kotlinmovie.materialdesign.ui.recyclerView

import java.text.FieldPosition

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position:Int)
}
interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}