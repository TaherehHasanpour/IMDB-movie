package com.example.myapplication.ui.screens.moviedetails

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.farzin.imdb.R
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.models.database.FavoriteDBModel
import com.example.myapplication.models.home.AddToWatchListRequest
import com.example.myapplication.models.tvDetail.Genre
import com.example.myapplication.models.tvDetail.ProductionCountry
import com.example.myapplication.models.tvDetail.SpokenLanguage
import com.farzin.imdb.ui.screens.cast_detail.ImageScreenState
import com.farzin.imdb.ui.screens.images_screen.ImageFullScreen
import com.farzin.imdb.ui.screens.tvdetails.MediaDetailAddToWatchListButton
import com.farzin.imdb.ui.screens.tvdetails.MediaDetailSection
import com.farzin.imdb.ui.screens.tvdetails.MediaDetailTitleSection
import com.farzin.imdb.ui.screens.tvdetails.MediaDetailTopBarSection
import com.farzin.imdb.ui.screens.tvdetails.MediaOverViewSection
import com.farzin.imdb.ui.screens.tvdetails.MediaPosterSection
import com.farzin.imdb.ui.screens.tvdetails.MediaVideoSection
import com.example.myapplication.ui.theme.appBackGround
import com.example.myapplication.ui.theme.imdbYellow
import com.farzin.imdb.utils.Constants
import com.farzin.imdb.utils.MyLoadingFullScreen
import com.farzin.imdb.viewmodel.HomeViewModel
import com.farzin.imdb.viewmodel.ImageScreenViewModel
import com.farzin.imdb.viewmodel.MovieDetailViewModel
import com.farzin.imdb.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MovieDetailsScreen(
    movieId: Int,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    imageScreenViewModel: ImageScreenViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var isInFavorite by remember { mutableStateOf(false) }
    LaunchedEffect(movieId) {
        scope.launch(Dispatchers.IO) {
            isInFavorite = profileViewModel.getFavoriteId(movieId) == movieId
        }
    }

    var isLoggedIn by remember { mutableStateOf(false) }
    isLoggedIn = Constants.SESSION_ID.isNotEmpty()

    var loading by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var picturePath by remember { mutableStateOf("") }
    var posterPath by remember { mutableStateOf("") }
    var overView by remember { mutableStateOf("") }
    var genres by remember { mutableStateOf<List<Genre>>(emptyList()) }
    var rating by remember { mutableDoubleStateOf(0.0) }
    var voteCount by remember { mutableIntStateOf(0) }
    var userRating by remember { mutableIntStateOf(0) }
    var runTime by remember { mutableIntStateOf(0) }
    var spokenLangList by remember { mutableStateOf<List<SpokenLanguage>>(emptyList()) }
    var productionCountry by remember { mutableStateOf<List<ProductionCountry>>(emptyList()) }
    var releaseDate by remember { mutableStateOf("") }
    var budget by remember { mutableIntStateOf(0) }
    var revenue by remember { mutableLongStateOf(0L) }


    var imageFullScreenPath by remember { mutableStateOf("") }


    //get media details
    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovieDetails(movieId)

        movieDetailViewModel.movieDetails.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {
                    loading = false
                    name = result.data?.title ?: ""
                    date = result.data?.release_date ?: ""
                    status = result.data?.status ?: ""
                    picturePath = result.data?.backdrop_path ?: ""
                    overView = result.data?.overview ?: ""
                    posterPath = result.data?.poster_path ?: ""
                    genres = result.data?.genres ?: emptyList()
                    rating = result.data?.vote_average ?: 0.0
                    voteCount = result.data?.vote_count ?: 0
                    runTime = result.data?.runtime ?: 0
                    spokenLangList = result.data?.spoken_languages ?: emptyList()
                    productionCountry = result.data?.production_countries ?: emptyList()
                    releaseDate = result.data?.release_date ?: ""
                    budget = result.data?.budget ?: 0
                    revenue = result.data?.revenue ?: 0

                }

                is NetworkResult.Error -> {
                    loading = false
                }

                is NetworkResult.Loading -> {
                    loading = true
                }
            }
        }

    }

    var isInWatchList by remember { mutableStateOf(false) }

    // get if the TV is in watchlist
    LaunchedEffect(movieId) {
        homeViewModel.getWatchListMovie()

        homeViewModel.watchListMovie.collectLatest { result ->
            when (result) {
                is NetworkResult.Success -> {
                    isInWatchList = result.data?.results?.any {
                        movieId == it.id
                    } ?: false
                }

                is NetworkResult.Error -> {}
                is NetworkResult.Loading -> {}
            }
        }

    }


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )


    if (loading){
        MyLoadingFullScreen(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp)
        )
    }else{
        when (imageScreenViewModel.imageScreenState) {
            ImageScreenState.IMAGE_LIST -> {
                ModalBottomSheetLayout(
                    sheetContent = {
                        MovieRatingBottomSheet(name, movieId)
                    },
                    sheetState = sheetState

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.appBackGround)
                        ) {

                            stickyHeader {
                                MediaDetailTopBarSection(
                                    name = name,
                                    onClick = {
                                        navController.popBackStack()
                                    },
                                    shouldHaveLikeButton = true,
                                    likeButtonOnClick = {


                                        if (isLoggedIn){
                                            if (isInFavorite){
                                                profileViewModel.removeFavorite(
                                                    FavoriteDBModel(
                                                        id = movieId,
                                                        image = posterPath,
                                                        name = name,
                                                        year = releaseDate,
                                                        rating = rating,
                                                        isMovie = true
                                                    )
                                                )

                                                isInFavorite = false

                                            }else{
                                                profileViewModel.addFavorite(
                                                    FavoriteDBModel(
                                                        id = movieId,
                                                        image = posterPath,
                                                        name = name,
                                                        year = releaseDate,
                                                        rating = rating,
                                                        isMovie = true
                                                    )
                                                )

                                                isInFavorite = true

                                            }
                                        }else{
                                            Toast.makeText(context,context.getString(R.string.please_login),
                                                Toast.LENGTH_SHORT)
                                                .show()
                                        }

                                    },
                                    isFavorite = isInFavorite
                                )
                            }


                            item {
                                MediaDetailTitleSection(
                                    name = name,
                                    date = date,
                                    status = status,
                                    isMovie = true,
                                    runTime = runTime
                                )
                            }
                            item {
                                MediaPosterSection(
                                    picturePath = picturePath,
                                    isMovie = true,
                                    name = name,
                                    startYear = date
                                )
                            }
                            item {

                                if (overView.isEmpty())
                                    overView = stringResource(R.string.no_overView)

                                MediaOverViewSection(
                                    genres = genres,
                                    overView = overView,
                                    posterPath = posterPath,
                                    mediaType = "movie"
                                )
                            }
                            item {
                                MediaDetailAddToWatchListButton(
                                    buttonBackGround = MaterialTheme.colorScheme.imdbYellow,
                                    buttonBorderColor = MaterialTheme.colorScheme.imdbYellow,
                                    onClick = {
                                        if (isInWatchList) {
                                            scope.launch {
                                                homeViewModel.addToWatchList(
                                                    AddToWatchListRequest(
                                                        media_type = "movie",
                                                        media_id = movieId,
                                                        watchlist = false
                                                    )
                                                )
                                                isInWatchList = false
                                            }
                                        } else {
                                            scope.launch {
                                                homeViewModel.addToWatchList(
                                                    AddToWatchListRequest(
                                                        media_type = "movie",
                                                        media_id = movieId,
                                                        watchlist = true
                                                    )
                                                )
                                                isInWatchList = true
                                            }
                                        }
                                    },
                                    isInWatchList = isInWatchList
                                )

                            }

                            item {
                                MovieRatingSection(
                                    rating = String.format("%.1f", rating),
                                    voteCount = voteCount,
                                    mediaId = movieId,
                                    onClick = {
                                        scope.launch {
                                            if (sheetState.isVisible)
                                                sheetState.hide() else sheetState.show()
                                        }
                                    },
                                    userRatingCallBack = {
                                        userRating = it
                                    }
                                )
                            }
                            item { MovieCastSection(mediaId = movieId, navController = navController) }
                            item {
                                MovieRecommendedSection(
                                    mediaId = movieId,
                                    navController = navController
                                )
                            }
                            item {
                                MovieImageSection(
                                    mediaId = movieId,
                                    navController = navController,
                                    onImageClickCallBack = { path ->
                                        imageFullScreenPath = path
                                    },
                                    onImageClick = {
                                        imageScreenViewModel.imageScreenState =
                                            ImageScreenState.IMAGE_FULLSCREEN
                                    }
                                )
                            }
                            item {
                                MediaVideoSection(
                                    mediaId = movieId,
                                    mediaType = "movie",
                                    poster = posterPath,
                                    navController = navController
                                )
                            }
                            item {
                                MovieCommentSection(
                                    mediaId = movieId,
                                    rating = String.format("%.1f", rating),
                                    userRating = userRating,
                                    navController = navController
                                )
                            }
                            item {
                                MediaDetailSection(
                                    spokenLangList = spokenLangList,
                                    productionCountry = productionCountry,
                                    releaseDate = releaseDate,
                                    budget = budget,
                                    revenue = revenue
                                )
                            }

                        }
                    }


                }
            }

            ImageScreenState.IMAGE_FULLSCREEN -> {
                ImageFullScreen(path = imageFullScreenPath)
            }
        }
    }


}