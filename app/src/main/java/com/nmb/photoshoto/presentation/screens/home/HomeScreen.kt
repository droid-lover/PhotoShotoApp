package com.nmb.photoshoto.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nmb.photoshoto.presentation.components.AppToolbar
import com.nmb.photoshoto.presentation.components.TextComponent
import com.nmb.photoshoto.presentation.ui.theme.whiteColor


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getImages()
    }

    Scaffold(
        topBar = {
            AppToolbar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(whiteColor)
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            if (state.error.isNotBlank()) {
                TextComponent(
                    textValue = state.error,
                    textColorValue = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }

            viewModel.state.value.images?.also {
                if (it.size > 0) {
                    GalleryComponent(images = it)
                } else {
                    EmptyScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}