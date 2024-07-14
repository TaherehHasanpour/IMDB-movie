package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.HomeApiInterface
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.home.AddToWatchListRequest
import com.example.myapplication.models.home.AddToWatchListResult
import com.example.myapplication.models.home.Movie
import com.example.myapplication.models.home.NowPlayingModel
import com.example.myapplication.models.home.TVModel
import com.example.myapplication.models.home.TrendingTVShowsForDay
import com.example.myapplication.models.home.WatchListTV
import javax.inject.Inject

class HomeRepo @Inject constructor(private val api: HomeApiInterface) : BaseApiResponse() {

    suspend fun getTVShowsForDay(): NetworkResult<TrendingTVShowsForDay> =
        safeApiCall {
            api.getTVShowsForDay()
        }


    suspend fun getPopularTV(page: Int = 1): NetworkResult<TVModel> =
        safeApiCall {
            api.getPopularTV(page = page)
        }


    suspend fun getTrendingMoviesForWeek(page: Int = 1): NetworkResult<Movie> =
        safeApiCall {
            api.getTrendingMoviesForWeek(page = page)
        }


    suspend fun getTVBasedOnNetwork(netWorkId: Int, page: Int = 1): NetworkResult<TVModel> =
        safeApiCall {
            api.getTVBasedOnNetwork(withNetworks = netWorkId, page = page)
        }


    suspend fun getNowPlaying(page: Int = 1): NetworkResult<NowPlayingModel> =
        safeApiCall {
            api.getNowPlaying(page = page)
        }


    suspend fun addToWatchList(watchListRequest: AddToWatchListRequest): NetworkResult<AddToWatchListResult> =
        safeApiCall {
            api.addToWatchList(watchListRequest)
        }

    suspend fun getWatchListTV(page: Int = 1): NetworkResult<WatchListTV> =
        safeApiCall {
            api.getWatchListTV(page = page)
        }


    suspend fun getWatchListMovie(page: Int = 1): NetworkResult<Movie> =
        safeApiCall {
            api.getWatchListMovie(page = page)
        }


    suspend fun getMoviesBasedOnGenre(genre: String, page: Int = 1): NetworkResult<Movie> =
        safeApiCall {
            api.getMoviesBasedOnGenre(genres = genre, page = page)
        }

}