package com.nmb.photoshoto.presentation.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nmb.photoshoto.presentation.components.TextComponent
import com.nmb.utilities.logging.AppLogger

@Composable
fun GalleryComponent(images: List<Uri>?) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextComponent(
            modifier = Modifier.wrapContentSize(),
            textValue = "Images with Face: ${images?.size}"
        )
        Spacer(modifier = Modifier.size(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            if (images?.isNotEmpty() == true) {
                AppLogger.d(message = "images are not null")
                items(items = images) {
                    Card(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            } else {
                AppLogger.d(message = "images are null")
            }
        }
    }
}