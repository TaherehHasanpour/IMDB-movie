package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.EpisodeGuideApiInterface
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.episode_guide.SeasonDetailModel
import javax.inject.Inject

class EpisodeGuideRepo @Inject constructor(private val api: EpisodeGuideApiInterface) : BaseApiResponse() {


    suspend fun getSeasonDetails(seriesId:Int,seasonNumber:Int) : NetworkResult<SeasonDetailModel> =
        safeApiCall {
            api.getSeasonDetails(seriesId, seasonNumber)
        }





}