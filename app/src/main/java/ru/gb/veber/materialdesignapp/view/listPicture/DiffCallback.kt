package ru.gb.veber.materialdesignapp.view.listPicture

import androidx.recyclerview.widget.DiffUtil
import ru.gb.veber.materialdesignapp.model.PictureDTO

class DiffCallback(
    private val oldItems: List<Triple<PictureDTO, Boolean, Int>>,
    private val newItems: List<Triple<PictureDTO, Boolean, Int>>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.title == newItems[newItemPosition].first.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.mediaType == newItems[newItemPosition].first.mediaType &&
                oldItems[oldItemPosition].first.explanation == newItems[newItemPosition].first.explanation
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return Change(old, new)
    }
}

data class Change<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()
    return Change(firstChange.oldData, lastChange.newData)
}

