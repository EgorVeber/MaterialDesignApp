package ru.gb.veber.materialdesignapp.view.listPicture.recycler


interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelected()
    fun onItemClear()
}

interface RecyclerListener {
    fun moveToPosition(position: Int)
    fun clickImageListener(url: String)
}
