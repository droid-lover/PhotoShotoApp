package com.nmb.photoshoto.data.repository

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.nmb.photoshoto.domain.repository.IGalleryRepository
import com.nmb.utilities.logging.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val context: Context
) : IGalleryRepository {

    @SuppressLint("Recycle")
    override suspend fun getImages(): MutableList<Uri>? {
        val images = mutableListOf<Uri>()
        return withContext(Dispatchers.IO) {
            val loadingImages = async {
                val columns = arrayOf(
                    MediaStore.Images.Media._ID
                ) // Only need the _ID column

                val orderBy = MediaStore.Images.Media.DATE_TAKEN

                val imageCursor: Cursor? = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    columns,
                    null,
                    null,
                    "$orderBy DESC"
                )

                if (imageCursor == null) return@async

                imageCursor.moveToFirst()

                while (!imageCursor.isAfterLast) {
                    val id = imageCursor.getLong(imageCursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    images.add(contentUri)
                    imageCursor.moveToNext()
                }
            }
            loadingImages.await()
            if (images.size > 0) {
                AppLogger.d(message = "Some Images read successfully")
            } else {
                AppLogger.d(message = "No Images found")
            }

            return@withContext images
        }
    }
}