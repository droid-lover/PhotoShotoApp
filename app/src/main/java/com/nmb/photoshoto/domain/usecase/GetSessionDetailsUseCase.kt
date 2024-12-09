package com.nmb.photoshoto.domain.usecase

import com.nmb.photoshoto.domain.repository.IGalleryRepository
import com.nmb.utilities.Resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val iGalleryRepository: IGalleryRepository
) {

    operator fun invoke(): Flow<Resource<MutableList<String>>> = flow {
        try {
            emit(Resource.Loading())
            val sessionDetail = iGalleryRepository.getImages()
            sessionDetail?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error("Couldn't get images"))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't get images"))
        }
    }
}