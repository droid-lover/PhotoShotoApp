package com.nmb.photoshoto.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmb.photoshoto.presentation.ui.theme.blackColor
import com.nmb.photoshoto.presentation.ui.theme.grayLightColor
import com.nmb.photoshoto.presentation.ui.theme.primaryColor

@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = grayLightColor
            )
            .padding(all = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(10.dp))

        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(10.dp))

        TextComponent(
            modifier = Modifier.wrapContentSize(),
            textValue = "Please wait",
            textColorValue = primaryColor,
            fontSizeValue = 14.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        TextComponent(
            modifier = Modifier.wrapContentSize(),
            textValue = "while we process next batch\nof images to find images with Face.",
            textColorValue = blackColor,
            fontSizeValue = 12.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun LoadingComponentPreview() {
    LoadingComponent()
}