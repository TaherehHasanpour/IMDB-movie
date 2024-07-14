package com.example.myapplication.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.imdb.R
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.home.AddToWatchListRequest
import com.example.myapplication.models.home.WatchListTVResult
import com.farzin.imdb.navigation.Screens
import com.example.myapplication.ui.theme.sectionContainerBackground
import com.farzin.imdb.utils.Constants
import com.farzin.imdb.utils.MySpacerHeight
import com.farzin.imdb.viewmodel.DataStoreViewModel
import com.farzin.imdb.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchListTVSection(
    homeViewModel: HomeViewModel = hiltViewModel(),
    dataStoreViewModel: DataStoreViewModel = hiltViewModel(),
    navController: NavController,
) {

    val scope = rememberCoroutineScope()

    var watchListTVList by remember {
        mutableStateOf<List<WatchListTVResult>>(emptyList())
    }

    var loading by remember { mutableStateOf(false) }

    val result by homeViewModel.watchListTV.collectAsState()
    when (result) {
        is NetworkResult.Success -> {
            watchListTVList = result.data?.results ?: emptyList()
            loading = false
        }

        is NetworkResult.Error -> {
            loading = false
        }

        is NetworkResult.Loading -> {
            loading = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {

        MySpacerHeight(height = 20.dp)



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.sectionContainerBackground),
            shape = MaterialTheme.shapes.extraSmall
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {

                SectionStickyHeader(
                    headerTitle = stringResource(R.string.your_watch_list_tv),
                    isHaveAnotherText = true,
                    headerSubtitle = stringResource(R.string.refresh),
                    headerOnClick = {
                        scope.launch {
                            homeViewModel.getWatchListTV()
                        }
                    }
                )

                MySpacerHeight(height = 8.dp)




                    if (watchListTVList.isEmpty() && Constants.SESSION_ID.isNotEmpty()) {

                        EmptySection(
                            onClick = {
                                navController.navigate(Screens.Search.route)
                            },
                            title = stringResource(R.string.empty_watchlist_title),
                            subtitle = stringResource(R.string.empty_watchlist_subtitle),
                            buttonText = stringResource(R.string.empty_watchlist_button_text)
                        )

                    } else if (Constants.SESSION_ID == "" && watchListTVList.isEmpty()) {
                        EmptySection(
                            onClick = { navController.navigate(Screens.Profile.route) },
                            title = stringResource(R.string.please_login_to_see_watchlist),
                            subtitle = stringResource(R.string.empty_watchlist_subtitle),
                            isHaveButton = true,
                            buttonText = stringResource(R.string.login)
                        )
                    } else {

                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(watchListTVList) {
                                MovieItem(
                                    posterPath = it.poster_path ?: "",
                                    voteAverage = it.vote_average,
                                    name = it.name,
                                    releaseDate = it.first_air_date,
                                    onCardClicked = {
                                        navController.navigate(
                                            Screens.TVDetails.route + "?id=${it.id}"
                                        )
                                    },
                                    onAddButtonClicked = {
                                        homeViewModel.addToWatchList(
                                            AddToWatchListRequest(
                                                media_id = it.id,
                                                media_type = "tv",
                                                watchlist = false
                                            )
                                        )
                                        scope.launch {
                                            delay(200)
                                            homeViewModel.getWatchListTV()
                                        }
                                    },
                                    isFromWatchlist = true,
                                    modifier = Modifier.animateItemPlacement()
                                )
                            }
                        }

                    }
                }



                MySpacerHeight(height = 20.dp)

            }


        }

    }