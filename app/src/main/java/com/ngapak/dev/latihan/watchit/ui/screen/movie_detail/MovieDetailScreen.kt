package com.ngapak.dev.latihan.watchit.ui.screen.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.latihan.watchit.R
import com.ngapak.dev.latihan.watchit.data.remote.model.MovieDetailResponse
import com.ngapak.dev.latihan.watchit.di.Injection
import com.ngapak.dev.latihan.watchit.factory.FavoriteViewModelFactory
import com.ngapak.dev.latihan.watchit.factory.ViewModelFactory
import com.ngapak.dev.latihan.watchit.ui.common.UiState
import com.ngapak.dev.latihan.watchit.ui.screen.favorite.FavoriteViewModel
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieDetailScreen(
    navigateBack: () -> Unit,
    movieId: Int,
    modifier: Modifier = Modifier,
    detailViewModel: MovieDetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideMoviesRepository(LocalContext.current)
        )
    ),
    favoriteViewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(Injection.provideFavoriteRepository(LocalContext.current))
    )
) {
    /*
        Apakah penggunaan launchedeffect ini sudah benar?
        Atau lebih baik menggunakan yg lain?
    */
    LaunchedEffect(Unit) {
        detailViewModel.getMovieDetail(movieId)
        favoriteViewModel.getFavorites()
    }

    var posterPath = ""
    var title = ""

    Box {
        detailViewModel.uiState.collectAsState().value.let { state ->
            when (state) {
                is UiState.Loading -> {
                    MovieDetailLoading()
                }

                is UiState.Success -> {
                    posterPath = state.data.posterPath ?: "no data"
                    title = state.data.title ?: "No title"

                    MovieDetailContent(
                        movieDetail = state.data,
                        modifier = modifier
                    )
                }

                is UiState.Error -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error getting movie data. ${state.errorMessage}",
                            modifier = modifier.clickable { detailViewModel.getMovieDetail(movieId) })
                    }
                }
            }
        }

        TopActionButton(
            viewModel = favoriteViewModel,
            navigateBack = { navigateBack() },
            movieId = movieId,
            posterPath = posterPath,
            title = title,
        )

    }
}

@Composable
fun TopActionButton(
    viewModel: FavoriteViewModel,
    navigateBack: () -> Unit,
    movieId: Int,
    posterPath: String,
    title: String,
    modifier: Modifier = Modifier,
) {
    var isFavorite by remember { mutableStateOf(false) }
    val moviesData by viewModel.moviesState.collectAsState()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = { navigateBack() },
            modifier = modifier
                .offset(x = 20.dp, y = 20.dp)
                .drawBehind {
                    drawCircle(
                        color = Color.Gray,
                        radius = 48f
                    )
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "go to previous page",
            )
        }
        if (moviesData != null) {
            for (movie in moviesData!!) {
                if (movie.id == movieId) {
                    isFavorite = true
                    break
                }
            }
            IconButton(
                onClick = {
                    isFavorite = if (isFavorite) {
                        viewModel.deleteFavorite(movieId, posterPath, title)
                        false
                    } else {
                        viewModel.addFavorite(movieId, posterPath, title)
                        true
                    }
                },
                modifier = modifier
                    .offset(x = (-20).dp, y = 20.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Gray,
                            radius = 48f
                        )
                    }
            ) {
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "go to previous page",
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "go to previous page",
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    movieDetail: MovieDetailResponse,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier.verticalScroll(
                state = rememberScrollState()
            ),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original${movieDetail.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Poster",
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.error_placeholder),
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .height(700.dp),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.padding(horizontal = 18.dp),
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = movieDetail.title ?: "Title not provided",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "2023 | 120 min", color = MaterialTheme.colorScheme.secondary)
                }
                Text(
                    "Language : ${
                        movieDetail.spokenLanguages.map { it?.englishName }.joinToString()
                    }"
                )
                Text(
                    "Countries : ${
                        movieDetail.productionCountries.map { it?.name }.joinToString()
                    }"
                )
                Row(modifier = modifier) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = modifier.size(18.dp),
                    )
                    Spacer(modifier = modifier.width(6.dp))
                    Text(
                        text = "${movieDetail.voteAverage} / 10 | ${movieDetail.voteCount} Total Vote",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Text(
                    "Genre : ${movieDetail.genres.map { it?.name }.joinToString()}"
                )
                Divider()
                Text(
                    text = "Prolog",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                    ),
                )
                Text(
                    text = "${movieDetail.overview}",
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.secondary
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(
            modifier = modifier
                .shimmer()
                .fillMaxWidth()
                .height(600.dp)
                .background(Color.DarkGray)
        )
        BoxForLoading(width = 200.dp, height = 25.dp)
        BoxForLoading(width = 100.dp, height = 20.dp)
        BoxForLoading(width = 150.dp, height = 20.dp)
        Text(
            text = "Prolog",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            ),
        )
        BoxForLoading(width = 400.dp, height = 16.dp)
        BoxForLoading(width = 400.dp, height = 16.dp)
        BoxForLoading(width = 400.dp, height = 16.dp)
        BoxForLoading(width = 400.dp, height = 16.dp)
        BoxForLoading(width = 400.dp, height = 16.dp)
    }
}

@Composable
fun BoxForLoading(width: Dp, height: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shimmer()
            .width(width)
            .height(height)
            .background(Color.DarkGray)
    )
}