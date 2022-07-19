package com.podium.technicalchallenge.domain

import android.os.Parcelable
import com.podium.technicalchallenge.DashboardQuery
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCellModel(
    val id: Int,
    val title: String,
    val rating: Double,
    val generes: List<String>,
    val cast: List<String>,
    val desc: String,
    val posterUrl: String?,
    val director: String
): Parcelable

fun DashboardQuery.Movie.toModel(): MovieCellModel {
    return MovieCellModel(
        id = id,
        title = title,
        rating = popularity,
        generes = genres,
        cast = cast.toList(),
        desc = overview,
        posterUrl = posterPath,
        director = director.name
    )
}

fun List<DashboardQuery.Cast>.toList(): List<String> {
    val list = mutableListOf<String>()
    map {
        list.add(it.name)
    }
    return list
}