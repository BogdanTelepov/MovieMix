package ru.btelepov.moviemix.utils

import androidx.recyclerview.widget.DiffUtil
import ru.btelepov.moviemix.models.serials.SerialItem

class DiffUtilCallBack : DiffUtil.ItemCallback<SerialItem>() {

    override fun areItemsTheSame(oldItem: SerialItem, newItem: SerialItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SerialItem, newItem: SerialItem): Boolean {
        return oldItem.name == newItem.name
    }
}