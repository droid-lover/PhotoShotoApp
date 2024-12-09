package com.nmb.photoshoto.domain.repository


interface IGalleryRepository {

    suspend fun getImages() : MutableList<String>?

}