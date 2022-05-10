package com.kotlinmovie.materialdesign.ui.recyclerView

import androidx.recyclerview.widget.RecyclerView

interface OnClickItemListener {
    fun onItemClick(data: RecyclerData)
}

interface OnStartDragListener {
    fun OnStartDrag(view: RecyclerView.ViewHolder)
}
