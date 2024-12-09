package com.nmb.photoshoto.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nmb.photoshoto.R
import com.nmb.photoshoto.presentation.components.TextComponent
import com.nmb.photoshoto.presentation.ui.theme.whiteColor

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(whiteColor)
    ) {

        TextComponent(
            modifier = Modifier.wrapContentSize(),
            textValue = stringResource(R.string.no_images_found)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    EmptyScreen()
}