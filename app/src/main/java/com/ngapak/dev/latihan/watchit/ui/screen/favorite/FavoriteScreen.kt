package com.ngapak.dev.latihan.watchit.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ngapak.dev.latihan.watchit.data.local.entities.FavoriteEntity
import com.ngapak.dev.latihan.watchit.di.Injection
import com.ngapak.dev.latihan.watchit.factory.FavoriteViewModelFactory
import com.ngapak.dev.latihan.watchit.ui.component.MovieCard

@Composable
fun FavoriteScreen(
    navigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = FavoriteViewModelFactory(Injection.provideFavoriteRepository(LocalContext.current))
    )
) {
    LaunchedEffect(Unit){
        viewModel.getFavorites()
    }
    Scaffold(
        topBar = { AppBar(navigateUp) }
    ) { paddingValues ->
        viewModel.moviesState.collectAsState().value.let { movies ->
            if (movies != null) {
                MoviesGridContent(
                    movies = movies,
                    modifier = modifier.padding(paddingValues),
                    viewModel = viewModel,
                    navigateToDetail = navigateToDetail
                )
            } else {
                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Failed getting movies. Tap to try again",
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .clickable { viewModel.getFavorites() }
                            .padding(12.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun MoviesGridContent(
    movies: List<FavoriteEntity>,
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel,
    modifier: Modifier
) {
    if (movies.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No movie available. Tap to try again",
                textAlign = TextAlign.Center,
                modifier = modifier.clickable { viewModel.getFavorites() })
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            items(movies, key = { it.id }) { data ->
                MovieCard(
                    id = data.id,
                    imageUrl = data.posterPath,
                    title = data.title,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navigateUp: () -> Unit) {
    TopAppBar(
        title = { Text("Favorite Movies") },
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        }
    )
}