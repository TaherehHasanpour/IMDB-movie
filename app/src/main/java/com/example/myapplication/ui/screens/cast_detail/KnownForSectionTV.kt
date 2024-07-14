package com.example.myapplication.ui.screens.cast_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.myapplication.models.castDetail.Cast
import com.example.myapplication.models.home.AddToWatchListRequest
import com.farzin.imdb.navigation.Screens
import com.farzin.imdb.ui.screens.home.MovieItem
import com.farzin.imdb.ui.screens.home.SectionStickyHeader
import com.example.myapplication.ui.theme.sectionContainerBackground
import com.farzin.imdb.utils.MySpacerHeight
import com.farzin.imdb.viewmodel.CastDetailViewModel
import com.farzin.imdb.viewmodel.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun KnownForSectionTV(
    castDetailViewModel: CastDetailViewModel = hiltViewModel(),
    id: Int,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var knownForTVList by remember { mutableStateOf<List<Cast>>(emptyList()) }

    LaunchedEffect(id) {
        castDetailViewModel.getCastDetail(id)

        castDetailViewModel.castTVCredits.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {
                    loading = false
                    knownForTVList = result.data ?: emptyList()
                }

                is NetworkResult.Error -> {
                    loading = false
                }

                is NetworkResult.Loading -> {
                    loading = false
                }
            }
        }

    }





    if (knownForTVList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            MySpacerHeight(height = 24.dp)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp),
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

                    SectionStickyHeader(stringResource(R.string.tv))

                    MySpacerHeight(height = 8.dp)

                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(knownForTVList) { item ->
                            MovieItem(
                                posterPath = item.poster_path ?: "",
                                voteAverage = item.vote_average ?: 0.0,
                                name = item.name ?: "",
                                character = item.character ?: stringResource(R.string.not_specified),
                                onCardClicked = {
                                    navController.navigate(
                                        Screens.TVDetails.route + "?id=${item.id}"
                                    )
                                },
                                onAddButtonClicked = {
                                    homeViewModel.addToWatchList(
                                        AddToWatchListRequest(
                                            media_id = item.id ?: 0,
                                            media_type = item.media_type ?: "tv",
                                            watchlist = true
                                        )
                                    )

                                    scope.launch {
                                        delay(200)
                                        homeViewModel.getWatchListTV()
                                    }
                                }
                            )
                        }
                    }
                }


            }

        }
    }


}