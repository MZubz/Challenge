package com.podium.technicalchallenge.domain

import com.podium.technicalchallenge.DashboardQuery
import com.podium.technicalchallenge.R

data class DashboardContentModel(val cells: List<Any>)

fun DashboardQuery.Data.toModel(): DashboardContentModel {
    val cells = mutableListOf<Any>()
    cells.add(
        HeaderCellModel(R.string.browse_by_genre)
    )
    cells.addAll(genres)
    cells.add(
        HeaderCellModel(R.string.browse_by_all)
    )
    movies?.map { movie ->
        movie?.let {
            cells.add(it.toModel())
        }
    }
    val topFive = movies?.sortedBy { it?.popularity }?.take(5)
    topFive?.map { movie ->
        movie?.let {
            cells.add(0, it.toModel())
        }
    }
    cells.add(0,  HeaderCellModel(R.string.top_5))
    return DashboardContentModel(cells)
}