package com.nmb.photoshoto.presentation.screens.home

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmb.photoshoto.domain.usecase.GetImagesUseCase
import com.nmb.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
): ViewModel() {


    private val _state = mutableStateOf(HomeUiState())
    val state: State<HomeUiState> = _state

    fun getImages() {
        getImagesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = HomeUiState(images = result.data)
                }

                is Resource.Error -> {
                    _state.value = HomeUiState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun isPermissionAllowed(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    }

}