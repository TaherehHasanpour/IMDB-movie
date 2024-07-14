package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.CastDetailApiInterface
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.castDetail.CastDetailModel
import com.example.myapplication.models.castDetail.CombinedCreditsModel
import javax.inject.Inject

class CastDetailRepo @Inject constructor(private val api: CastDetailApiInterface) : BaseApiResponse() {


    suspend fun getCastDetail(castId: Int): NetworkResult<CastDetailModel> =
        safeApiCall {
            api.getCastDetail(castId)
        }


    suspend fun getCombinedCredits(castId: Int): NetworkResult<CombinedCreditsModel> =
        safeApiCall {
            api.getCombinedCredits(castId)
        }


}