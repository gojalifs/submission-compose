package com.ngapak.dev.latihan.watchit.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.latihan.watchit.di.Injection
import com.ngapak.dev.latihan.watchit.factory.ViewModelFactory

@Composable
fun SearchScreen(
    navigateToDetail: (Int) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideMoviesRepository(
                LocalContext.current
            )
        )
    ),
) {
    val searchesMovie by viewModel.searchedMovie.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier) {
        SearchBar(query = query, onQueryChange = viewModel::searchMovie, onNavigateUp = navigateUp)
        LazyColumn(modifier = modifier.padding(top = 100.dp)) {
            items(searchesMovie, key = { it.id ?: -1 }) { movie ->
                SearchListCard(
                    id = movie.id ?: 0,
                    imageUrl = movie.posterPath ?: "",
                    title = movie.title ?: "",
                    rating = movie.voteAverage ?: 0.0,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}

@Composable
fun SearchListCard(
    id: Int,
    imageUrl: String,
    title: String,
    rating: Double,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { navigateToDetail(id) }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original$imageUrl")
                .crossfade(true)
                .build(),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(100.dp)
        )
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 21.sp,
                ),
                modifier = modifier.padding(horizontal = 6.dp),
            )
            Row(modifier = modifier.padding(horizontal = 6.dp)) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "$rating / 10",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = Color.Gray,
                        fontSize = 16.sp,
                    )
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back to previous screen"
                )
            }
        },
        placeholder = {
            Text("Find your favorite movie")
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}