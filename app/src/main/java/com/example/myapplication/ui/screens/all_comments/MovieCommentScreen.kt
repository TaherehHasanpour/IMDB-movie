package com.example.myapplication.ui.screens.all_comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.imdb.R
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.tvDetail.TVReviewModelResult
import com.farzin.imdb.ui.screens.tvdetails.MediaDetailTopBarSection
import com.farzin.imdb.viewmodel.MovieDetailViewModel

@Composable
fun MovieCommentScreen(
    mediaId: Int,
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
) {


    LaunchedEffect(true) {
        movieDetailViewModel.getMovieReviews(mediaId)
    }

    var loading by remember { mutableStateOf(false) }
    var reviewList by remember { mutableStateOf<List<TVReviewModelResult>>(emptyList()) }
    var totalResult by remember { mutableIntStateOf(0) }


    val result by movieDetailViewModel.movieReviews.collectAsState()
    when (result) {
        is NetworkResult.Success -> {
            loading = false
            reviewList = result.data?.results ?: emptyList()
            totalResult = result.data?.total_results ?: 0


        }

        is NetworkResult.Error -> {
            loading = false
        }

        is NetworkResult.Loading -> {
            loading = true
        }
    }

    if (!loading) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {


            LazyColumn() {

                item {
                    MediaDetailTopBarSection(name = stringResource(R.string.user_review),
                        onClick = { navController.popBackStack() }
                    )
                }

                item {
                    CommentResultSection(totalResult)
                }

                items(reviewList) { comment ->
                    CommentItem(comment)
                }
            }

        }
    }
}