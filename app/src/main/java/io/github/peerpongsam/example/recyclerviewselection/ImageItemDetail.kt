package io.github.peerpongsam.example.recyclerviewselection

import androidx.recyclerview.selection.ItemDetailsLookup

class ImageItemDetail(
    private val itemId: Long,
    private val adapterPosition: Int
) : ItemDetailsLookup.ItemDetails<Long>() {

    override fun getSelectionKey(): Long? {
        return itemId
    }

    override fun getPosition(): Int {
        return adapterPosition
    }
}
