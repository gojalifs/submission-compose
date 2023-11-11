package com.ngapak.dev.latihan.watchit.ui.screen.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ngapak.dev.latihan.watchit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { AboutTopBar(navigateUp) }
    ) { paddingValues ->
        AboutPageContent(modifier.padding(paddingValues))
    }
}

@Composable
fun AboutPageContent(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://github.com/gojalifs.png")
                .crossfade(true)
                .build(),
            contentDescription = "User Avatar",
            placeholder = painterResource(id = R.drawable.image_placeholder),
            error = painterResource(id = R.drawable.error_placeholder),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(200.dp)
                .clip(CircleShape),
        )
        Text(text = "This app is made with heart <3", color = Color.Gray)
        Text(text = "Just contact me on this bio :", color = Color.Gray)
        Text(
            text = "Fajar Sidik Prasetio",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = "fajarsidik1999@hotmail.com", style = MaterialTheme.typography.titleLarge)
        Text(text = "https://github.com/gojalifs", style = MaterialTheme.typography.titleLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopBar(navigateUp: () -> Unit) {
    TopAppBar(
        title = { Text("About Watch It!") },
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
            }
        }
    )
}
