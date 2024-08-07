package com.example.myapplication.data.remote

import com.example.myapplication.models.home.AddToWatchListRequest
import com.example.myapplication.models.home.AddToWatchListResult
import com.example.myapplication.models.home.Movie
import com.example.myapplication.models.home.NowPlayingModel
import com.example.myapplication.models.home.TVModel
import com.example.myapplication.models.home.TrendingTVShowsForDay
import com.example.myapplication.models.home.WatchListTV
import com.farzin.imdb.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiInterface {


    @GET("trending/tv/day")
    suspend fun getTVShowsForDay(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
    ): Response<TrendingTVShowsForDay>


    @GET("tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("page") page: Int = 1,
    ): Response<TVModel>


    @GET("trending/movie/week")
    suspend fun getTrendingMoviesForWeek(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("page") page: Int = 1,
    ): Response<Movie>


    @GET("discover/tv")
    suspend fun getTVBasedOnNetwork(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("with_networks") withNetworks: Int,
        @Query("page") page: Int = 1,
    ): Response<TVModel>


    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("page") page: Int = 1,
    ): Response<NowPlayingModel>


    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchList(
        @Body watchListRequest: AddToWatchListRequest,
        @Path(
            value = "account_id",
            encoded = true
        ) accountId: Int = Constants.ACC_ID.toInt(),
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("session_id") sessionId: String = Constants.SESSION_ID,
        @Query("language") language: String = Constants.USER_LANG,
    ): Response<AddToWatchListResult>


    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchListTV(
        @Path(
            value = "account_id",
            encoded = true
        ) accountId: Int = Constants.ACC_ID.toInt(),
        @Query("sort_by") sortBy: String = Constants.CREATED_AT_DESC,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("session_id") sessionId: String = Constants.SESSION_ID,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("page") page: Int = 1,
    ): Response<WatchListTV>


    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchListMovie(
        @Path(
            value = "account_id",
            encoded = true
        ) accountId: Int = Constants.ACC_ID.toInt(),
        @Query("sort_by") sortBy: String = Constants.CREATED_AT_DESC,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("session_id") sessionId: String = Constants.SESSION_ID,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("page") page: Int = 1,
    ): Response<Movie>



    @GET("discover/movie")
    suspend fun getMoviesBasedOnGenre(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.USER_LANG,
        @Query("with_genres") genres: String,
        @Query("page") page: Int = 1,
    ): Response<Movie>


}