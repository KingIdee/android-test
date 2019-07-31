package dev.idee.androidtest.utils

import androidx.recyclerview.widget.DiffUtil
import dev.idee.androidtest.TimeCardModel

class DiffUtilCallback : DiffUtil.ItemCallback<TimeCardModel>() {

    override fun areItemsTheSame(oldItem: TimeCardModel, newItem: TimeCardModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TimeCardModel, newItem: TimeCardModel): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.projectName == newItem.projectName
    }

}