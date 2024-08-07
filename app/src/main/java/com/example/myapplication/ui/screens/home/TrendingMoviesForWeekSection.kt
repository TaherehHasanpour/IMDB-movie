package com.example.myapplication.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.farzin.imdb.R
import com.example.myapplication.models.home.AddToWatchListRequest
import com.farzin.imdb.navigation.Screens
import com.example.myapplication.ui.theme.sectionContainerBackground
import com.farzin.imdb.utils.My3DotsLoading
import com.farzin.imdb.utils.MySpacerHeight
import com.farzin.imdb.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrendingMoviesForWeekSection(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {


    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current


    val trendingMoviesList = homeViewModel.trendingMoviesForWeek.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        MySpacerHeight(height = 20.dp)

        SectionTitleMaker(title = stringResource(R.string.movies))

        MySpacerHeight(height = 16.dp)



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.sectionContainerBackground
            ),
            shape = MaterialTheme.shapes.extraSmall
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {

                SectionStickyHeader(stringResource(R.string.movies_of_the_week))

                MySpacerHeight(height = 8.dp)

                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        count = trendingMoviesList.itemCount,
                        key = trendingMoviesList.itemKey { it.id },
                        contentType = trendingMoviesList.itemContentType { "trendingMoviesList" }
                    ) {

                        val list = trendingMoviesList[it]
                        MovieItem(
                            posterPath = list?.poster_path ?: "",
                            voteAverage = list?.vote_average ?: 0.0,
                            name = list?.title ?: "",
                            releaseDate = list?.release_date ?: "",
                            onCardClicked = {
                                navController.navigate(
                                    Screens.MovieDetails.route + "?id=${list?.id}"
                                )
                            },
                            onAddButtonClicked = {
                                homeViewModel.addToWatchList(
                                    AddToWatchListRequest(
                                        media_id = list?.id ?: 0,
                                        media_type = "movie",
                                        watchlist = true
                                    )
                                )
                                scope.launch {
                                    delay(200)
                                    homeViewModel.getWatchListMovie()
                                    Toast.makeText(
                                        ctx, ctx.getString(R.string.added_to_watchList),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }

                    trendingMoviesList.loadState.apply {
                        when {
                            refresh is LoadState.Loading -> {
                                item {
                                    My3DotsLoading(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                }
                            }

                            append is LoadState.Loading -> {
                                item {
                                    My3DotsLoading(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(100.dp)
                                    )

                                }
                            }
                        }
                    }
                }

                MySpacerHeight(height = 20.dp)
            }


        }


    }

}