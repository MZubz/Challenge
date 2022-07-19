package com.podium.technicalchallenge.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.podium.technicalchallenge.databinding.FragmentMovieDetailBinding
import com.podium.technicalchallenge.domain.MovieCellModel
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {
    companion object {
        const val DETAILS_KEY = "detail"
        private const val SEPARATOR = ", "
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<MovieCellModel>(DETAILS_KEY)?.let { model ->
            with(binding) {
                Picasso.get().load(model.posterUrl).into(poster)
                title.text = model.title
                desc.text = model.desc
                director.text = model.director
                cast.text = model.cast.joinToString(separator = SEPARATOR)
                genre.text = model.generes.joinToString(separator = SEPARATOR)
            }
        }
    }
}