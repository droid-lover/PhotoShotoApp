package com.nmb.photoshoto.presentation.screens.home

import android.net.Uri

data class HomeUiState (
    val isLoading : Boolean = false,
    val error : String = "",
    val images : MutableList<Uri>? = mutableListOf()
)