package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.data.remote.TVDetailApiInterface
import com.example.myapplication.models.home.TrendingTVShowsForDay
import com.example.myapplication.models.movieDetail.VideosModel
import com.example.myapplication.models.tvDetail.AddRating
import com.example.myapplication.models.tvDetail.AddRatingModel
import com.example.myapplication.models.tvDetail.CastAndCrewModelTV
import com.example.myapplication.models.tvDetail.ImagesModel
import com.example.myapplication.models.tvDetail.RatedTVModel
import com.example.myapplication.models.tvDetail.TVDetailModel
import com.example.myapplication.models.tvDetail.TVReviewModel
import javax.inject.Inject

class TVDetailRepo @Inject constructor(private val api: TVDetailApiInterface) : BaseApiResponse() {


    suspend fun getTVDetails(seriesId:Int) : NetworkResult<TVDetailModel> =
        safeApiCall {
            api.getTVDetails(seriesId)
        }


    suspend fun getRatedTV() : NetworkResult<RatedTVModel> =
        safeApiCall {
            api.getRatedTV()
        }


    suspend fun addRating(seriesId:Int,rating: AddRating) : NetworkResult<AddRatingModel> =
        safeApiCall {
            api.addRating(seriesId = seriesId, rating = rating)
        }


    suspend fun getTVCastAndCrew(seriesId:Int) : NetworkResult<CastAndCrewModelTV> =
        safeApiCall {
            api.getTVCastAndCrew(seriesId)
        }


    suspend fun getRecommendedTVShows(seriesId:Int) : NetworkResult<TrendingTVShowsForDay> =
        safeApiCall {
            api.getRecommendedTVShows(seriesId)
        }

    suspend fun getImagesForTV(seriesId:Int) : NetworkResult<ImagesModel> =
        safeApiCall {
            api.getImagesForTV(seriesId)
        }

    suspend fun getReviewsForTV(seriesId:Int,page:Int) : NetworkResult<TVReviewModel> =
        safeApiCall {
            api.getReviewsForTV(seriesId, page = page)
        }

    suspend fun getVideosForTV(seriesId: Int) : NetworkResult<VideosModel> = safeApiCall {
        api.getVideosForTV(seriesId = seriesId)
    }


}