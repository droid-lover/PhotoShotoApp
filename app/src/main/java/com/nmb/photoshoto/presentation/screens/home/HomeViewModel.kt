package com.nmb.photoshoto.presentation.screens.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.nmb.photoshoto.domain.usecase.GetImagesUseCase
import com.nmb.utilities.Resource
import com.nmb.utilities.logging.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {


    private val _state = mutableStateOf(HomeUiState())
    val state: State<HomeUiState> = _state


    fun getImages() {
        getImagesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = HomeUiState(isLoading = true)
                }

                is Resource.Success -> {
                    viewModelScope.launch {
                        val imageUris = result.data
                        val batchSize = 100
                        val allFilteredImages = mutableListOf<Uri>()
                        val batches = imageUris?.chunked(batchSize) ?: emptyList()

                        // Process batches sequentially
                        for (batch in batches) {
                            _state.value = _state.value.copy(isLoading = true) // Show loader

                            val channel = Channel<Pair<Boolean, Uri>>(Channel.BUFFERED)
                            val maxConcurrentCoroutines = 4
                            val semaphore = Semaphore(maxConcurrentCoroutines)

                            batch.forEach { imageUri ->
                                launch(Dispatchers.Default) {
                                    semaphore.withPermit {
                                        val hasFace = runFaceContourDetection(imageUri, context)
                                        channel.send(hasFace to imageUri)
                                    }
                                }
                            }

                            val filteredImages = mutableListOf<Uri>()
                            repeat(batch.size) {
                                val (hasFace, imageUri) = channel.receive()
                                if (hasFace) {
                                    filteredImages.add(imageUri)
                                }
                            }

                            allFilteredImages.addAll(filteredImages)
                            _state.value = _state.value.copy(images = allFilteredImages, isLoading = false)
                        }
                    }
                }

                is Resource.Error -> {
                    _state.value = HomeUiState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun runDetectionOnImage(uri: Uri) {
        viewModelScope.launch {
            runFaceContourDetection(uri, context)
        }
    }
}

suspend fun runFaceContourDetection(uri: Uri, context: Context): Boolean {
    return try {
        val image = InputImage.fromFilePath(context, uri)
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        val detector = FaceDetection.getClient(options)

        // Use suspendCancellableCoroutine to convert Task to coroutine
        suspendCancellableCoroutine { continuation ->
            detector.process(image)
                .addOnSuccessListener { result ->
                    // Explicitly check for faces
                    var hasFace = false
                    if (result.size > 0) {
                        hasFace = true
                        AppLogger.d("FaceDetection", "Face Detected: ${result.size}")
                    }
                    continuation.resume(hasFace)
                    AppLogger.d("FaceDetection", "Face Detected Not true: ${result.size}")
                }
                .addOnFailureListener { e ->
                    AppLogger.e("FaceDetection", "Error: ${e.message}")
                    continuation.resume(false)
                }
        }

    } catch (e: IOException) {
        AppLogger.e("FaceDetection", "Error: ${e.message}")
        false
    }
}


data class UiState(
    val results: Any? = null, // Replace with the actual type of your results
    val imageHeight: Int = 0,
    val imageWidth: Int = 0,
    val isUiEnabled: Boolean = false,
    val inferenceTime: Long = 0
)


