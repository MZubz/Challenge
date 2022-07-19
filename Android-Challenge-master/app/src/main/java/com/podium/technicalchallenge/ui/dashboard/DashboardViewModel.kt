package com.podium.technicalchallenge.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.domain.DashboardContentModel
import com.podium.technicalchallenge.network.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    sealed class ViewState {
        object Loading : ViewState()
        class Ready(val dashboardContentModel: DashboardContentModel) : ViewState()
        object Error : ViewState()
    }

    private val _content = MutableLiveData<ViewState>()
    val content: LiveData<ViewState> = _content

    init {
        getMovies()
    }

    private fun getMovies() {
        _content.value = ViewState.Loading
        viewModelScope.launch {
            _content.value =
                try {
                    dashboardRepository.getMovies()?.let {
                        ViewState.Ready(it)
                    } ?: ViewState.Error
                } catch (ex: Exception) {
                    ViewState.Error
                }
        }
    }
}
