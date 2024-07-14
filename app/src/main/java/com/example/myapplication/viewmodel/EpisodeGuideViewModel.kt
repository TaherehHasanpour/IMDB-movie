package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.episode_guide.SeasonDetailModel
import com.farzin.imdb.repository.EpisodeGuideRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeGuideViewModel @Inject constructor(private val repo: EpisodeGuideRepo) : ViewModel() {

    val seasonDetails = MutableStateFlow<NetworkResult<SeasonDetailModel>>(NetworkResult.Loading())

    fun getSeasonDetails(seriesId: Int, seasonNumber: Int) {
        viewModelScope.launch {
            seasonDetails.emit(repo.getSeasonDetails(seriesId, seasonNumber))
        }
    }


}