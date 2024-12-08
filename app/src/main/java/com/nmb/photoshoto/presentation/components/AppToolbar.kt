package com.nmb.photoshoto.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nmb.photoshoto.R
import com.nmb.photoshoto.presentation.ui.theme.primaryColor
import com.nmb.photoshoto.presentation.ui.theme.whiteColor

@Composable
fun AppToolbar() {
    Row(
        modifier = Modifier
            .background(primaryColor)
            .systemBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 18.dp,end =18.dp,top=8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        TextComponent(
            modifier = Modifier.wrapContentSize(),
            textValue = stringResource(R.string.app_name),
            fontSizeValue = 20.sp,
            textColorValue = whiteColor
        )

    }
}

@Preview(showBackground = true)
@Composable
fun AppToolbarPreview() {
    AppToolbar()
}
