package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.data.remote.SearchApiInterface
import com.example.myapplication.models.home.Movie
import com.example.myapplication.models.home.TrendingTVShowsForDay
import javax.inject.Inject

class SearchRepo @Inject constructor(private val api: SearchApiInterface) : BaseApiResponse() {


    suspend fun searchMovie(query: String,page:Int = 1) : NetworkResult<Movie> =
        safeApiCall {
            api.searchMovie(query = query, page = page)
        }



    suspend fun searchTV(query: String,page:Int = 1) : NetworkResult<TrendingTVShowsForDay> =
        safeApiCall {
            api.searchTV(query = query, page = page)
        }


}