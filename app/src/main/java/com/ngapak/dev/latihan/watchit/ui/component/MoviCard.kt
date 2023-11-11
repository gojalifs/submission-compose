package com.ngapak.dev.latihan.watchit.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieCard(
    id: Int,
    imageUrl: String,
    title: String,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    rating: Double? = null,
) {
    Card(modifier = modifier
        .height(270.dp)
        .clickable {
            navigateToDetail(id)
        }) {
        Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original$imageUrl")
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                    )
                    .size(height = 200.dp, width = 250.dp)
                    .align(Alignment.CenterHorizontally),
                alignment = Alignment.Center
            )
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                ),
                modifier = modifier.padding(horizontal = 6.dp),
            )
            if (rating != null)
                Row(modifier = modifier.padding(horizontal = 6.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = modifier.size(16.dp),
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

