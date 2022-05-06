package com.matrixvision.watervision.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.matrixvision.watervision.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader{
        return ImageLoader.Builder(app)
            .crossfade(true)
            .componentRegistry {
                add(SvgDecoder(app))
            }
            .build()
    }
}