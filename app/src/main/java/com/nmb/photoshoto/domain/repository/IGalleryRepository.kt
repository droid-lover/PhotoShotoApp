package com.nmb.photoshoto.domain.repository

import android.net.Uri


interface IGalleryRepository {

    suspend fun getImages() : MutableList<Uri>?

}