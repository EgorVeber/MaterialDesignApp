package ru.gb.veber.materialdesignapp.view.listPicture


interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}

interface ScrollingToPositionListener {
    fun moveToPosition(position: Int)
}

