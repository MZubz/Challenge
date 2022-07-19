package com.podium.technicalchallenge.ui.dashboard

import androidx.recyclerview.widget.DiffUtil
import com.podium.technicalchallenge.domain.HeaderCellModel
import com.podium.technicalchallenge.domain.MovieCellModel

object DashboardDiffCallback: DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is HeaderCellModel && newItem is HeaderCellModel)
            oldItem.titleRes == newItem.titleRes
        else if(oldItem is MovieCellModel && newItem is MovieCellModel)
            oldItem.id == newItem.id
        else
            false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is HeaderCellModel && newItem is HeaderCellModel)
            oldItem == newItem
        else if(oldItem is MovieCellModel && newItem is MovieCellModel)
            oldItem == newItem
        else
            return false
    }
}