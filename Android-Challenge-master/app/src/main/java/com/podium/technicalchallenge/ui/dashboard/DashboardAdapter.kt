package com.podium.technicalchallenge.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.podium.technicalchallenge.databinding.CellGenreBinding
import com.podium.technicalchallenge.databinding.CellHeaderBinding
import com.podium.technicalchallenge.databinding.CellMovieBinding
import com.podium.technicalchallenge.domain.HeaderCellModel
import com.podium.technicalchallenge.domain.MovieCellModel
import com.squareup.picasso.Picasso

class DashboardAdapter(private val listener: OnMovieClickedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val HEADER_VIEW_TYPE = 0
        const val MOVIE_VIEW_TYPE = 1
        const val GENRE_VIEW_TYPE = 2
    }

    interface OnMovieClickedListener {
        fun onMovieClicked(model: MovieCellModel)
    }

    private val diffUtil: AsyncListDiffer<Any> =
        AsyncListDiffer(this, DashboardDiffCallback)

    override fun getItemViewType(position: Int): Int {
        return when (diffUtil.currentList[position]) {
            is HeaderCellModel -> HEADER_VIEW_TYPE
            is MovieCellModel -> MOVIE_VIEW_TYPE
            is String -> GENRE_VIEW_TYPE
            else -> throw IllegalStateException("unsupported type for adapter")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_VIEW_TYPE -> HeaderViewHolder(CellHeaderBinding.inflate(inflater, parent, false))
            MOVIE_VIEW_TYPE -> MovieViewHolder(CellMovieBinding.inflate(inflater, parent, false), listener)
            GENRE_VIEW_TYPE -> GenreViewHolder(CellGenreBinding.inflate(inflater, parent, false))
            else -> throw IllegalStateException("unsupported type for adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = diffUtil.currentList[position]
        when (model) {
            is HeaderCellModel -> (holder as? HeaderViewHolder)?.bind(model)
            is MovieCellModel -> (holder as? MovieViewHolder)?.bind(model)
            is String -> (holder as? GenreViewHolder)?.bind(model)
        }
    }

    override fun getItemCount(): Int =
        diffUtil.currentList.size

    fun submit(cells: List<Any>) {
        diffUtil.submitList(cells)
    }

    class HeaderViewHolder(private val binding: CellHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: HeaderCellModel) {
            binding.headerTitle.text = itemView.context.getString(model.titleRes)
        }
    }

    class MovieViewHolder(
        private val binding: CellMovieBinding,
        private val listener: OnMovieClickedListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieCellModel) {
            with(binding) {
                Picasso.get().load(model.posterUrl).into(poster)
                title.text = model.title
                root.setOnClickListener {
                    listener.onMovieClicked(model)
                }
            }
        }
    }

    class GenreViewHolder(private val binding: CellGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genreString: String) {
            with(binding) {
                genre.text = genreString
            }
        }
    }
}

