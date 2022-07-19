package com.podium.technicalchallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.databinding.FragmentDashboardBinding
import com.podium.technicalchallenge.domain.DashboardContentModel
import com.podium.technicalchallenge.domain.MovieCellModel
import com.podium.technicalchallenge.ui.dashboard.DashboardAdapter.Companion.HEADER_VIEW_TYPE
import com.podium.technicalchallenge.ui.dashboard.DashboardAdapter.Companion.MOVIE_VIEW_TYPE
import com.podium.technicalchallenge.ui.details.MovieDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment(), DashboardAdapter.OnMovieClickedListener {
    private val viewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val dashboardAdapter = DashboardAdapter(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.content.observe(viewLifecycleOwner) {
            when(it) {
                DashboardViewModel.ViewState.Loading -> binding.progress.visibility = View.VISIBLE
                is DashboardViewModel.ViewState.Ready -> showContent(it.dashboardContentModel)
                DashboardViewModel.ViewState.Error -> Snackbar.make(requireView(), "Error", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showContent(dashboardContentModel: DashboardContentModel) {
        with(binding) {
            progress.visibility = View.GONE
            val gridLayoutManager = GridLayoutManager(requireContext(), 4)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when(dashboardAdapter.getItemViewType(position)) {
                        HEADER_VIEW_TYPE -> 4
                        MOVIE_VIEW_TYPE -> 2
                        else -> 1
                    }
                }
            }
            recyclerView.apply {
                visibility = View.VISIBLE
                layoutManager = gridLayoutManager
                adapter = dashboardAdapter
            }
            dashboardAdapter.submit(dashboardContentModel.cells)
        }
    }

    override fun onMovieClicked(model: MovieCellModel) {
        findNavController().navigate(R.id.navigation_detail, bundleOf(MovieDetailFragment.DETAILS_KEY to model))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

