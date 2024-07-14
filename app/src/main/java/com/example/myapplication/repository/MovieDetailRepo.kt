package com.example.myapplication.repository

import com.example.myapplication.data.remote.BaseApiResponse
import com.example.myapplication.data.remote.MovieDetailApiInterface
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.home.Movie
import com.example.myapplication.models.movieDetail.MovieCastAndCrewModel
import com.example.myapplication.models.movieDetail.MovieDetailModel
import com.example.myapplication.models.movieDetail.RatedMovieModel
import com.example.myapplication.models.movieDetail.VideosModel
import com.example.myapplication.models.tvDetail.AddRating
import com.example.myapplication.models.tvDetail.AddRatingModel
import com.example.myapplication.models.tvDetail.ImagesModel
import com.example.myapplication.models.tvDetail.TVReviewModel
import javax.inject.Inject

class MovieDetailRepo @Inject constructor(private val api: MovieDetailApiInterface) : BaseApiResponse() {


    suspend fun getMovieDetails(movieId:Int) : NetworkResult<MovieDetailModel> = safeApiCall {
        api.getMovieDetails(movieId)
    }
    suspend fun getRatedMovie() : NetworkResult<RatedMovieModel> = safeApiCall {
        api.getRatedMovie()
    }

    suspend fun addRating(rating: AddRating, movieId: Int) : NetworkResult<AddRatingModel> = safeApiCall {
        api.addRating(rating = rating, movieId = movieId)
    }

    suspend fun getMovieCastAndCrew(movieId: Int) : NetworkResult<MovieCastAndCrewModel> = safeApiCall {
        api.getMovieCastAndCrew(movieId = movieId)
    }

    suspend fun getMovieRecommendation(movieId: Int) : NetworkResult<Movie> = safeApiCall {
        api.getMovieRecommendation(movieId = movieId)
    }


    suspend fun getMovieReviews(movieId: Int) : NetworkResult<TVReviewModel> = safeApiCall {
        api.getMovieReviews(movieId = movieId)
    }

    suspend fun getImagesForMovie(movieId: Int) : NetworkResult<ImagesModel> = safeApiCall {
        api.getImagesForMovie(movieId = movieId)
    }


    suspend fun getVideosForMovie(movieId: Int) : NetworkResult<VideosModel> = safeApiCall {
        api.getVideosForMovie(movieId = movieId)
    }


}