package com.nmb.photoshoto.di

import android.content.Context
import com.nmb.photoshoto.data.repository.GalleryRepositoryImpl
import com.nmb.photoshoto.domain.repository.IGalleryRepository
import com.nmb.utilities.logging.AppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhotoShotoAppDependencies {
    @Provides
    @Singleton
    fun provideGalleryRepository(@ApplicationContext context: Context): IGalleryRepository {
        AppLogger.d( message = "IGalleryRepository Object created")
        return GalleryRepositoryImpl(context)
    }
    
    
}